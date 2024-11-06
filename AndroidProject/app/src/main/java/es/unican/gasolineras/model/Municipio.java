package es.unican.gasolineras.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
@Getter
@Setter
public class Municipio {

    @SerializedName("IDMunicipio")      protected String idMunicipio;
    @SerializedName("IDProvincia")      protected String idProvincia;
    @SerializedName("IDCCAA")           protected String idCCAA;
    @SerializedName("Municipio")        protected String nombre; // Nombre del municipio
    @SerializedName("Provincia")        protected String provincia;
    @SerializedName("CCAA")             protected String ccaa;

}
