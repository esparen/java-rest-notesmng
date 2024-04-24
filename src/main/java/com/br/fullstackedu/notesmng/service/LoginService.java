package com.br.fullstackedu.notesmng.service;

import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.CadastroRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoRequest.LoginRequest;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.CadastroResponse;
import com.br.fullstackedu.notesmng.controller.dto.dtoResponse.LoginResponse;
import com.br.fullstackedu.notesmng.database.entity.UsuarioEntity;
import com.br.fullstackedu.notesmng.database.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private static final long EXPIRATION_TIME = 72000L;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptEncoder;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String getFieldInToken(String token, String field) {
        String result = jwtDecoder
                .decode(token)
                .getClaims()
                .get(field)
                .toString();
        return result;
    }

    public CadastroResponse createUser(CadastroRequest cadastroRequest) {
        boolean foundUser = usuarioRepository.findByNomeUsuario(cadastroRequest.nome_usuario()).isPresent();
        if (foundUser)
            return new CadastroResponse(false, LocalDateTime.now() , "Usuário [" + cadastroRequest.nome_usuario() + "] já existe" , HttpStatus.CONFLICT);

        UsuarioEntity newUser = new UsuarioEntity();
        newUser.setNomeUsuario(cadastroRequest.nome_usuario());
        newUser.setSenha(bCryptEncoder.encode(cadastroRequest.senha()));
        usuarioRepository.save(newUser);

        return new CadastroResponse(true, LocalDateTime.now() , "Usuário criado com sucesso" , HttpStatus.CREATED);

    }

    public LoginResponse doLogin(LoginRequest loginRequest) throws Exception{
        try {
            UsuarioEntity usuarioEntity = usuarioRepository
                    .findByNomeUsuario(loginRequest.nome_usuario())
                    .orElseThrow(
                            () -> {
                                String errMessageUsrNotFound = "Usuário ["+ loginRequest.nome_usuario() +"] não encontrado";
                                log.error(errMessageUsrNotFound);
                                return new RuntimeException(errMessageUsrNotFound);
                            }
                    );

            if (!usuarioEntity.isValidPassword(loginRequest, bCryptEncoder)) {
                String errMessage = "Credenciais inválidas";
                log.error(errMessage);
                return new LoginResponse(
                        false,
                        LocalDateTime.now(),
                        errMessage,
                        null,
                        EXPIRATION_TIME);
            }

            log.info("Sucesso na autenticação do usuário [{}]", loginRequest.nome_usuario());
            Instant now = Instant.now();
            String scope = "admin";

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("notesmng_system")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(EXPIRATION_TIME))
                    .subject(usuarioEntity.getId().toString())  // token owner
                    .claim("scope", scope)
                    .claim("usuario_id", usuarioEntity.getId())
                    .build();
            log.info("claims: [{}]", claims);

            var tokenJWT = jwtEncoder.encode(
                    JwtEncoderParameters.from(claims) // encoding params
            ).getTokenValue(); // get the actual token from the object

            return new LoginResponse(
                    true,
                    LocalDateTime.now(),
                    "Sucesso na autenticação do usuário [" + loginRequest.nome_usuario() + "]",
                    tokenJWT,
                    EXPIRATION_TIME);


        } catch(Exception e ) {
            String errMessage = "Falha ao tentar autenticação do usuário [" + loginRequest.nome_usuario() + "]. Erro: " + e.getMessage();
            log.error(errMessage);
            return new LoginResponse(false, LocalDateTime.now(), errMessage, null, EXPIRATION_TIME);
        }
    }
}
