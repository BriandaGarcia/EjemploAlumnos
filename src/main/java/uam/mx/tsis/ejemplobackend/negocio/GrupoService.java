package uam.mx.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uam.mx.tsis.ejemplobackend.datos.GrupoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Grupo;

@Service
@Slf4j
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	/**
	 * Metodo que permite crear un nuevo grupo
	 * @param grupo Grupo nuevo que se creara en la BD 
	 * @return devuelve el grupo creado
	 */
	public Grupo create(Grupo grupo) {
		//Validar reglas de negocio previas a la creacion
		
		return grupoRepository.save(grupo);
	}
	
	/**
	 * Metodo que permite recuperar todos los grupos de la BD
	 * @return Una lista de todos los grupos recuperados
	 */
	public Iterable<Grupo> retrieveAll(){
		return grupoRepository.findAll();
	}
	
	/**
	 * Metodo que permite recuperar un grupo por su id
	 * @param id id del grupo que se desea recuperar
	 * @return El grupo recuperado. Un optional de tipo Grupo que puede o no contener el grupo
	 */
	public Optional<Grupo> retrieve(Integer id) {
		return grupoRepository.findById(id);
	}
	
	/**
	 * Metodo que permite actualizar un grupo existente
	 * @param grupoActualizado Información del grupo que se desea actualizar
	 * @return El grupo actualizado si se actualizó correctamente, null en caso contrario
	 */
	public Grupo update(Grupo grupoActualizado) {
		
		//REGLA DE NEGOCIO: NO SE PUEDE ACTUALIZAR UN GRUPO QUE NO EXISTE
		Optional <Grupo> grupo = grupoRepository.findById(grupoActualizado.getId());
				
		//SI EL GRUPO EXISTE, SE ACTUALIZA Y REGRESA AL GRUPO ACTUALIZADO
		if(grupo.isPresent()) {
			return grupoRepository.save(grupoActualizado);
		}
		//SI EL GRUPO NO EXISTE, NO SE PUEDE ACTUALIZAR, REGRESA NULL
		else {
			return null;
		}
	}
	
	/**
	 * Metodo que permite eliminar un grupo
	 * @param id id del grupo que se desea eliminar
	 * @return true si el grupo existe y se eliminó correctamente, false si no
	 */
	public boolean delete(Integer id) {
		
		//REGLA DE NEGOCIO: NO SE PUEDE ELIMINAR UN GRUPO QUE NO EXISTE
		Optional <Grupo> grupo = grupoRepository.findById(id);
						
		//SI EL ALUMNO EXISTE, SE ELIMINA Y REGRESA TRUE
		if(grupo.isPresent()) {
			grupoRepository.deleteById(id);
					
			return true;
		}
		//SI EL ALUMNO NO EXISTE REGRESA FALSE
		else {
			return false;
		}
	}
	
	/**
	 * Metodo que permite agregar un alumno a un grupo 
	 * @param id el id del grupo
	 * @param matricula la matricula del alumno 
	 * @return true si se agregó correctamente, false si no
	 */
	public boolean addStudentToGroup(Integer id, Integer matricula) {
		
		log.info("Agregando alumno con matricula "+matricula+" al grupo "+id);
		
		//RECUPERAMOS PRIMERO AL ALUMNO
		Optional <Alumno> alumno = alumnoService.retrieve(matricula);
		
		//RECUPERAMOS AL GRUPO
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		
		//VERIFICAMOS QUE EL ALUMNO Y EL GRUPO EXISTAN
		if(!grupoOpt.isPresent() || !alumno.isPresent()) {
			log.info("No se encontró alumno o grupo");
			
			return false;
		}
		
		//AGREGO EL ALUMNO AL GRUPO
		Grupo grupo = grupoOpt.get();
		grupo.addAlumno(alumno);
		
		//PERSISTIR EL CAMBIO
		grupoRepository.save(grupo);
		
		return true;
	}
}