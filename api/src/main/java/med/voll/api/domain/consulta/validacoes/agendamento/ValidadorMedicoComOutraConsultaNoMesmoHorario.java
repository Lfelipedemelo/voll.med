package med.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

	@Autowired
	private ConsultaRepository repository;

	@Override
	public void validar(DadosAgendamentoConsulta dados) {
		Boolean medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),dados.data());
		if (medicoPossuiOutraConsultaNoMesmoHorario) {
			throw new ValidacaoException("Méedico já possui outra consulta agendada nesse mesmo horário");
		}
	}

}
