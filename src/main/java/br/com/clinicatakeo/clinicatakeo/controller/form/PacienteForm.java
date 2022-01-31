package br.com.clinicatakeo.clinicatakeo.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteForm {

	@NotNull
	@NotBlank
	private String nome;
	
	private EnderecoForm endereco;
	private String planoDeSaude;
	
	public Paciente converter() {
		return new Paciente(this.nome, this.endereco.getLogradouro(), this.endereco.getNumero(),
				this.endereco.getBairro(), this.endereco.getCep(), this.endereco.getCidade(),
				this.planoDeSaude);
	}

}
