
package es.unican.gasolineras.common;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import static es.unican.gasolineras.common.Horario.estaAbierto;

import es.unican.gasolineras.model.Gasolinera;
public class Filtros {
    /**
     * Filters a list of gas stations by their state (open or closed)
     * @param gasolineras the list of gas stations to filter
     * @return the list of gas stations that match the filter
     * @throws DataAccessException if an error occurs while filtering
     */

    public static List<Gasolinera> filtrarPorEstado(List<Gasolinera> gasolineras) throws DataAccessException {
        List<Gasolinera> resultado = new ArrayList<>();

        for (Gasolinera gasolinera : gasolineras) {
            // enseña por log.d el numero de iteracion acutal
            Log.d("CUENTA", "Iteración: " + gasolineras.indexOf(gasolinera) + " gasolinera con horario:" + gasolinera.getHorario());
            //si estoy en la iteracion 157
            if (gasolinera.getHorario().isEmpty()){
                //Toast Faltan Gasolineras (no mostradas)
            }
            if (gasolineras.indexOf(gasolinera) == 387) {
                gasolinera.setEstado(estaAbierto(gasolinera.getHorario()));
            }
            gasolinera.setEstado(estaAbierto(gasolinera.getHorario()));

            if (gasolinera.getEstado().equals("Abierto") || gasolinera.getEstado().equals("Abierto 24h")) {
                resultado.add(gasolinera);
            }

        }
        return resultado;
    }
}