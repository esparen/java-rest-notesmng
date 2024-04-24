package com.br.fullstackedu.notesmng.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "caderno")
public class CadernoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome")
    @NotNull(message = "Atributo nome é obrigatório")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NotNull(message = "Atributo id_usuario é obrigatorio")
    private UsuarioEntity user;




}
