package es.unican.gasolineras.repository;

import java.util.List;

import javax.annotation.Nonnull;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.GasolinerasResponse;
import es.unican.gasolineras.model.Municipio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of @link{IGasolinerasRepository} that access the real
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">Gasolineras API</a>
 */
public class GasolinerasRepository implements IGasolinerasRepository {

    /** Since this class does not have any state, it can be a singleton */
    public static final IGasolinerasRepository INSTANCE = new GasolinerasRepository();

    /** Singleton pattern with private constructor */
    private GasolinerasRepository() {}

    /**
     * Request gas stations from the Gasolineras real API.
     * @see IGasolinerasRepository#requestGasolineras(ICallBack)
     * @param cb the callback that will asynchronously process the returned gas stations
     */
    @Override
    public void requestGasolineras(ICallBack<Gasolinera> cb) {
        Call<GasolinerasResponse> call = GasolinerasService.api.gasolineras();
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // to avoid warning in the following line
                cb.onSuccess(body.getGasolineras());
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }

    /**
     * Request the 'municipios' of a given 'provincia'
     * @see IGasolinerasRepository#requestMunicipiosPorProvincia(ICallBack, String)
     * @param cb the callback that will asynchronously process the returned gas stations
     * @param idProvincia id de la provincia.
     */
    @Override
    public void requestMunicipiosPorProvincia(ICallBack<Municipio> cb, String idProvincia) {
        Call<List<Municipio>> call = GasolinerasService.api.municipiosPorProvincia(idProvincia);
        call.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(@Nonnull Call<List<Municipio>> call, @Nonnull Response<List<Municipio>> response) {
                List<Municipio> municipios = response.body();
                cb.onSuccess(municipios);
            }

            @Override
            public void onFailure(@Nonnull Call<List<Municipio>> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }
}