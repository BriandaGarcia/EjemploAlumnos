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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.negocio.GrupoService;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Grupo;

@RestController
@Slf4j
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@ApiOperation(
		value = "Crear grupo",
		notes = "Permite crear un nuevo grupo"
		) //DOCUMENTACION DEL API
	@PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Grupo nuevoGrupo) { //VALIDACIONES
		
		log.info("Recibí llamada a create con "+nuevoGrupo); //LOGGING
		
		Grupo grupo = grupoService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear grupo");
		}
	}
	
	/**
	 * POST /grupos/{id}/alumnos?matricula=1234
	 * PROBAR ESTE!!
	 * @param id
	 * @param matricula
	 * @return
	 */
	@ApiOperation(
		value = "Agrega alumno",
		notes = "Permite agregar un nuevo alumno a un grupo"
		) //DOCUMENTACION DEL API
	@PostMapping(path = "/grupos/{id}/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addStudentToGroup(@PathVariable("id") Integer id, @RequestParam("matricula") Integer matricula) { //VALIDACIONES
		
		boolean result = grupoService.addStudentToGroup(id, matricula);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@ApiOperation(
			value = "Recuperar grupos",
			notes = "Permite recuperar a todos los grupos"
			)
	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveAll() {
		
		Iterable <Grupo> result = grupoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@ApiOperation(
			value = "Recuperar grupo",
			notes = "Permite recuperar un grupo mediante su id, el cual es único"
			)
	@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieve(@PathVariable("id") @Valid Integer id) {
		
		log.info("Buscando al grupo con id "+id);
		
		Optional<Grupo> grupo = grupoService.retrieve(id);

		if(grupo.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo con id "+id+" no encontrado");
		}
	}
	
	@ApiOperation(
			value = "Actualizar grupo",
			notes = "Permite actualizar la información de un grupo, identificandolo por su id, el cual no debe ser modificado"
			)
	@PutMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody @Valid Grupo grupoActualizado) {
		
		log.info("Actualizando al grupo con id "+id);
					
		//NO SE MODIFICA EL ID
		grupoActualizado.setId(id);
			
		//MANDA LA ACTUALIZACION DEL GRUPO
		grupoActualizado = grupoService.update(grupoActualizado);
			
		//SI EL OBJETO DEVUELTO NO ES NULL, EL GRUPO SE ACTUALIZO CORRECTAMENTE
		if(grupoActualizado != null) {
			return ResponseEntity.status(HttpStatus.OK).body(grupoActualizado);
		} 
		//SI EL OBJUETO DEVUELTO ES NULL, EL GRUPO QUE SE INTENTA ACTUALIZAR NO EXISTE
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo con id "+id+" no encontrado. No se ha podido actualizar grupo");
		}
	}
	
	@ApiOperation(
			value = "Borrar grupo",
			notes = "Permite borrar a un grupo, siendo identificado por su id"
			)
	@DeleteMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		
		log.info("Borrando al grupo con id "+id);
			
		//SI RECIBE TRUE EL GRUPO SE ELIMINÓ CORRECTAMENTE
		if(grupoService.delete(id)) {
			return ResponseEntity.status(HttpStatus.OK).body("Grupo con id "+id+" eliminado correctamente");
		} 
		// SI RECIBE FALSE, EL GRUPO NO EXISTE, POR LO TANTO NO PUEDE SER ELIMINADO
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo con id "+id+" no encontrado. No se ha podido eliminar");
		}
	}
}
