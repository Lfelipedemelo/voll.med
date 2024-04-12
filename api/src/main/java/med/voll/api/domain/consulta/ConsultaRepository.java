package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	Boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

	Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario,
			LocalDateTime ultimoHorario);

	@Query("""
			select c.data
			from Consulta c
			where c.id = :idConsulta
			and c.ativo = true
			""")
	LocalDateTime findDataByConsultaId(Long idConsulta);
}
