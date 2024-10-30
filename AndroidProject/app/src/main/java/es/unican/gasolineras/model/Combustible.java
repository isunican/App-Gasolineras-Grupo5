package es.unican.gasolineras.model;

public enum Combustible {
    GASOLEOA("DIESEL"),
    GASOLINA95E("GASOLINA");

    private final String displayName;

    Combustible(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
