package es.unican.gasolineras.model;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("Localidad")                    protected String localidad;
    @SerializedName("Horario")                      protected String horario;

    @SerializedName("Precio Gasoleo A")             protected double gasoleoA;
    @SerializedName("Precio Gasolina 95 E5")        protected double gasolina95E5;

    public double calculateSummarizedPrice(){
        if (gasoleoA == 0 || gasoleoA < 0) {
            return gasolina95E5;
        }
        else if (gasolina95E5 == 0 || gasolina95E5 < 0) {
            return gasoleoA;
        }
        // en caso de que ambos precios sean 0, se devuelve 0
        else if (gasolina95E5 == 0 && gasoleoA == 0) {
            return 0;
        } else {
            return (gasoleoA + gasolina95E5 * 2) / 3;
        }
    }

}
