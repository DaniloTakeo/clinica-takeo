package br.com.clinicatakeo.clinicatakeo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;
	private String crm;
	private String especialidade;

	@Embedded
	private Endereco endereco = new Endereco();
	
	public Medico(@NotNull @NotBlank String nome, String logradouro, String numero, String bairro, String cep,
			String cidade, String crm, String especialidade) {
		this.nome = nome;
		this.endereco.setLogradouro(logradouro);
		this.endereco.setNumero(numero);
		this.endereco.setBairro(bairro);
		this.endereco.setCep(cep);
		this.endereco.setCidade(cidade);
		this.crm = crm;
		this.especialidade = especialidade;
	}
}
