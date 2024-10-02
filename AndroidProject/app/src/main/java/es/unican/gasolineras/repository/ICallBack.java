package es.unican.gasolineras.repository;

import java.util.List;

import es.unican.gasolineras.model.Gasolinera;

/**
 * The callback used by the repository to asynchronously retrieve gas stations.
 */
public interface ICallBack {

    /**
     * This method is automatically called when the gas stations were successfully retrieved
     * @param stations the list of retrieved gas stations
     */
    public void onSuccess(List<Gasolinera> stations);

    /**
     * This method is automaticaly called when there was some failure when retrieving the
     * gas stations.
     * @param e the information about the failure
     */
    public void onFailure(Throwable e);

}
