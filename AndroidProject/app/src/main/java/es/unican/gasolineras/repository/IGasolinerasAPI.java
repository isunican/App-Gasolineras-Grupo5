package es.unican.gasolineras.repository;

import java.util.List;

import es.unican.gasolineras.model.GasolinerasResponse;
import es.unican.gasolineras.model.Municipio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Gasolineras <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">API</a>
 * using Retrofit
 */
public interface IGasolinerasAPI {

    /**
     * Retrieve gas stations filtered by "comunidad autónoma"
     * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestresFiltroCCAA">API</a>
     *
     * @return retrofit call object
     */
    @GET("EstacionesTerrestres/")
    Call<GasolinerasResponse> gasolineras();

    @GET("Listados/MunicipiosPorProvincia/{IDPROVINCIA}")
    Call<List<Municipio>> municipiosPorProvincia(@Path("IDPROVINCIA") String idProvincia);
}
