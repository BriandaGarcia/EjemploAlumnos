package uam.mx.tsis.ejemplobackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable;
import uam.mx.tsis.ejemplobackend.datos.AlumnoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Prueba de integració para el endpoint alumnos
 * @author Brianda Garcia
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AlumnoControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@BeforeEach
	public void prepare() {
		//AQUI SE PUEDE HACER COSAS PARA PREPARAR SUS CASOS DE PRUEBA, INCLUYENDO AGREGAR DATOS A LA BD
	}
	
	@Test
	public void testCreate201(){
		//CREO EL ALUMNO QUE VOY A ENVIAR
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		//CREO EL ENCABEZADO
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", MediaType.APPLICATION_JSON.toString());
		
		//CREO LA PETICION CON EL ALUMNO COMO BODY Y EL ENCABEZADO
		HttpEntity<Alumno> request = new HttpEntity<>(alumno, headers);
		
		ResponseEntity<Alumno> responseEntity = restTemplate.exchange("/alumnos", HttpMethod.POST, request, Alumno.class);
		
		log.info("Me regresó: "+responseEntity.getBody());
		
		//CORROBO QUE EL ENDPOINT ME REGRESE EL STATUS ESPERADO
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(12345678);
		
		//CORROBO QUE EN LA BD SE GUARDO EL ALUMNO
		assertEquals(alumno, alumnoOpt.get());
	}
}
