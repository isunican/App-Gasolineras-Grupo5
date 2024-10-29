package es.unican.gasolineras.model;
import static es.unican.gasolineras.common.Horario.estaAbierto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.IFiltros;

public class Filtros implements IFiltros {

    /**
     * @see IFiltros#filtrarPorProvinciaYMunicipio(List, String provincia, String municipio)
     */
    @Override
    public List<Gasolinera> filtrarPorProvinciaYMunicipio(List<Gasolinera> gasolineras,
                                                          String provincia, String municipio) {
        List<Gasolinera> resultado = new ArrayList<>();

        if (gasolineras.isEmpty()) { return null; }

        if (provincia != null && municipio != null) {
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getProvincia().equalsIgnoreCase(provincia) &&
                        gasolinera.getMunicipio().equalsIgnoreCase(municipio)) {
                    resultado.add(gasolinera);
                }
            }
            return resultado;
        } else if (provincia != null){
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getProvincia().equalsIgnoreCase(provincia)) {
                    resultado.add(gasolinera);
                }
            }
            return resultado;
        } else if (municipio != null) {
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getMunicipio().equalsIgnoreCase(municipio)) {
                    resultado.add(gasolinera);
                }
            }
            return resultado;
        }
        return gasolineras;
    }

    /**
     * @see IFiltros#filtrarPorEstado(List gasolineras)
     */
    @Override
    public List<Gasolinera> filtrarPorEstado(List<Gasolinera> gasolineras) {
        List<Gasolinera> resultado = new ArrayList<>();

        for (Gasolinera gasolinera : gasolineras) {
            gasolinera.setEstado(estaAbierto(gasolinera.getHorario()));
            if (gasolinera.getEstado().equals("Abierto") ||
                    gasolinera.getEstado().equals("Abierto 24h")) {
                resultado.add(gasolinera);
            }

        }
        return resultado;
    }

    /**
     * @see IFiltros#filtrarPorCompanhia(List gasolineras, String companhia)
     */
    @Override
    public List<Gasolinera> filtrarPorCompanhia(List<Gasolinera> gasolineras, String companhia) {
        List<Gasolinera> resultado = new ArrayList<>();

        if ("Otros".equalsIgnoreCase(companhia)) {
            for (Gasolinera gasolinera : gasolineras) {
                if (!esCompanhiaConocida(gasolinera.getRotulo())) {
                    resultado.add(gasolinera);
                }
            }
        } else {
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getRotulo().toLowerCase().contains(companhia.toLowerCase())) {
                    resultado.add(gasolinera);
                }
            }
        }
        return resultado;
    }

    /**
     * Verifica si la companhia es es una de las conocidas.
     *
     * @param companhia El nombre de la companhia a verificar.
     * @return true si la companhia es conocida, false en caso contrario.
     */
    private boolean esCompanhiaConocida(String companhia) {
        String[] conocidas = {"REPSOL", "CEPSA", "AVIA", "CARREFOUR", "PETRONOR",
                "BALLENOIL", "GALP", "SHELL", "MEROIL", "PETROPRIX","BP"};

        for (String marca : conocidas) {
            if (companhia.toLowerCase().contains(marca.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
