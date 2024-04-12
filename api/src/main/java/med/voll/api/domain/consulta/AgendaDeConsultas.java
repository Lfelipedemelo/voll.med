package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;

	@Autowired
	private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		if (null != dados.idPaciente() && !pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("ID do paciente informado não existe!");
		}

		if (null != dados.idMedico() && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("ID do Medico informado não existe!");
		}

		validadores.forEach(v -> v.validar(dados));

		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		Medico medico = escolherMedico(dados);
		if(null == medico) {
			throw new ValidacaoException("Não existe médico disponível nessa data!");
		}
		Consulta consulta = new Consulta(null, medico, paciente, dados.data(), true, null);

		consultaRepository.save(consulta);
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if (null != dados.idMedico()) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}

		if (null == dados.especialidade()) {
			throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
		}
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}




	public void cancelar(DadosCancelamentoConsulta dados) {
		if (null != dados.idConsulta() && !consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("ID da consulta informada não existe!");
		}
		validadoresCancelamento.forEach(v -> v.validar(dados));

		Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.setMotivoCancelamento(dados.motivo());
		consulta.cancelar();
	}

}
