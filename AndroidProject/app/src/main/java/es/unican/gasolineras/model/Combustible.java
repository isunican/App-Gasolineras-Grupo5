package es.unican.gasolineras.model;

/**
 * Represents different types of fuel available in the application.
 * Each fuel type has a display name for user-friendly representation.
 */
public enum Combustible {
    GASOLEOA("DIESEL"),
    GASOLINA95E("GASOLINA 95"),
    GASOLINA98E("GASOLINA 98"),
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
