package uam.mx.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uam.mx.tsis.ejemplobackend.datos.AlumnoRepository;
import uam.mx.tsis.ejemplobackend.negocio.modelo.Alumno;

@ExtendWith(MockitoExtension.class) //USO DE MOCKITO
public class AlumnoServiceTest {

	@Mock
	private AlumnoRepository alumnoRepositoryMock; //MOCK GENERADO POR MOCKITO
	
	@InjectMocks
	private AlumnoService alumnoService; //LA UNIDAD QUE QUIERO PROBAR
	
	@Test
	public void testSuccessfulCreate() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		//SIMULA LO QUE HARIA EL ALUMNO REPOSITORY REAL CUANDO LE PASAN UNA MATRICULA DE UN ALUMNO QUE NO HA SIDO GUARDADO
		when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(null));
		
		//SIMULA LO QUE HARIA EL ALUMNO REPOSITORY REAL CUANDO GUARDA UN ALUMNO
		when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
		
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		alumno = alumnoService.create(alumno);
		
		//AQUI COMPRUEBO EL RESULTADO
		assertNotNull(alumno); //Probar que la referencia a alumno no es nula	
	}
	
	@Test
	public void testUnsuccessfulCreate() {
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computacion");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		//SIMULA LO QUE HARIA EL ALUMNO REPOSITORY REAL CUANDO LE PASAN UNA MATRICULA DE UN ALUMNO QUE NO HA SIDO GUARDADO
		when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(alumno));
		
		//AQUI EJECUTO A LA UNIDAD QUE QUIERO PROBAR
		alumno = alumnoService.create(alumno);
		
		//AQUI COMPRUEBO EL RESULTADO
		assertNull(alumno); //Probar que la referencia a alumno es nula porque el alumno ya existia
	}
}
