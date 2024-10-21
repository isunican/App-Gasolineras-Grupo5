package es.unican.gasolineras.common;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;
public class Filtros {

    public static List<Gasolinera> filtrarPorProvincia(List<Gasolinera> gasolineras, String provincia) {
        if (provincia != null) {
            Iterator<Gasolinera> iterator = gasolineras.iterator();
            while (iterator.hasNext()) {
                Gasolinera gasolinera = iterator.next();
                if (!gasolinera.getProvincia().equalsIgnoreCase(provincia)) {
                    iterator.remove();
                }
            }
        }

        return gasolineras;
    }

    public static List<Gasolinera> filtrarPorProvinciaYMunicipio(List<Gasolinera> gasolineras, String provincia, String municipio) {
        if (provincia != null && municipio != null) {
            Iterator<Gasolinera> iterator = gasolineras.iterator();
            while (iterator.hasNext()) {
                Gasolinera gasolinera = iterator.next();
                if (!gasolinera.getProvincia().equalsIgnoreCase(provincia) || !gasolinera.getMunicipio().equalsIgnoreCase(municipio)) {
                    iterator.remove();
                }
            }
        }

        return gasolineras;
    }
}
