package ec.edu.cbenalcazar.exercisejava.pojo;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ec.edu.cbenalcazar.exercisejava.exceptions.RutaException;
import lombok.Getter;
import lombok.Setter;

public class Ciudad {
	
	@Getter
	@Setter
	private String nombre;
	@Getter
	@Setter
	private Set<Ruta> rutaSet;

	public Ciudad(String nombre) {
		this.nombre = nombre;
	}
	
	public Ruta validaRutaDestino(Ciudad ciudadDestino, String rutaCiudad) throws RutaException {
		Optional<Ruta> rutaOptional = rutaSet.stream().filter(
				c -> c.getCiudadDestino().equals(ciudadDestino)).findAny();
		if (rutaOptional.isPresent()) {
			return rutaOptional.get();
		} else {
			throw new RutaException("No existe ruta a la ciudad: " + rutaCiudad);
		}
	}

	@Override
	public String toString() {
		return "Ciudad [nombre=" + nombre + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (Objects.nonNull(o) && o instanceof Ciudad) {
            Ciudad otra = (Ciudad) o;
            return Objects.equals(this.nombre, otra.nombre);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
	
	

}
