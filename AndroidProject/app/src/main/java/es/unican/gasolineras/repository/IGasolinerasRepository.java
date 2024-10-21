package es.unican.gasolineras.repository;

/**
 * A repository to retrieve gas stations
 */
public interface IGasolinerasRepository {

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         */
        public void requestGasolineras(ICallBack cb);

}
