package uam.mx.tsis.ejemplobackend.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uam.mx.tsis.ejemplobackend.datos.AlumnoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Clase que contiene la lógica de negocio del manejo de alumnos
 * @author Brianda Garcia
 *
 */
@Service
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
		
		if(!alumnoOpt.isPresent()) {
			return alumnoRepository.save(nuevoAlumno);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return Regresa una lista con todos los alumnos en la BD
	 */
	public Iterable <Alumno> retrieveAll() {
		return alumnoRepository.findAll();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return regresa el alumno identificado por su matricula
	 */
	public Optional<Alumno> retrieve(Integer matricula) {
		return alumnoRepository.findById(matricula);
	}
	
	
	/**
	 * 
	 * @param matricula alumno que se desea actualizar
	 * @param alumnoActualizado informacion actualizada del alumno
	 * @return regresa el alumno actualizado
	 */
	public Alumno update(Alumno alumnoActualizado) {
		return alumnoRepository.save(alumnoActualizado);
	}
	
	
	/**
	 * 
	 * @param matricula matricula del alumno que se desea eliminar
	 * @return regresa el alumno eliminado
	 */
	public void delete(Integer matricula) {
		alumnoRepository.deleteById(matricula);
	}
}