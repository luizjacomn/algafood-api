package com.luizjacomn.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private List<Grupo> grupos = new ArrayList<>();

    public boolean senhaCoincideCom(String confirmacaoSenha) {
        return getSenha().equals(confirmacaoSenha);
    }

    public boolean senhaNaoCoincideCom(String confirmacaoSenha) {
        return !senhaCoincideCom(confirmacaoSenha);
    }
}
