package es.unican.gasolineras.repository;

/**
 * A repository to retrieve gas stations
 */
public interface IGasolinerasRepository {

        /**
         * Asynchronously requests a list of gas stations.
         * @param cb the callback that will asynchronously process the returned gas stations
         * @param provincia the province to filter the gas stations
         * @param municipio the municipality to filter the gas stations
         */
        public void requestGasolineras(ICallBack cb, String provincia, String municipio);

}
