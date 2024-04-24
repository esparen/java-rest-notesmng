package com.br.fullstackedu.notesmng.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "perfil")
public class PerfilEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfil_id")
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Atributo nome é obrigatório")
    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
