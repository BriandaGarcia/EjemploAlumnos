package uam.mx.tsis.ejemplobackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Grupo;

public class GrupoControllerIntegrationTest {

	@Test
	public void testCreate201(){
		//CREO GRUPO
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//CREO EL ALUMNO
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		//CREO EL ENCABEZADO
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());
		
		//CREO LA PETICION CON EL ALUMNO COMO BODY Y EL ENCABEZADO
		HttpEntity<Alumno> request = new HttpEntity<>(alumno, headers);
		
		ResponseEntity<Alumno> responseEntity = RestTemplate.exchange("/grupos/{id}/alumnos?matricula=1234", HttpMethod.POST, request, Alumno.class);
		
		log.info("Me regresó: "+responseEntity.getBody());
		
		//CORROBO QUE EL ENDPOINT ME REGRESE EL STATUS ESPERADO
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(12345678);
		
		//CORROBO QUE EN LA BD SE GUARDO EL ALUMNO
		assertEquals(alumno, alumnoOpt.get());
	}
}
