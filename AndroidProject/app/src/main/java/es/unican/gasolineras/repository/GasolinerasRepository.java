package es.unican.gasolineras.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nonnull;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.GasolinerasResponse;
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
     * @see IGasolinerasRepository#requestGasolineras(ICallBack, String, String)
     * @param cb the callback that will asynchronously process the returned gas stations
     * @param provincia the province to filter the gas stations
     *                  (if null, no filter is applied)
     * @param localidad the locality to filter the gas stations
     *                  (if null, no filter is applied)
     */
    @Override
    public void requestGasolineras(ICallBack cb, String provincia, String localidad) {
        Call<GasolinerasResponse> call = null;
        if (provincia == null) {
            call = GasolinerasService.api.gasolineras();
        } else{
            call = GasolinerasService.api.gasolinerasPorProvincia(provincia);
        }
        call.enqueue(new Callback<GasolinerasResponse>() {
            @Override
            public void onResponse(@Nonnull Call<GasolinerasResponse> call, @Nonnull Response<GasolinerasResponse> response) {
                GasolinerasResponse body = response.body();
                assert body != null;  // para evitar advertencias

                if (provincia != null && localidad != null) {
                    List<Gasolinera> encontradas = body.getGasolineras();

                    Iterator<Gasolinera> iterator = encontradas.iterator();
                    while (iterator.hasNext()) {
                        Gasolinera g = iterator.next();
                        if (!g.getLocalidad().equalsIgnoreCase(localidad)) {
                            iterator.remove();
                        }
                    }
                    cb.onSuccess(encontradas);
                } else {
                    cb.onSuccess(body.getGasolineras());
                }
            }

            @Override
            public void onFailure(@Nonnull Call<GasolinerasResponse> call, @Nonnull Throwable t) {
                cb.onFailure(t);
            }
        });
    }
}
