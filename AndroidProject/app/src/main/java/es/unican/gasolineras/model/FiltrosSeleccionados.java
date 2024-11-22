package es.unican.gasolineras.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltrosSeleccionados {
    private String provincia;
    private String municipio;
    private String companhia;
    private List<String> combustibles;
    private boolean estadoAbierto;

    public FiltrosSeleccionados() {
        this.provincia = "";
        this.municipio = "";
        this.companhia = "";
        this.combustibles = new ArrayList<>();
        this.estadoAbierto = false;
    }
}
