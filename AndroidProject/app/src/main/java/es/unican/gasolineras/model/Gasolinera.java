package es.unican.gasolineras.model;

import static es.unican.gasolineras.common.Horario.estaAbierto;

import com.google.gson.annotations.SerializedName;

import es.unican.gasolineras.common.DataAccessException;
import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

/**
 * A Gas Station.
 *
 * Properties are defined in the <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestres#response-json">API</a>
 *
 * The #SerializedName annotation is a GSON annotation that defines the name of the property
 * as defined in the json response.
 *
 * Getters are automatically generated at compile time by Lombok.
 */
@Parcel
@Getter
@Setter
public class Gasolinera {

    @SerializedName("IDEESS")                       protected String id;

    @SerializedName("Rótulo")                       protected String rotulo;
    @SerializedName("C.P.")                         protected String cp;
    @SerializedName("Dirección")                    protected String direccion;
    @SerializedName("Municipio")                    protected String municipio;
    @SerializedName("Provincia")                    protected String provincia;
    @SerializedName("Horario")                      protected String horario;

    @SerializedName("Precio Gasoleo A")             protected double gasoleoA;
    @SerializedName("Precio Gasolina 95 E5")        protected double gasolina95E5;

    private String estado;

    /**
     * Calcula el precio medio de los carburantes de la gasolinera.
     * Si uno de los dos precios es 0, se devuelve el otro.
     * Si ambos precios son 0, se devuelve 0.
     * En caso contrario, se devuelve la media de los dos precios.
     * @return el precio medio de los carburantes de la gasolinera
     */
    public double calculateSummarizedPrice() {
        // Primero verificamos si ambos precios son exactamente 0
        if (gasoleoA == 0 && gasolina95E5 == 0) {
            return 0;
        }
        // Si solo gasoleoA es negativo, devolvemos gasolina95E5
        else if (gasoleoA < 0) {
            return gasolina95E5;
        }
        // Si solo gasolina95E5 es negativo, devolvemos gasoleoA
        else if (gasolina95E5 < 0) {
            return gasoleoA;
        }
        // Si ambos precios son positivos, calculamos la media ponderada
        else {
            return (gasoleoA + gasolina95E5 * 2) / 3;
        }
    }
    /**
     * Comprueba si la gasolinera está abierta en el horario actual.
     *
     * @param horario el horario de la gasolinera
     * @return el estado de la gasolinera
     * @throws IllegalArgumentException si el horario no es válido
     */
    public String compruebaEstado(String horario) throws IllegalArgumentException, DataAccessException {
        this.estado = estaAbierto(horario);
        return estaAbierto(horario);
    }
}
