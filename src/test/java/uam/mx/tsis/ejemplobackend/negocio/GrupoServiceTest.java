package uam.mx.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.util.ArrayIterator;

import uam.mx.tsis.ejemplobackend.datos.GrupoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class) //USO DE MOCKITO
public class GrupoServiceTest {

	@Mock
	private GrupoRepository grupoRepositoryMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	@Test
	public void testSuccessfulCreate() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");		
				
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO GUARDA UN GRUPO
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
				
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		grupo = grupoService.create(grupo);
				
		//AQUI COMPRUEBO EL RESULTADO
		assertNotNull(grupo); //Probar que la referencia a grupo no es nula		
	}
	
	@Test
	public void testSuccessfulRetrieveAll() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");		
				
		Grupo[] grupos = {grupo};
				
		Iterable<Grupo> gruposI =new ArrayIterator(grupos);
		
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO RECUPERA TODOS LOS GRUPOS
		when(grupoRepositoryMock.findAll()).thenReturn(gruposI);
				
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		Iterable<Grupo> result = grupoService.retrieveAll();
				
		//AQUI COMPRUEBO EL RESULTADO
		assertEquals(result, gruposI); //Probar que son iguales	
	}
	
	@Test
	public void testSuccessfulRetrieve() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO RECUPERA UN GRUPO
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.of(grupo));
						
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		Optional<Grupo> grupoOpt = grupoService.retrieve(1);
						
		//AQUI COMPRUEBO EL RESULTADO
		assertEquals(grupoOpt,Optional.of(grupo)); //Probar que son iguales
	}
	
	@Test
	public void testSuccessfulUpdate() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO RECUPERA UN GRUPO
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.of(grupo));
				
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO RECUPERA UN GRUPO
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
				
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		Grupo grupoActualizado = grupoService.update(grupo);
						
		//AQUI COMPRUEBO EL RESULTADO
		assertEquals(grupoActualizado,grupo); //Probar que son iguales
	}
	
	@Test
	public void testUnsuccessfulUpdate() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//SIMULA LO QUE HARIA EL GRUPO REPOSITORY REAL CUANDO RECUPERA UN GRUPO
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
				
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		Grupo grupoActualizado = grupoService.update(grupo);
						
		//AQUI COMPRUEBO EL RESULTADO
		assertNull(grupoActualizado); //Probar que es nulo porque el grupo que se intenta actualizar no existe.
	}
	
	@Test
	public void testSuccessfulAddStudentToGroup() {
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");		
						
		//Stubbing para el alumno service
		when(alumnoServiceMock.retrieve(12345678)).thenReturn(Optional.of(alumno));
		
		//Stubbing para grupo repository
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.of(grupo));
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);

		assertTrue(result);
		
		assertEquals(grupo.getAlumnos().get(0), alumno);	
	}
	
	@Test
	public void testUnsuccessfulAddStudentToGroup() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");		
						
		//Stubbing para el alumno service
		when(alumnoServiceMock.retrieve(12345678)).thenReturn(Optional.of(alumno));
		
		//Stubbing para grupo repository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);

		assertFalse(result);
	}
}
