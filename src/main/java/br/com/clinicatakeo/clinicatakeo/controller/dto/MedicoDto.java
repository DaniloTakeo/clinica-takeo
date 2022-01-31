package br.com.clinicatakeo.clinicatakeo.controller.dto;

import org.springframework.data.domain.Page;

import br.com.clinicatakeo.clinicatakeo.model.Medico;
import lombok.Getter;

@Getter
public class MedicoDto {

	private String nome;
	private String crm;
	private String especialidade;
	private EnderecoDto endereco = new EnderecoDto();
	
	public MedicoDto(Medico medico) {
		this.nome = medico.getNome();
		this.endereco.setLogradouro(medico.getEndereco().getLogradouro());
		this.endereco.setNumero(medico.getEndereco().getNumero());
		this.endereco.setBairro(medico.getEndereco().getBairro());
		this.endereco.setCep(medico.getEndereco().getCep());
		this.endereco.setCidade(medico.getEndereco().getCidade());
		this.crm = medico.getCrm();
		this.especialidade = medico.getEspecialidade();
	}

	public static Page<MedicoDto> converter(Page<Medico> medicos) {
		return medicos.map(MedicoDto::new);
	}
	
}
