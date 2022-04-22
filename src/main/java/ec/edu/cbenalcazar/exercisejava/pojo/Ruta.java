package ec.edu.cbenalcazar.exercisejava.pojo;

import java.util.Objects;

import ec.edu.cbenalcazar.exercisejava.exceptions.RutaException;
import lombok.Getter;
import lombok.Setter;

public class Ruta {

	@Getter
	@Setter
	private Ciudad ciudadOrigen;
	@Getter
	@Setter
	private Ciudad ciudadDestino;
	@Getter
	@Setter
	private Integer distancia;

	// constructor para crear ruta-nodo
	public Ruta(String rutaNodo) throws RutaException {
		// se valida ruta null o tamanio
		if (Objects.isNull(rutaNodo) || rutaNodo.length() < 3) {
			throw new RutaException("La rutaNodo es null o mal definida: " + rutaNodo);
		}
		// se toma la ciudad inicio
		ciudadOrigen = new Ciudad(rutaNodo.substring(0, 1));
		// se toma la ciudad fin
		ciudadDestino = new Ciudad(rutaNodo.substring(1, 2));
		try {
			// se obtiene la distancia
			distancia = Integer.parseInt(rutaNodo.substring(2));
		} catch (NumberFormatException e) {
			// error al obtener la distancia
			throw new RutaException(
					"Error al obtener la distancia, rutaNodo: " + rutaNodo + " causa: " + e.getMessage(), e);
		}
	}

	@Override
	public String toString() {
		return "Ruta [ciudadOrigen=" + ciudadOrigen + ", ciudadDestino=" + ciudadDestino + ", distancia=" + distancia
				+ "]";
	}

}
