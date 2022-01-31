package br.com.clinicatakeo.clinicatakeo.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.clinicatakeo.clinicatakeo.model.Consulta;
import br.com.clinicatakeo.clinicatakeo.model.Medico;
import br.com.clinicatakeo.clinicatakeo.model.StatusConsulta;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(profiles = "test")
public class ConsultaRepositoryTest {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Test
	public void deveriaRetornarUmaListaDeConsultasAgendadas() {
		List<Consulta> consultas = consultaRepository.findByStatus(StatusConsulta.AGENDADA, PageRequest.of(0, 1)).getContent();
		
		Assert.assertNotNull(consultas);
	}
	
	@Test
	public void deveriaRetornarUmaListaDeConsultasAgendadasPorMedico() {
		Medico medico = medicoRepository.findById(6l).get();
		List<Consulta> consultas = consultaRepository.findByMedico(medico, PageRequest.of(0, 1)).get().getContent();
		
		Assert.assertNotNull(consultas);
	}

}
