package es.unican.gasolineras.repository;

import es.unican.gasolineras.model.GasolinerasResponse;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.GET;

/**
 * Gasolineras <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">API</a>
 * using Retrofit
 */
public interface IGasolinerasAPI {

    /**
     * Retrieve gas stations filtered by "comunidad autónoma"
     * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help/operations/PreciosEESSTerrestresFiltroCCAA">API</a>*
     *
     * @return retrofit call object
     */
    @GET("EstacionesTerrestres/")
    Call<GasolinerasResponse> gasolineras();
}