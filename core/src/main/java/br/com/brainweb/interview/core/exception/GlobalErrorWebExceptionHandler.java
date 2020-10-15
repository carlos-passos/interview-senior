package br.com.brainweb.interview.core.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@RestControllerAdvice
@Order(-2)
public class GlobalErrorWebExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);
	
	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	@ExceptionHandler(value = Throwable.class)
	public ResponseEntity<Object> handle(Throwable ex) {
    	if(ex instanceof HttpClientErrorException) {
    		return handleHttpClientError((HttpClientErrorException) ex);
    	} else if(ex instanceof WebExchangeBindException) {
    		return handleMethodArgumentNotValid((WebExchangeBindException) ex);
    	} else if(ex instanceof HttpMessageNotReadableException)  {
    		return handleHttpReadableError((HttpMessageNotReadableException) ex);   		
    	}
    	String message = "Ocorreu um erro interno de servidor.";
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.INTERNAL_SERVER_ERROR, message);
		return handleExceptionInternal((Exception) ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex) {
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.CONFLICT, ex.getMessage());
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstrantViolation(javax.validation.ConstraintViolationException ex) {
		String mensagem = ex.getCause().getMessage();
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.CONFLICT, mensagem);
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = HttpClientErrorException.class)
	protected ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex) {
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(ex.getStatusCode(), ex.getStatusText());
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), ex.getStatusCode());
	}
	
	@ExceptionHandler(value = WebExchangeBindException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(WebExchangeBindException ex) {
		String mensagem = "O pedido não pode ser cumprido devido a envio de campos inválidos";
		List<GlobalFieldError> erros = getErrors(ex);
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.BAD_REQUEST, mensagem, erros);
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		String mensagem = "O pedido não pode ser cumprido devido a envio de campos inválidos";
		List<GlobalFieldError> erros = getErrors(ex.getBindingResult());
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.BAD_REQUEST, mensagem, erros);
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpReadableError(HttpMessageNotReadableException ex) {
		String mensagem = "Não foi possível converter o objeto json. "
				+ "A exceção pode ter sido ocasionada por algum atributo em formato diferente do documentado. "
				+ "Valide os campos na documentação da Api";
		GlobalErrorAttributes responseExceptionHandler = new GlobalErrorAttributes(HttpStatus.BAD_REQUEST, mensagem);
		return handleExceptionInternal(ex, responseExceptionHandler, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status) {
		log.error("{} {} :: {}", new String[]{String.valueOf(status.value()), status.name(), gson.toJson(body)}, ex);
		return new ResponseEntity<Object>(body, headers, status);
	}

	private List<GlobalFieldError> getErrors(BindingResult bindingResult) {

		return bindingResult.getFieldErrors().stream()
                .map(error -> new GlobalFieldError(error.getDefaultMessage(), error.getField(), error.getRejectedValue()))
                .collect(Collectors.toList());
	}
	
}
