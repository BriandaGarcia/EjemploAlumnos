package uam.mx.tsis.ejemplobackend.servicios;

import java.util.List;
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

import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.negocio.AlumnoService;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API Rest
 * @author Bree
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Alumno nuevoAlumno) {
		
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno");
		}
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll() {
		
		List <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("matricula") @Valid Integer matricula) {
		
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrieve(matricula);

		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula, @RequestBody @Valid Alumno alumnoActualizado) {
		
		log.info("Actualizando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrieve(matricula);
		
		if(alumno != null) {
			alumno.setNombre(alumnoActualizado.getNombre());
			alumno.setCarrera(alumnoActualizado.getCarrera());
			alumno.setMatricula(matricula);
			
			alumnoActualizado = alumnoService.update(matricula, alumno);
			
			if(alumnoActualizado != null) {
				return ResponseEntity.status(HttpStatus.OK).body(alumnoActualizado);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se ha actualizar el alumno");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con matricula "+matricula+"no encontrado");
		}
	}
	
	@DeleteMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable Integer matricula) {
		
		log.info("Borrando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrieve(matricula);
		
		if(alumno != null) {
			alumnoService.delete(matricula);

			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con matricula "+matricula+"no encontrado");
		}
	}
}