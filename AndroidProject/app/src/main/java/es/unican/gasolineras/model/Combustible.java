package es.unican.gasolineras.model;

/**
 * Represents different types of fuel available in the application.
 * Each fuel type has a display name for user-friendly representation.
 */
public enum Combustible {
    GASOLEOA("DIESEL"),
    GASOLINA95E5("Gasolina 95 E5"),
    GASOLINA95E5PREM("Gasolina 95 E5 Premium"),
    GASOLINA95E10("Gasolina 95 E10"),
    GASOLINA98E5("Gasolina 98 E5"),
    GASOLINA98E10("Gasolina 98 E10"),
    BIODIESEL("BIODIESEL");


    /** The display name of the fuel type. */
    private final String displayName;

    /**
     * Constructor for the fuel type with a display name.
     *
     * @param displayName The user-friendly name of the fuel type
     */
    Combustible(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the fuel type.
     *
     * @return the display name of this fuel type
     */
    @Override
    public String toString() {
        return displayName;
    }
}
