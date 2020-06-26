package uam.mx.tsis.ejemplobackend.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Alumno {

	@NotBlank
	private String nombre;
	
	@NotBlank
	private String carrera;
	
	@NotNull
	private Integer matricula;
}