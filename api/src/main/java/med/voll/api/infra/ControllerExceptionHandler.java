package med.voll.api.infra;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	private static String MEDICO_JA_CADASTRADO = "Medico já cadastrado!";

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException e) {
		List<FieldError> erros = e.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity tratarErro500(Exception e) {
		if(e instanceof DataIntegrityViolationException) {
			return ResponseEntity.status(CONFLICT).body(new DadosRetornoMensagemComErro(CONFLICT.value(), MEDICO_JA_CADASTRADO));
		}
		return ResponseEntity.internalServerError().build();
		
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}

	private record DadosRetornoMensagemComErro(int error, String mensagem) {
	}

}
