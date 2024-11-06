package es.unican.gasolineras.model;

public enum Combustible {
    GASOLEOA("DIESEL"),
    GASOLINA95E("GASOLINA 95"),
    GASOLINA98E("GASOLINA 98"),
    BIODIESEL("BIODIESEL");

    private final String displayName;

    Combustible(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
