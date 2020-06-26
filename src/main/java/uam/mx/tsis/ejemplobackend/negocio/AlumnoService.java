package uam.mx.tsis.ejemplobackend.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uam.mx.tsis.ejemplobackend.datos.AlumnoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;


@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * Crea un nuevo alumno
	 * @param nuevoAlumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		//REGLA DE NEGOCIO: No se puede crear a más de un alumno con la misma matricula
		Alumno alumno = alumnoRepository.findByMatricula(nuevoAlumno.getMatricula());
		
		if(alumno == null) {
			return alumnoRepository.save(nuevoAlumno);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return Regresa una lista con todos los alumnos en la BD
	 */
	public List<Alumno> retrieveAll() {
		return alumnoRepository.find();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return regresa el alumno identificado por su matricula
	 */
	public Alumno retrieve(Integer matricula) {
		return alumnoRepository.findByMatricula(matricula);
	}
	
	/**
	 * 
	 * @param matricula alumno que se desea actualizar
	 * @param alumnoActualizado informacion actualizada del alumno
	 * @return regresa el alumno actualizado
	 */
	public Alumno update(Integer matricula, Alumno alumnoActualizado) {
		return alumnoRepository.set(matricula, alumnoActualizado);
	}
	
	/**
	 * 
	 * @param matricula matricula del alumno que se desea eliminar
	 * @return regresa el alumno eliminado
	 */
	public Alumno delete(Integer matricula) {
		return alumnoRepository.remove(matricula);
	}
}