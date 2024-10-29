package es.unican.gasolineras.activities.main;

import java.util.List;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Filtros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import lombok.Setter;

/**
 * The presenter of the main activity of the application. It controls {@link MainView}
 */
public class MainPresenter implements IMainContract.Presenter {

    /** The view that is controlled by this presenter */
    private IMainContract.View view;
    private List<Gasolinera> gasolineras;
    @Setter
    private IFiltros filtros;

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
        String finalMunicipio = "".equals(municipio) ? null : municipio;
        String finalCompanhia = "-".equals(companhia) ? null : companhia;

        if (finalProvincia != null || finalMunicipio != null) {
            gasolinerasFiltradas = filtros.filtrarPorProvinciaYMunicipio(gasolinerasFiltradas,
                    finalProvincia, finalMunicipio);
        }
        if (finalCompanhia != null){
            gasolinerasFiltradas = filtros.filtrarPorCompanhia(gasolinerasFiltradas, finalCompanhia);
        }
        if (abierto) {
            gasolinerasFiltradas = filtros.filtrarPorEstado(gasolinerasFiltradas);
        }

        view.showStations(gasolinerasFiltradas);
        view.showLoadCorrect(gasolinerasFiltradas.size());
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
            }
        };
        repository.requestGasolineras(callBack);
    }

}
