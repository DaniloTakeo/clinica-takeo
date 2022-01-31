package br.com.clinicatakeo.clinicatakeo.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.clinicatakeo.clinicatakeo.model.Medico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicoForm {

	@NotNull
	@NotBlank
	private String nome;
	private EnderecoForm endereco;
	private String crm;
	private String especialidade;

	public Medico converter() {
		return new Medico(this.nome, this.endereco.getLogradouro(), this.endereco.getNumero(),
				this.endereco.getBairro(), this.endereco.getCep(), this.endereco.getCidade(),
				this.crm, this.especialidade);
	}
	
	
}
