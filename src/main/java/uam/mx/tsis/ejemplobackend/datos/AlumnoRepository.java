package uam.mx.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Se encarga de almacenar, recuperar, actualizar y eliminar alumnos
 * @author Brianda Garcia
 *
 */
@Component
@Slf4j
public class AlumnoRepository {

	//La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap<>();
	
	/**
	 * Guarda en la BD
	 * @param nuevoAlumno alumno que se desea almacenar/guardar en la BD
	 */
	public Alumno save(Alumno nuevoAlumno) {
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;
	}
	
	/**
	 * Recupera de la BD al alumno
	 * @param matricula matricula del alumno que se desea recuperar
	 * @return regresa el alumno que es un Objeto de tipo Alumno
	 */
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}
	
	/**
	 * Recupera de la BD a todos los alumnos
	 * @return regresa una lista de los alumnos que son de tipo Alumno
	 */
	public List <Alumno> find(){
		return new ArrayList <> (alumnoRepository.values());
	}
	
	/**
	 * Actualiza un alumno en la BD
	 * @param matricula Matricula del alumno que se desea actualizar
	 * @param alumnoActualizado Informacion actualizada del alumno
	 * @return Devuelve el alumno actualizado que es un objeto de tipo Alumno
	 */
	public Alumno set(Integer matricula, Alumno alumnoActualizado) {
		return alumnoRepository.put(matricula, alumnoActualizado);
	}
	
	/**
	 * Elimina un alumno de la BD
	 * @param matricula Matricula del alumno que se desea eliminar
	 * @return devuelve el alumno eliminado que es un objeto de tipo Alumno
	 */
	public Alumno remove(Integer matricula) {
		return alumnoRepository.remove(matricula);
	}
}