package es.unican.gasolineras.model;

/**
 * Static collection of Comunidades Autonomas ID's, as used by the REST API.
 * The values of these ID's can be fetched from a dedicated
 * <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/Listados/ComunidadesAutonomas/">endpoint</a>
 */
public enum IDProvincias {

    ALBACETE("02", "Albacete"),
    ALICANTE("03", "Alicante"),
    ALMERIA("04", "Almería"),
    ALAVA("01", "Araba/Álava"),
    ASTURIAS("33", "Asturias"),
    AVILA("05", "Ávila"),
    BADAJOZ("06", "Badajoz"),
    BALEARES("07", "Balears (Illes)"),
    BARCELONA("08", "Barcelona"),
    BIZKAIA("48", "Bizkaia"),
    BURGOS("09", "Burgos"),
    CACERES("10", "Cáceres"),
    CADIZ("11", "Cádiz"),
    CANTABRIA("39", "Cantabria"),
    CASTELLON("12", "Castellón / Castelló"),
    CEUTA("51", "Ceuta"),
    CIUDAD_REAL("13", "Ciudad Real"),
    CORDOBA("14", "Córdoba"),
    CORUNA("15", "Coruña (A)"),
    CUENCA("16", "Cuenca"),
    GIPUZKOA("20", "Gipuzkoa"),
    GIRONA("17", "Girona"),
    GRANADA("18", "Granada"),
    GUADALAJARA("19", "Guadalajara"),
    HUELVA("21", "Huelva"),
    HUESCA("22", "Huesca"),
    JAEN("23", "Jaén"),
    LEON("24", "León"),
    LLEIDA("25", "Lleida"),
    LUGO("27", "Lugo"),
    MADRID("28", "Madrid"),
    MALAGA("29", "Málaga"),
    MELILLA("52", "Melilla"),
    MURCIA("30", "Murcia"),
    NAVARRA("31", "Navarra"),
    OURENSE("32", "Ourense"),
    PALENCIA("34", "Palencia"),
    LAS_PALMAS("35", "Palmas (Las)"),
    PONTEVEDRA("36", "Pontevedra"),
    LA_RIOJA("26", "Rioja (La)"),
    SALAMANCA("37", "Salamanca"),
    SANTA_CRUZ_DE_TENERIFE("38", "Santa Cruz de Tenerife"),
    SEGOVIA("40", "Segovia"),
    SEVILLA("41", "Sevilla"),
    SORIA("42", "Soria"),
    TARRAGONA("43", "Tarragona"),
    TERUEL("44", "Teruel"),
    TOLEDO("45", "Toledo"),
    VALENCIA("46", "Valencia / València"),
    VALLADOLID("47", "Valladolid"),
    ZAMORA("49", "Zamora"),
    ZARAGOZA("50", "Zaragoza");

    public final String id;
    public final String nombre;

    private IDProvincias(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    /**
     * Devuelve el id de la provincia pasada como parametro.
     *
     * @param  nombreProvincia Nombre de la provincia a obtener el código.
     */
    public static String getCodigoByProvincia(String nombreProvincia) {
        if (nombreProvincia == null || nombreProvincia.equals("-")) { return null; }
        for (IDProvincias provincia : IDProvincias.values()) {
            if (provincia.nombre.equalsIgnoreCase(nombreProvincia)) {
                return provincia.id;
            }
        }
        return null;
    }
}
