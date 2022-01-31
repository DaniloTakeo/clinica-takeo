package br.com.clinicatakeo.clinicatakeo.controller.dto;

import lombok.Data;

@Data
public class EnderecoDto {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cep;
	private String cidade;
}
