package uam.mx.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
