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

import br.com.clinicatakeo.clinicatakeo.model.Paciente;
import br.com.clinicatakeo.clinicatakeo.model.PlanoDeSaude;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(profiles = "test")
public class PacienteRepositoryTest {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Test
	public void deveriaRetornarUmaListaDePacientesPeloPlanoDeSaude() {
		String planoDeSaude = "PLANO2";
		List<Paciente> pacientes = pacienteRepository.findByPlanoDeSaude(PlanoDeSaude.valueOf(planoDeSaude), PageRequest.of(0, 1))
				.get().getContent();
		
		Assert.assertNotNull(pacientes);
		Assert.assertEquals(pacientes.get(0).getPlanoDeSaude().toString(), planoDeSaude);
	}

}
