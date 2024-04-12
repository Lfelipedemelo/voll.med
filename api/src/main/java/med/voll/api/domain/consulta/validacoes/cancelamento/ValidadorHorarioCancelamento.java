package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component
public class ValidadorHorarioCancelamento implements ValidadorCancelamentoDeConsulta {

	@Autowired
	ConsultaRepository repository;

	@Override
	public void validar(DadosCancelamentoConsulta dados) {
		LocalDateTime dataConsulta = repository.findDataByConsultaId(dados.idConsulta());
		if (null == dataConsulta) {
			throw new ValidacaoException("Não foi possível encontrar uma consulta com este ID!");
		}
		LocalDateTime agora = LocalDateTime.now();
		if(dataConsulta.isBefore(agora)) {
			throw new ValidacaoException("Não é possível cancelar consultas passadas!");
		}
		if (Duration.between(agora, dataConsulta).toDays() < 1) {
			throw new ValidacaoException("A consulta só poderá ser cancelada com antecedência mínima de 24 horas!");
		}
	}
}
