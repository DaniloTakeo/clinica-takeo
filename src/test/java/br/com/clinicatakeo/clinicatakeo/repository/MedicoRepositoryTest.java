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

import br.com.clinicatakeo.clinicatakeo.model.Medico;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles(profiles = "test")
public class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Test
	public void deveriaRetornarUmaListaDeMedicosPorEspecialidade() {
		String especialidade = "Pediatra";
		List<Medico> medicos = medicoRepository.findByEspecialidade(especialidade, PageRequest.of(0, 1))
				.get().getContent();
		
		Assert.assertNotNull(medicos);
		Assert.assertEquals(especialidade, medicos.get(0).getEspecialidade());
	}

}
