package br.com.clinicatakeo.clinicatakeo.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoForm {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
