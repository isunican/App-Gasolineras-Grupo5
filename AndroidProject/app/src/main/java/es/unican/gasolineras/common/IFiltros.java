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


    /**
     * Filtra una lista de gasolineras segun los combustibles seleccionados.
     * @param gasolineras la lista completa de gasolineras a filtrar.
     * @param combustibles una lista de strings que representan los tipos de combustibles seleccionados
     *                     (por ejemplo, "Gasolina 95 E5", "Gasolina 98 E10"). Si la lista es nula o
     *                     vacia, se devolverán todas las gasolineras.
     * @return una lista de gasolineras que ofrecen al menos uno de los combustibles seleccionados.
     *         Si no se especifican combustibles, se devuelve la lista original completa.
     *
     * @implNote Este metodo utiliza un bucle para recorrer las gasolineras y evalua cada combustible
     *           especificado. Si una gasolinera ofrece al menos uno de los combustibles seleccionados,
     *           se incluye en la lista de resultados. La evaluacion se detiene para cada gasolinera una vez
     *           que se encuentra un combustible coincidente.
     */
    public List<Gasolinera> filtrarPorCombustibles(List<Gasolinera> gasolineras, List<String> combustibles);
}
