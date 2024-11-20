package es.unican.gasolineras.activities.main;

import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Municipio;
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
         *                  debe filtar por municipio.
         * @param companhia La compnhia por la que filtrar. Puede ser "-" para indicar que no se
         *                  debe filtar por companhia.
         * @param combustibles El conjunto de combustibles por los que filtar. Puede ser "-" para
         *                    indicar que no se debe filtrar por combustible.
         * @param abierto Un boolean que indica si se debe filtar por gasolineras abiertas.
         */
        public void onSearchStationsWithFilters(String provincia,String municipio, String companhia,
                                                List<String> combustibles, boolean abierto);

        /**
         * Segun el nombre de la provincia, en caso de exito devuelve los municipios de esta y, en
         * caso de error lanza un mensaje.
         *
         * @param provinciaNombre El nombre de la provincia por la que se filtraran los municipios.
         */
        public void onProvinciaSelected(String provinciaNombre);

        /**
         * Handles the event when the sort button is clicked, displaying the sort options popup.
         */
        public void onOrdenarButtonClicked();

        /**
         * Sorts gas stations by the specified fuel type and order.
         * @param combustible the fuel type to sort by
         * @param orden the order (ascending or descending)
         */
        public void ordenarGasolinerasPorPrecio(String combustible, String orden);

        /**
         * Handles the event when the coordinates button is clicked, displaying the coordinates options popup.
         */
        public void onCoordinatesButtonClicked();

        /**
         * Filtra una lista de gasolineras segun su proximidad a un punto de referencia
         * especificado por coordenadas y una distancia maxima. Las gasolineras que cumplan
         * con el criterio se almacenan en una nueva lista y se actualiza la vista con
         * los resultados.
         *
         * @param longitud Longitud del punto de referencia en grados.
         * @param latitud  Latitud del punto de referencia en grados.
         * @param distancia Distancia máxima permitida en kilómetros para incluir una gasolinera.
         */
        public void searchWithCoordinates(Double longitud, Double latitud, int distancia);
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
         * Muestra un cuadro de dialogo emergente para configurar los filtros de busqueda de gasolineras.
         * Permite al usuario seleccionar provincia, municipio, companhiía, estado de apertura, y
         * tipos de combustibles, aplicando y guardando los filtros seleccionados al confirmar.
         */
        public void showFiltersPopUp();

        /**
         * Muestra un cuadro de dialogo emergente para ordenar las gasolineras.
         * Permite al usuario seleccionar el tipo de combustible y el criterio
         * de orden, aplicando la ordenacion al confirmar.
         */
        public void showOrdenarPopUp();

        /**
         * Muestra un cuadro de dialogo emergente que permite al usuario ingresar las coordenadas
         * de longitud y latitud, asi como seleccionar una distancia mediante un slider.
         * El dialog tambien muestra las coordenadas guardadas previamente si estan disponibles.
         */
         public void showCoordinatesPopUp();

        /**
         * Actualiza el spinner de municipios con una lista proporcionada.
         * Anhade una opcion por defecto ("-") y establece el municipio previamente guardado si existe.
         *
         * @param municipios La lista de municipios para poblar el spinner.
         */
        public void updateMunicipiosSpinner(List<Municipio> municipios);
    }
}
