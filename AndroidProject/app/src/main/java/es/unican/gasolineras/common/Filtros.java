package es.unican.gasolineras.common;
import static es.unican.gasolineras.common.Horario.estaAbierto;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.model.Gasolinera;
public class Filtros {

    public static List<Gasolinera> filtrarPorProvinciaYMunicipio(List<Gasolinera> gasolineras, String provincia, String municipio) {
        List<Gasolinera> resultado = new ArrayList<>(gasolineras.size());

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

    public static List<Gasolinera> filtrarPorEstado(List<Gasolinera> gasolineras) throws DataAccessException {
        List<Gasolinera> resultado = new ArrayList<>();

        for (Gasolinera gasolinera : gasolineras) {

            gasolinera.setEstado(estaAbierto(gasolinera.getHorario()));

            if (gasolinera.getEstado().equals("Abierto") || gasolinera.getEstado().equals("Abierto 24h")) {
                resultado.add(gasolinera);
            }

        }
        return resultado;
    }
}
