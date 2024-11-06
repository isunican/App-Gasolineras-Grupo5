package es.unican.gasolineras.repository;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Municipio;

/**
 * A repository to retrieve gas stations
 */
public interface IGasolinerasRepository {

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         */
        public void requestGasolineras(ICallBack<Gasolinera> cb);

        public void requestMunicipiosPorProvincia(ICallBack<Municipio> cb, String idProvincia);
}
