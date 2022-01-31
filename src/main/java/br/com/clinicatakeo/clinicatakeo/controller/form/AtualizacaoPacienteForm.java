package br.com.clinicatakeo.clinicatakeo.controller.form;

import br.com.clinicatakeo.clinicatakeo.model.Endereco;
import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.model.PlanoDeSaude;
import br.com.clinicatakeo.clinicatakeo.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizacaoPacienteForm {

	private String nome;
	private EnderecoForm endereco = new EnderecoForm();
	private String planoDeSaude;

	public Paciente atualizar(Long id, PacienteRepository pacienteRepository) {
		Paciente paciente = pacienteRepository.getById(id);
		
		paciente.setNome(nome);
		paciente.setEndereco(new Endereco());
		paciente.getEndereco().setLogradouro(endereco.getLogradouro());
		paciente.getEndereco().setNumero(endereco.getNumero());
		paciente.getEndereco().setBairro(endereco.getBairro());
		paciente.getEndereco().setCep(endereco.getCep());
		paciente.getEndereco().setCidade(endereco.getCidade());
		paciente.setPlanoDeSaude(PlanoDeSaude.valueOf(planoDeSaude));
		
		return paciente;
	}
	
}
