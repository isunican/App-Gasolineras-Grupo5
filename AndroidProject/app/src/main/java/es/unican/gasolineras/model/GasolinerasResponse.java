package es.unican.gasolineras.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

/**
 * Model that represents the response obtained from the
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestres#response-json">Gasolineras REST API</a>
 *
 * The API returns an object with these 4 properties: "fecha", "nota", "gasolineras", "consulta".
 * The actual list of gas stations is the property "gasolineras".
 *
 * The #SerializedName annotation is a GSON annotation that defines the name of the property
 * as defined in the json response.
 *
 * Getters are automatically generated at compile time by Lombok.
 */
@Getter
public class GasolinerasResponse {

    @SerializedName(value="Fecha")              private String fecha;
    @SerializedName(value="Nota")               private String nota;
    @SerializedName(value="ListaEESSPrecio")    private List<Gasolinera> gasolineras;
    @SerializedName(value="ResultadoConsulta")  private String consulta;

    /**
     * Returns the number of gas stations in the list
     * @return the number of gas stations in the list
     */
    public int getGasolinerasCount() {
        return gasolineras != null ? gasolineras.size() : 0;
    }

}
