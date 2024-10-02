package es.unican.gasolineras.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service singletons for the <a href="https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/help">Gasolineras service API</a>
 */
public class GasolinerasService {

    /** Open Charge Map API base URL*/
    final static String BASE_URL = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/";

    /** Custom deserializer to support spanish format of float numbers (with comma separator) */
    public static final JsonDeserializer<Double> deserializer;

    /** Retrofit object for the Gasolineras API that uses the custom deserializer */
    public static final Retrofit retrofit;

    /** Object to access the Gasolineras API */
    public static final IGasolinerasAPI api;

    static {
        // custom deserializer to support spanish format of float numbers (with comma separator)
        deserializer = new JsonDeserializer<Double>() {
            @Override
            public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                final Locale locale = Locale.forLanguageTag("es-ES");
                NumberFormat format = NumberFormat.getInstance(locale);
                Number number = null;
                try {
                    number = format.parse(json.getAsString());
                } catch (ParseException e) {}
                return number != null ? number.doubleValue() : null;
            }
        };
        final Gson gson = new GsonBuilder().registerTypeAdapter(double.class, deserializer).create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(IGasolinerasAPI.class);
    }

}
