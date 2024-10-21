
package es.unican.gasolineras.common;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import es.unican.gasolineras.model.Gasolinera;
public class Filtros {

    public static List<Gasolinera> filtrarPorEstado(List<Gasolinera> gasolineras, boolean estado) {
        List<Gasolinera> resultado = new ArrayList<>(gasolineras.size());
        if (estado) {
            for (Gasolinera gasolinera : gasolineras) {
                if (gasolinera.getEstado().equals("Abierto") || gasolinera.getEstado().equals("Abierto 24h") ) {
                        resultado.add(gasolinera);
                }
            }
            return resultado;
        } else {
            return gasolineras;
        }
    }
}