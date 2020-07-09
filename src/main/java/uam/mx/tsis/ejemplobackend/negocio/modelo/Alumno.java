package uam.mx.tsis.ejemplobackend.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity //INDICA QUE HAY QUE PERSISTIR EN BD
@EqualsAndHashCode
public class Alumno {

	@NotBlank
	@ApiModelProperty(notes = "Nombre del alumno", required = true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes = "Carrera del alumno", required = true)
	private String carrera;
	
	@NotNull
	@ApiModelProperty(notes = "Matricula del alumno", required = true)
	@Id //INDICA QUE ESTE ES LLAVE PRIMARIA
	private Integer matricula;
}