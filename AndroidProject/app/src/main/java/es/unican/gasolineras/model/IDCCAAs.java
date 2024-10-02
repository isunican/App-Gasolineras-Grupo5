package es.unican.gasolineras.model;

/**
 * Static collection of Comunidades Autonomas ID's, as used by the REST API.
 * The values of these ID's can be fetched from a dedicated
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/Listados/ComunidadesAutonomas/">endpoint</a>
 */
public enum IDCCAAs {

    CANTABRIA("06");

    public final String id;

    private IDCCAAs(String id) {
        this.id = id;
    }

}
