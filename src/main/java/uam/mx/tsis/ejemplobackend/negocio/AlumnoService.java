package uam.mx.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.hibernate.validator.cfg.context.ReturnValueTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.datos.AlumnoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Clase que contiene la lógica de negocio del manejo de alumnos
 * @author Brianda Garcia
 *
 */
@Service
@Slf4j
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * Metodo que permite crear nuevos alumnos
	 * @param nuevoAlumno El alumno que se desea crear en el sistema. Es un objeto de tipo Alumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
				
		//REGLA DE NEGOCIO: No se puede crear a más de un alumno con la misma matricula
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		log.info("Alumno con matricula "+nuevoAlumno.getMatricula()+" AlumnoOpt presente? "+alumnoOpt.isPresent());

		if(!alumnoOpt.isPresent()) {
			
			log.info("Voy a guardar a alumno "+nuevoAlumno);
			
			Alumno returnAlumno = alumnoRepository.save(nuevoAlumno);
			
			log.info("Voy a regresar a alumno "+returnAlumno);

			return returnAlumno;
		} else {
			return null;
		}
	}

	/**
	 * Recupera todos los alumnos de la BD
	 * @return Regresa una lista con todos los alumnos en la BD
	 */
	public Iterable <Alumno> retrieveAll() {
		return alumnoRepository.findAll();
	}
	
	/**
	 * Recupera un alumno de la BD
	 * @param matricula identificador del alumno que se desea recuperar
	 * @return regresa el alumno identificado por su matricula
	 */
	public Optional<Alumno> retrieve(Integer matricula) {
		return alumnoRepository.findById(matricula);
	}
	
	
	/**
	 * Actualiza un alumno existente en la BD
	 * @param alumnoActualizado informacion actualizada del alumno
	 * @return regresa el alumno actualizado, null si el alumno no existia
	 */
	public Alumno update(Alumno alumnoActualizado) {
		
		//REGLA DE NEGOCIO: NO SE PUEDE ACTUALIZAR UN ALUMNO QUE NO EXISTE
		Optional <Alumno> alumno = alumnoRepository.findById(alumnoActualizado.getMatricula());
		
		//SI EL ALUMNO EXISTE, SE ACTUALIZA Y REGRESA AL ALUMNO ACTUALIZADO
		if(alumno.isPresent()) {
			return alumnoRepository.save(alumnoActualizado);
		}
		//SI EL ALUMNO NO EXISTE, NO SE PUEDE ACTUALIZAR, REGRESA NULL
		else {
			return null;
		}
	}
	
	
	/**
	 * ELIMINA UN ALUMNO EXISTENTE EN LA BD
	 * @param matricula matricula del alumno que se desea eliminar
	 * @return True si el alumno se eliminó, false si no
	 */
	public boolean delete(Integer matricula) {
		
		//REGLA DE NEGOCIO: NO SE PUEDE ELIMINAR UN ALUMNO QUE NO EXISTE
		Optional <Alumno> alumno = alumnoRepository.findById(matricula);
				
		//SI EL ALUMNO EXISTE, SE ELIMINA Y REGRESA TRUE
		if(alumno.isPresent()) {
			alumnoRepository.deleteById(matricula);
			
			return true;
		}
		//SI EL ALUMNO NO EXISTE REGRESA FALSE
		else {
			return false;
		}
	}
}