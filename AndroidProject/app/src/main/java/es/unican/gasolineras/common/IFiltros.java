package es.unican.gasolineras.common;

import java.util.List;

import es.unican.gasolineras.model.Gasolinera;

public interface IFiltros {

    /**
     * Filtra una lista de gasolineras por provincia y municipio.
     *
     * @param gasolineras La lista de gasolineras a filtar.
     * @param provincia La provincia por la que filtrar. Puede ser null.
     * @param municipio El municipio por el que filtrar. Puede ser null.
     * @return Una lista de gasolineras que coinciden con los filtros de provincia y municipio.
     * Devuelve la lista original si ambos filtros son null. Devuelve null si la lista esta vacia.
     */
    public List<Gasolinera> filtrarPorProvinciaYMunicipio(List<Gasolinera> gasolineras,
                                                          String provincia, String municipio);

    /**
     * Filtra una lista de gasolineras por su estado (abierta o cerrada).
     *
     * @param gasolineras La lista de gasolineras a filtar.
     * @return Una lista con las gasolineras que estan abiertas.
     */
    public List<Gasolinera> filtrarPorEstado(List<Gasolinera> gasolineras);

    /**
     * Filtra una lista de gasolineras por companhia.
     *
     * @param gasolineras La lista de gasolineras a filtar.
     * @param companhia El nomrbe de la companhia por la que filtrar.
     * @return Una lista de gasolineras que perteneces a la companhia especificada.
     * Si la companhia es "Otros", devuelve las gasolineras que no son de ninguna marca conocida.
     */
    public List<Gasolinera> filtrarPorCompanhia(List<Gasolinera> gasolineras, String companhia);
}
