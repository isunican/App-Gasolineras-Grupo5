package es.unican.gasolineras.activities.main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Combustible;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDProvincias;
import es.unican.gasolineras.model.Municipio;
import es.unican.gasolineras.model.Orden;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import lombok.Setter;
import lombok.Getter;

/**
 * The presenter of the main activity of the application. It controls {@link MainView}
 */
@Setter
@Getter
public class MainPresenter implements IMainContract.Presenter {

    /** The view that is controlled by this presenter */
    private IMainContract.View view;
    private List<Gasolinera> gasolineras;
    @Setter
    private IFiltros filtros;
    private List<Gasolinera> gasolinerasFiltradas;
    private List<Gasolinera> gasolinerasCoordenadas;
    private boolean IsFiltro;

    /**
     * @see IMainContract.Presenter#init(IMainContract.View)
     * @param view the view to control
     */
    @Override
    public void init(IMainContract.View view) {
        this.view = view;
        this.view.init();
        load();
    }

    /**
     * @see IMainContract.Presenter#onStationClicked(Gasolinera)
     * @param station the station that has been clicked
     */
    @Override
    public void onStationClicked(Gasolinera station) {
        view.showStationDetails(station);
    }

    /**
     * @see IMainContract.Presenter#onMenuInfoClicked()
     */
    @Override
    public void onMenuInfoClicked() {
        view.showInfoActivity();
    }

    /**
     * @see IMainContract.Presenter#onFilterButtonClicked()
     */
    @Override
    public void onFilterButtonClicked() {
        view.showFiltersPopUp();
    }

    /**
     * @see IMainContract.Presenter#onSearchStationsWithFilters(String provincia, String municipio,
     *                                                          String companhia, boolean abierto)
     */
    @Override
    public void onSearchStationsWithFilters(String provincia, String municipio, String companhia,
                                            boolean abierto) throws DataAccessException {

        List<Gasolinera> gasolinerasFiltradas = gasolineras;

        String finalProvincia = "-".equals(provincia) ? null : provincia;
        String finalMunicipio = ("-".equals(municipio) || municipio.isEmpty()) ? null : municipio;
        String finalCompanhia = "-".equals(companhia) ? null : companhia;

        if (finalProvincia != null) {
            gasolinerasFiltradas = filtros.filtrarPorProvinciaYMunicipio(gasolinerasFiltradas,
                    finalProvincia, finalMunicipio);
        }
        if (finalCompanhia != null){
            gasolinerasFiltradas = filtros.filtrarPorCompanhia(gasolinerasFiltradas, finalCompanhia);
        }
        if (abierto) {
            gasolinerasFiltradas = filtros.filtrarPorEstado(gasolinerasFiltradas);
        }

        // Guarda la lista filtrada para utilizarla en la ordenación
        this.gasolinerasFiltradas = gasolinerasFiltradas;

        view.showStations(gasolinerasFiltradas);
        view.showLoadCorrect(gasolinerasFiltradas.size());
    }

    @Override
    public void onProvinciaSelected(String provinciaNombre) {
        String idProvincia = IDProvincias.getCodigoByProvincia(provinciaNombre);
        if (idProvincia != null) {
            view.getGasolinerasRepository().requestMunicipiosPorProvincia(new ICallBack<Municipio>() {
                @Override
                public void onSuccess(List<Municipio> municipios) {
                    view.updateMunicipiosSpinner(municipios);
                }

                @Override
                public void onFailure(Throwable e) {
                    view.showLoadError();
                }
                }, idProvincia);
        }
    }

    /**
     * @see IMainContract.Presenter#onOrdenarButtonClicked()
     */
    @Override
    public void onOrdenarButtonClicked() { view.showOrdenarPopUp(); }


    /**
     * @see IMainContract.Presenter#ordenarGasolinerasPorPrecio(Combustible combustible, Orden orden)
     */
    @Override
    public void ordenarGasolinerasPorPrecio(Combustible combustible, Orden orden) {
        // Usa la lista filtrada
        List<Gasolinera> gasolinerasAOrdenar;
        if(IsFiltro){
             gasolinerasAOrdenar = this.gasolinerasFiltradas;
        }else{

            gasolinerasAOrdenar = this.gasolinerasCoordenadas;
        }


        // Determinamos el comparador básico según el tipo de combustible
        Comparator<Gasolinera> comparator = (g1, g2) -> {
            double precio1 = getPrecioCombustible(g1, combustible);
            double precio2 = getPrecioCombustible(g2, combustible);

            // Coloca precios 0.0 al final en cualquier orden
            if (precio1 == 0.0 && precio2 != 0.0) return 1;
            if (precio2 == 0.0 && precio1 != 0.0) return -1;

            // Ordena normalmente por precio
            return Double.compare(precio1, precio2);
        };

        // Si el orden es descendente, cambiamos la comparación sin afectar a los 0.0
        if (orden == Orden.DESCENDENTE) {
            comparator = (g1, g2) -> {
                double precio1 = getPrecioCombustible(g1, combustible);
                double precio2 = getPrecioCombustible(g2, combustible);

                // Coloca precios 0.0 al final en cualquier orden
                if (precio1 == 0.0 && precio2 != 0.0) return 1;
                if (precio2 == 0.0 && precio1 != 0.0) return -1;

                // Ordena de mayor a menor
                return Double.compare(precio2, precio1);
            };
        }

        // Ordenamos la lista
        gasolinerasAOrdenar.sort(comparator);
        // Mostramos la lista ordenada
        view.showStations(gasolinerasAOrdenar);

    }

    @Override
    public void onCoordinatesButtonClicked() {view.showCoordinatesPopUp();}

    @Override
    public void searchWithCoordinates(Double longitud, Double latitud, int distancia){

        // Crear una nueva lista para almacenar las gasolineras filtradas
        List<Gasolinera> gasolinerasFiltradasCoordenadas = new ArrayList<>();

        for (Gasolinera g : this.gasolinerasFiltradas) {
            // Obtener y convertir las coordenadas de la gasolinera
            String longitudStr = g.getLongitud().replace(",", "."); // Asegurar formato decimal
            String latitudStr = g.getLatitud().replace(",", ".");
            Double longitudGasolinera = Double.parseDouble(longitudStr);
            Double latitudGasolinera = Double.parseDouble(latitudStr);

            // Verificar si la gasolinera está dentro de la distancia
            if (estaEnCoordenadas(longitud, latitud, distancia, longitudGasolinera, latitudGasolinera)) {
                gasolinerasFiltradasCoordenadas.add(g); // Añadir si cumple el criterio
            }
        }

        this.gasolinerasCoordenadas = gasolinerasFiltradasCoordenadas;

                // Actualizar la vista con las gasolineras filtradas
        view.showStations(gasolinerasFiltradasCoordenadas);
        view.showLoadCorrect(gasolinerasFiltradasCoordenadas.size());


    }

    private boolean estaEnCoordenadas(Double longitudSelec, Double latitudSelec, int distancia, Double longitudGasolinera, Double latitudGasolinera){
        final int RADIO_TIERRA = 6371000; // Radio de la Tierra en metros

        // Verifica que las coordenadas no sean nulas o extremas
        if (longitudSelec == null || latitudSelec == null || longitudGasolinera == null || latitudGasolinera == null) {
            return false; // Coordenadas inválidas
        }

        // Convertir las coordenadas de grados a radianes
        double latitudSelecRad = Math.toRadians(latitudSelec);
        double longitudSelecRad = Math.toRadians(longitudSelec);
        double latitudGasolineraRad = Math.toRadians(latitudGasolinera);
        double longitudGasolineraRad = Math.toRadians(longitudGasolinera);

        // Diferencias entre las coordenadas
        double diferenciaLatitud = latitudGasolineraRad - latitudSelecRad;
        double diferenciaLongitud = longitudGasolineraRad - longitudSelecRad;

        // Fórmula del haversine
        double a = Math.sin(diferenciaLatitud / 2) * Math.sin(diferenciaLatitud / 2)
                + Math.cos(latitudSelecRad) * Math.cos(latitudGasolineraRad)
                * Math.sin(diferenciaLongitud / 2) * Math.sin(diferenciaLongitud / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en metros
        double distanciaEntrePuntos = RADIO_TIERRA * c;

        // Comparar la distancia calculada con la distancia permitida (convertida a metros)
        return distanciaEntrePuntos <= distancia * 1000;

    }

    /**
     * Método auxiliar para obtener el precio del combustible
     * @param gasolinera la gasolinera de la que se queire obtener los precios
     * @param combustible el tipo de combustible del que se quiere obtener el precio
     * @return el precio del tipo de combustible seleccionado en la gasolinera seleccionada
     */
    private double getPrecioCombustible(Gasolinera gasolinera, Combustible combustible) {
        switch (combustible) {
            case GASOLEOA:
                return gasolinera.getGasoleoA();
            case GASOLINA95E:
                return gasolinera.getGasolina95E5();
            case GASOLINA98E:
                return gasolinera.getGasolina98E5();
            default:
                return gasolinera.getBiodiesel();
        }
    }

    /**
     Loads the gas stations from the repository, and sends them to the view
     */
    private void load() {
        IGasolinerasRepository repository = view.getGasolinerasRepository();

        ICallBack<Gasolinera> callBack = new ICallBack<Gasolinera>() {

            @Override
            public void onSuccess(List<Gasolinera> stations) {
                gasolineras = stations;
                gasolinerasFiltradas = stations;
                view.showStations(stations);
                view.showLoadCorrect(stations.size());
            }

            @Override
            public void onFailure(Throwable e) {
                view.showLoadError();
            }
        };
        repository.requestGasolineras(callBack);
    }
}