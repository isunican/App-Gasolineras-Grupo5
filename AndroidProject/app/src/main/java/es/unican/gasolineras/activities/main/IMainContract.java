package es.unican.gasolineras.activities.main;

import java.util.List;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.model.Combustible;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Municipio;
import es.unican.gasolineras.model.Orden;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The Presenter-View contract for the Main activity.
 * The Main activity shows a list of gas stations.
 */
public interface IMainContract {

    /**
     * Methods that must be implemented in the Main Presenter.
     * Only the View should call these methods.
     */
    public interface Presenter {

        /**
         * Links the presenter with its view.
         * Only the View should call this method
         * @param view
         */
        public void init(View view);

        /**
         * The presenter is informed that a gas station has been clicked
         * Only the View should call this method
         * @param station the station that has been clicked
         */
        public void onStationClicked(Gasolinera station);

        /**
         * The presenter is informed that the Info item in the menu has been clicked
         * Only the View should call this method
         */
        public void onMenuInfoClicked();

        /**
         * The presenter is informed that the Filters item in the menu has been clicked
         * Only the View should call this method
         */
        public void onFilterButtonClicked();

        /**
         * Filtra y muestra la lista de gasolineras segun los filtros indicados.
         *
         * @param provincia La provincia por la que filtrar. Puede ser "-" para indicar que no se
         *                  debe filtar por provincia.
         * @param municipio El municipio por el que filtrar. Puede ser "-" para indicar que no se
         *          *                  debe filtar por municipio.
         * @param companhia La compnhia por la que filtrar. Puede ser "-" para indicar que no se
         *          *                  debe filtar por companhia.
         * @param abierto Un boolean que indica si se debe filtar por gasolineras abiertas.
         * @throws DataAccessException Si ocurre un error al acceder a los datos
         */
        public void onSearchStationsWithFilters(String provincia,String municipio, String companhia, boolean abierto) throws DataAccessException;

        /**
         * Según el nombre de la provincia, en caso de éxito devuelve los municipios de esta y, en caso de error lanza un mensaje.
         *
         * @param provinciaNombre El nombre de la provincia por la que se filtraran los municipios.
         *
         */
        public void onProvinciaSelected(String provinciaNombre);

        /**
         * The presenter is informed that the Order button in the menu has been clicked
         * Only the View should call this method
         */
        public void onOrdenarButtonClicked();

        /**
         * Filtra y muestra la lista de gasolineras segun los filtros indicados.
         *
         * @param combustible el combustible por el que filtrar.Por defecto es "diesel".
         * @param orden El orden por el que filtrar.Por defecto es "ascedente".
         */
        public void ordenarGasolinerasPorPrecio(Combustible combustible, Orden orden);
    }

    /**
     * Methods that must be implemented in the Main View.
     * Only the Presenter should call these methods.
     */
    public interface View {

        /**
         * Initialize the view. Typically this should initialize all the listeners in the view.
         * Only the Presenter should call this method
         */
        public void init();

        /**
         * Returns a repository that can be called by the Presenter to retrieve gas stations.
         * This method must be located in the view because Android resources must be accessed
         * in order to instantiate a repository (for example Internet Access). This requires
         * dependencies to Android. We want to keep the Presenter free of Android dependencies,
         * therefore the Presenter should be unable to instantiate repositories and must rely on
         * the view to create the repository.
         * Only the Presenter should call this method
         * @return
         */
        public IGasolinerasRepository getGasolinerasRepository();

        /**
         * The view is requested to display the given list of gas stations.
         * Only the Presenter should call this method
         * @param stations the list of charging stations
         */
        public void showStations(List<Gasolinera> stations);

        /**
         * The view is requested to display a notification indicating  that the gas
         * stations were loaded correctly.
         * Only the Presenter should call this method
         * @param stations
         */
        public void showLoadCorrect(int stations);

        /**
         * The view is requested to display a notificacion indicating that the gas
         * stations were not loaded correctly.
         * Only the Presenter should call this method
         */
        public void showLoadError();

        /**
         * The view is requested to display the detailed view of the given gas station.
         * Only the Presenter should call this method
         * @param station the charging station
         */
        public void showStationDetails(Gasolinera station);

        /**
         * The view is requested to open the info activity.
         * Only the Presenter should call this method
         */
        public void showInfoActivity();

        /**
         * The view is requested to open the info activity.
         * Only the Presenter should call this method
         */
        public void showFiltersPopUp();

        /**
         * The view is requested to open the info activity.
         * Only the Presenter should call this method
         */
        public void showOrdenarPopUp();

        /**
         * The view is requested to update the spinner content.
         * Only the Presenter should call this method
         */
        public void updateMunicipiosSpinner(List<Municipio> municipios);
    }
}
