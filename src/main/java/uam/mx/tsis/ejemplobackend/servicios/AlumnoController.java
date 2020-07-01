package uam.mx.tsis.ejemplobackend.servicios;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.negocio.AlumnoService;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API Rest
 * @author Brianda Garcia
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@ApiOperation(
		value = "Crear alumno",
		notes = "Permite crear un nuevo alumno, la matrícula debe ser única"
		) //DOCUMENTACION DEL API
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Alumno nuevoAlumno) { //VALIDACIONES
		
		log.info("Recibí llamada a create con "+nuevoAlumno); //LOGGING
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno");
		}
	}
	
	@ApiOperation(
			value = "Recuperar alumnos",
			notes = "Permite recuperar a todos los alumnos"
			)
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll() {
		
		Iterable <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@ApiOperation(
			value = "Recuperar alumno",
			notes = "Permite recuperar un alumno mediante su matricula, la cual es única"
			)
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("matricula") @Valid Integer matricula) {
		
		log.info("Buscando al alumno con matricula "+matricula);
		
		Optional<Alumno> alumno = alumnoService.retrieve(matricula);

		if(alumno.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alummno con matricula "+matricula+" no encontrado");
		}
	}
	
	@ApiOperation(
			value = "Actualizar alumno",
			notes = "Permite actualizar la información de un alumno, identificandolo por su matricula, la cual no debe ser modificada"
			)
	@PutMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula, @RequestBody @Valid Alumno alumnoActualizado) {
		
		log.info("Actualizando al alumno con matricula "+matricula);
		
		Optional<Alumno> alumno = alumnoService.retrieve(matricula);
		
		if(alumno.isPresent()) {	
			alumnoActualizado.setMatricula(matricula);
			
			alumno = Optional.of(alumnoService.update(alumnoActualizado));
			
			if(alumno.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(alumnoActualizado);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha podido actualizar el alumno");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con matricula "+matricula+" no encontrado");
		}
	}
	
	@ApiOperation(
			value = "Borrar alumno",
			notes = "Permite borrar a un alumno, siendo identificado por su matricula"
			)
	@DeleteMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable Integer matricula) {
		
		log.info("Borrando al alumno con matricula "+matricula);
		
		Optional<Alumno> alumno = alumnoService.retrieve(matricula);
		
		if(alumno.isPresent()) {
			alumnoService.delete(matricula);

			return ResponseEntity.status(HttpStatus.OK).body("Alumno con matricula "+matricula+" eliminado correctamente");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con matricula "+matricula+" no encontrado");
		}
	}
}