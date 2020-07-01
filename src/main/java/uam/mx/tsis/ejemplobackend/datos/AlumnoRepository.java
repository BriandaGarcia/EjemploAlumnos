package uam.mx.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;

import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Se encarga de almacenar, recuperar, actualizar y eliminar alumnos
 * @author Brianda Garcia
 *
 */
public interface AlumnoRepository extends CrudRepository<Alumno, Integer>{//ENTIDAD, TIPO DE LA LLAVE PRIMARIA
}