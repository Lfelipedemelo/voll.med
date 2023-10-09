package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.medico.Medico;

@RestController
@RequestMapping("medico")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar (@RequestBody @Valid DadosCadastroMedico dadosCadastroMedico) {
		repository.save(new Medico(dadosCadastroMedico));
	}
	
}
