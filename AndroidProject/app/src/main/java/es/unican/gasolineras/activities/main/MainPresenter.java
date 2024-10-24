package es.unican.gasolineras.activities.main;

import android.util.Log;

import java.util.List;

import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.Filtros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.IDProvincias;
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

    /**
     * @see IMainContract.Presenter#onFilterButtonClicked()
     */
    @Override
    public void onFilterButtonClicked() {
        view.showFiltersPopUp();
    }

    /**
     * @see IMainContract.Presenter#onSearchStationsWhithFilters(String provincia, String municipio, boolean abierto)
     */
    @Override
    public void onSearchStationsWhithFilters(String provincia, String municipio, boolean abierto) throws DataAccessException {
        List<Gasolinera> gasolinerasFiltradas = gasolineras;

        String finalProvincia = "-".equals(provincia) ? null : provincia;
        String finalMunicipio = municipio.isEmpty() ? null : municipio;

        Log.d("Provincia", provincia);
        Log.d("Municipio", municipio);

        if (finalProvincia != null || finalMunicipio != null) {
            gasolinerasFiltradas = Filtros.filtrarPorProvinciaYMunicipio(gasolinerasFiltradas, finalProvincia, finalMunicipio);

            if (abierto) {
                gasolinerasFiltradas = Filtros.filtrarPorEstado(gasolinerasFiltradas);
            }

            view.showStations(gasolinerasFiltradas);
            view.showLoadCorrect(gasolinerasFiltradas.size());
        } else {
            gasolinerasFiltradas = gasolineras;
            if (abierto) {
                gasolinerasFiltradas = Filtros.filtrarPorEstado(gasolineras);
            }
            view.showStations(gasolinerasFiltradas);
            view.showLoadCorrect(gasolinerasFiltradas.size());
        }
    }

    /**
     Loads the gas stations from the repository, and sends them to the view
     */
    private void load() {
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
                view.showLoadError();
            }
        };
        repository.requestGasolineras(callBack);
    }
}
