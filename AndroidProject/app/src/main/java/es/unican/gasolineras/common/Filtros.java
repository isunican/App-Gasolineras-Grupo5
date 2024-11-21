package es.unican.gasolineras.common;

import static es.unican.gasolineras.common.Horario.estaAbierto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;

public class Filtros implements IFiltros {

    /**
     * @see IFiltros#filtrarPorProvinciaYMunicipio(List, String provincia, String municipio)
     */
    @Override
    public List<Gasolinera> filtrarPorProvinciaYMunicipio(List<Gasolinera> gasolineras,
                                                          String provincia, String municipio) {
        List<Gasolinera> resultado = new ArrayList<>();

        if (gasolineras.isEmpty()) {
            return resultado;
        }

        if (provincia != null && municipio != null) {
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getProvincia().equalsIgnoreCase(provincia) &&
                        gasolinera.getMunicipio().equalsIgnoreCase(municipio)) {
                    resultado.add(gasolinera);
                }
            }
            return resultado;
        } else if (provincia != null) {
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

        // Si el filtro de la compañía está vacío, incluir todas las gasolineras
        if (companhia == null || companhia.isEmpty()) {
            resultado.addAll(gasolineras);
            return resultado;
        }

        if ("Otros".equalsIgnoreCase(companhia)) {
            for (Gasolinera gasolinera : gasolineras) {
                String rotulo = gasolinera.getRotulo();
                if (rotulo == null || !esCompanhiaConocida(rotulo)) {
                    resultado.add(gasolinera);
                }
            }
        } else {
            for (Gasolinera gasolinera : gasolineras) {
                String rotulo = gasolinera.getRotulo();
                if (rotulo != null && rotulo.toLowerCase().contains(companhia.toLowerCase())) {
                    resultado.add(gasolinera);
                }
            }
        }
        return resultado;
    }

    /**
     * @see IFiltros#filtrarPorCombustibles(List gasolineras, List combustibles)
     */
    @Override
    public List<Gasolinera> filtrarPorCombustibles(List<Gasolinera> gasolineras, List<String> combustibles) {

        List<Gasolinera> resultado = new ArrayList<>();

        // Si el filtro del combustible está vacío, incluir todas las gasolineras
        if (combustibles == null || combustibles.isEmpty()) {
            resultado.addAll(gasolineras);
            return resultado;
        }

        //Recorro la lista de gasolineras
        for (Gasolinera g : gasolineras) {
            boolean anhadida = false;
            Iterator<String> iter = combustibles.iterator();

            //Por cada combustible, compruebo si la gasolinera tiene, al menos, uno de los combustibles
            while (!anhadida && iter.hasNext()) {
                String actual = iter.next();
                switch (actual) {
                    case "Gasolina 95 E5":
                        if (g.getGasolina95E5() > 0) {
                            resultado.add(g);
                            anhadida = true;
                        }
                        break;

                    case "Gasolina 95 E5 Premium":
                        if (g.getGasolina95E5PREM() > 0) {
                            resultado.add(g);
                            anhadida = true;
                        }
                        break;

                    case "Gasolina 95 E10":
                        if (g.getGasolina95E10() > 0) {
                            resultado.add(g);
                            anhadida = true;
                        }
                        break;

                    case "Gasolina 98 E5":
                        if (g.getGasolina98E5() > 0) {
                            resultado.add(g);
                            anhadida = true;
                        }
                        break;

                    case "Gasolina 98 E10":
                        if (g.getGasolina98E10() > 0) {
                            resultado.add(g);
                            anhadida = true;
                        }
                        break;
                    default:
                        break;
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
    private boolean esCompanhiaConocida(String companhia){
        String[] conocidas = {"REPSOL", "CEPSA", "AVIA", "CARREFOUR", "PETRONOR",
                "BALLENOIL", "GALP", "SHELL", "MEROIL", "PETROPRIX", "BP"};

        for (String marca : conocidas) {
            if (companhia.toLowerCase().contains(marca.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

