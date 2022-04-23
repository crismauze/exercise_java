package ec.edu.cbenalcazar.exercisejava.pojo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import ec.edu.cbenalcazar.exercisejava.exceptions.GrafoException;
import ec.edu.cbenalcazar.exercisejava.exceptions.RutaException;
import lombok.Getter;
import lombok.Setter;

public class Grafo {
	@Getter
	@Setter
	public Set<Ciudad> ciudades;
	
	public Grafo(String defineGrafo) throws GrafoException {
		if (Objects.isNull(defineGrafo) || defineGrafo.length() < 3) {
			throw new GrafoException("Grafo null o incorrecto");
		}

		ciudades = new HashSet<>();
		Set<Ruta> rutasSet = new HashSet<>();
		String[] rutasString = defineGrafo.split(",");

		// creo las rutas y ciudades vacias
		for (String rutaString : rutasString) {
			try {
				Ruta rutaTemp = new Ruta(rutaString);
				rutasSet.add(rutaTemp);
				// se agregan
				ciudades.add(rutaTemp.getCiudadOrigen());
				ciudades.add(rutaTemp.getCiudadDestino());
			} catch (RutaException e) {
				System.out.println("Error en la ruta, causa: " + e.getMessage());
			}
		}

		if (rutasSet.isEmpty() || ciudades.isEmpty()) {
			throw new GrafoException("No hay rutas definidas, grafo: " + defineGrafo);
		}

		// proceso las rutas obtenidas
		for (Ruta rutaTemp : rutasSet) {
			Ciudad ciudadOrigen = ciudades.stream().filter(c -> c.equals(rutaTemp.getCiudadOrigen())).findAny().get();

			Ciudad ciudadDestino = ciudades.stream().filter(c -> c.equals(rutaTemp.getCiudadDestino())).findAny().get();

			rutaTemp.setCiudadOrigen(ciudadOrigen);
			rutaTemp.setCiudadDestino(ciudadDestino);

			if (Objects.isNull(ciudadOrigen.getRutaSet())) {
				ciudadOrigen.setRutaSet(new HashSet<Ruta>());
			}
			ciudadOrigen.getRutaSet().add(rutaTemp);

			ciudades.add(ciudadOrigen);
			ciudades.add(ciudadDestino);
		}
	}
	
	
	// metodo para buscar la ruta indicada
	public void buscarRutas(Ciudad origen, Ciudad destino, Queue<Ruta> rutasVisitadas,
			Set<Queue<Ruta>> rutasEncontradas) {

		for (Ruta rutaTmp : origen.getRutaSet()) {
			Queue<Ruta> rutasTemporales = new LinkedList<>(rutasVisitadas);

			// si encuentra la ruta, se sale del bucle
			if (rutasVisitadas.contains(rutaTmp)) {
				break;
			}

			rutasTemporales.add(rutaTmp);

			// cuando ya se encuentra la ciudad destino
			if (rutaTmp.getCiudadDestino().equals(destino)) {
				rutasEncontradas.add(rutasTemporales);
				break;
			} else {
				buscarRutas(rutaTmp.getCiudadDestino(), destino, rutasTemporales, rutasEncontradas);
			}
		}
	}
	
	
	// metodo para calcular la distancia de una ruta
	public Integer calcularDistanciaDeRuta(String definicionRuta) throws GrafoException, RutaException {
		Queue<Ruta> rutaQueue = validarRuta(definicionRuta);
		return rutaQueue.stream().map(Ruta::getDistancia).collect(Collectors.toList()).stream().reduce(0, Integer::sum);
	}
	
	
	// metodo para validar la ruta
	public Queue<Ruta> validarRuta(String definicionRuta) throws GrafoException, RutaException {
		Queue<Ruta> rutaQueue = new LinkedList<>();
		String[] ciudades = definicionRuta.split("-");
		for (int indice = 0; indice < ciudades.length - 1; indice++) {
			Ciudad ciudadOrigen = obtenerCiudadDeGrafoPorNombre(ciudades[indice]);
			Ciudad ciudadDestino = obtenerCiudadDeGrafoPorNombre(ciudades[indice + 1]);
			Ruta rutaGrafo = ciudadOrigen.validaRutaDestino(ciudadDestino, definicionRuta);
			rutaQueue.add(rutaGrafo);
		}
		return rutaQueue;
	}
	
	
	// metodo para obtener la ciudad del grafo
	public Ciudad obtenerCiudadDeGrafoPorNombre(String nombreCiudad) throws GrafoException {
        Ciudad ciudad = new Ciudad(nombreCiudad);
		Optional<Ciudad> ciudadOptional = ciudades.stream().filter(c -> c.equals(ciudad)).findAny();
        if (ciudadOptional.isPresent()) {
            return ciudadOptional.get();
        } else {
            throw new GrafoException("Ciudad no encontrada en grafo: " + nombreCiudad);
        }
    }
	
	
	// metodo para calcular la distancia
	public int calcularDistanciaCorta(Set<Queue<Ruta>> rutas) {
        // se asigna distancia con valor alto
		int distanciaReferencia = 100;
        for (Queue<Ruta> rutasQueue : rutas) {
            int distancia = calcularDistanciaRutasCola(rutasQueue);
            if (distancia < distanciaReferencia) {
                distanciaReferencia = distancia;
            }
        }
        return distanciaReferencia;
    }
	
	
	// metodo para calcular la distancia de una ruta
	public int calcularDistanciaRutasCola(Queue<Ruta> rutaQueue) {
        return rutaQueue.stream().map(
        		r -> r.getDistancia()).collect(Collectors.toList()).stream().reduce(0, Integer :: sum);
    }

}
