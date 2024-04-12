package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private MedicoRepository repository;

	@Override
	public void validar(DadosAgendamentoConsulta dados) {

		if (null == dados.idMedico()) {
			return;
		}

		Boolean medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
		if (!medicoEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com médico inativo!");
		}
	}
}
