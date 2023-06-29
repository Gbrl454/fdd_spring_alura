package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByMadicoIdAndData (Long id, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween (Long id, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
