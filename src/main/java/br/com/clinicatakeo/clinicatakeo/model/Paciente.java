package br.com.clinicatakeo.clinicatakeo.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Embedded
	private Endereco endereco = new Endereco();
	
	@Enumerated(EnumType.STRING)
	private PlanoDeSaude planoDeSaude;
	
	public Paciente(String nome, String logradouro, String numero, String bairro, String cep,
			String cidade, String planoDeSaude) {
		this.nome = nome;
		this.endereco.setLogradouro(logradouro);
		this.endereco.setNumero(numero);
		this.endereco.setBairro(bairro);
		this.endereco.setCep(cep);
		this.endereco.setCidade(cidade);
		this.planoDeSaude = PlanoDeSaude.valueOf(planoDeSaude);
	}
}
