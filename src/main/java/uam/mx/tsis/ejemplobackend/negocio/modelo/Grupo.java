package uam.mx.tsis.ejemplobackend.negocio.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity //INDICA QUE HAY QUE PERSISTIR EN BD
public class Grupo {

	@Id
	@GeneratedValue //AUTOGENERA UN ID UNICO
	private Integer id;
	
	@NotBlank
	private String clave;
	
	@OneToMany(targetEntity = Alumno.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "GrupoID") //NO CREA TABLA INTERMEDIA
	private List <Alumno> alumnos = new ArrayList<>();
	
	public boolean addAlumno(Optional<Alumno> alumnoOpt) {
		Alumno alumno = alumnoOpt.get();
		
		return alumnos.add(alumno);
	}
}
