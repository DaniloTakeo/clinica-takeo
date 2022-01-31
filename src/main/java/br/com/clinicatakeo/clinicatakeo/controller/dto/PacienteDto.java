package br.com.clinicatakeo.clinicatakeo.controller.dto;

import org.springframework.data.domain.Page;

import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import lombok.Getter;

@Getter
public class PacienteDto {
	
	private String nome;
	private EnderecoDto endereco = new EnderecoDto();
	private String planoDeSaude;
	
	public PacienteDto(Paciente paciente) {
		this.nome = paciente.getNome();
		this.endereco.setLogradouro(paciente.getEndereco().getLogradouro());
		this.endereco.setNumero(paciente.getEndereco().getNumero());
		this.endereco.setBairro(paciente.getEndereco().getBairro());
		this.endereco.setCep(paciente.getEndereco().getCep());
		this.endereco.setCidade(paciente.getEndereco().getCidade());
		this.planoDeSaude = paciente.getPlanoDeSaude().toString();
	}

	public static Page<PacienteDto> converter(Page<Paciente> pacientes) {
		return pacientes.map(PacienteDto::new);
	}
}
