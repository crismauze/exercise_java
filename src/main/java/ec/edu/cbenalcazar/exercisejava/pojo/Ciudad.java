package ec.edu.cbenalcazar.exercisejava.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Ciudad {
	
	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private List<Ruta> rutaList;

	public Ciudad(String nombre) {
		this.nombre = nombre;
	}
	
	
	

}
