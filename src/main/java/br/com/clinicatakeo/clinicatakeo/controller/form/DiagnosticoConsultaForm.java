package br.com.clinicatakeo.clinicatakeo.controller.form;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.StatusConsulta;
import br.com.clinicatakeo.clinicatakeo.repository.ConsultaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoConsultaForm {

	private String cid;
	private String diagnostico;
	
	public Consulta atualizar(Long id, ConsultaRepository consultaRepository) {
		Consulta consulta = consultaRepository.getById(id);
		
		consulta.setCid(cid);
		consulta.setDiagnostico(diagnostico);
		consulta.setStatus(StatusConsulta.REALIZADA);
		
		return consulta;
	}
	
}
