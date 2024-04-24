package com.br.fullstackedu.notesmng.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "nota")
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Atributo title é obrigatório")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_caderno")
    @NotNull(message = "Atributo id_caderno é obrigatorio")
    private CadernoEntity caderno;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NotNull(message = "Atributo id_usuario é obrigatorio")
    private UsuarioEntity usuario;
}
