package com.luizjacomn.algafood.domain.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

import com.luizjacomn.algafood.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@Valid
	@ConvertGroup(to = Groups.Id.class)
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado_id", nullable = false)
	private Estado estado;

}
