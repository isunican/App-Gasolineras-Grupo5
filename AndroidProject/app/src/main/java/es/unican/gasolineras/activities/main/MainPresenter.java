package es.unican.gasolineras.activities.main;

import androidx.multidex.BuildConfig;

import java.util.List;

import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.Filtros;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDCCAAs;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The presenter of the main activity of the application. It controls {@link MainView}
 */
public class MainPresenter implements IMainContract.Presenter {

    /** The view that is controlled by this presenter */
    private IMainContract.View view;
    private List<Gasolinera> gasolineras;
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

    @Override
    public void onFilterButtonClicked() {
        view.showFiltersPopUp();
    }



    @Override
    public void onSearchStationsWithFilters(boolean estado) throws DataAccessException {

                List<Gasolinera>gasolinerasFiltradas = gasolineras;
                if (estado) {
                    gasolinerasFiltradas = Filtros.filtrarPorEstado(gasolinerasFiltradas);
                    view.showStations(gasolinerasFiltradas);
                    view.showLoadCorrect(gasolinerasFiltradas.size());
                }else{
                    view.showStations(gasolineras);
                    view.showLoadCorrect(gasolineras.size());
                }

    }


    /**
     * Loads the gas stations from the repository, and sends them to the view
     */
    private void load() {
        if (BuildConfig.DEBUG) {
            // Modo pruebas: usa la lista generada
            gasolineras = Generador.generarGasolineras();
        } else {
            // Modo producción: usa el repositorio real
            IGasolinerasRepository repository = view.getGasolinerasRepository();

            ICallBack callBack = new ICallBack() {
                @Override
                public void onSuccess(List<Gasolinera> stations) {
                    gasolineras = stations;
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

        // Muestra las gasolineras (tanto para modo pruebas como producción)
        view.showStations(gasolineras);
        view.showLoadCorrect(gasolineras.size());
    }
}
