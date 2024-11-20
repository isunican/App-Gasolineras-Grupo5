package es.unican.gasolineras.activities.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;

/**
 * Adapter that renders the gas stations in each row of a ListView
 */
public class GasolinerasArrayAdapter extends BaseAdapter {

    /** The list of gas stations to render */
    private final List<Gasolinera> gasolineras;

    /** Context of the application */
    private final Context context;

    /** Selected fuel type for filtering or highlighting prices */
    private final   List<String> combustibleSeleccionado;

    /**
     * Constructs an adapter to handle a list of gasolineras
     * @param context the application context
     * @param objects the list of gas stations
     */
    public GasolinerasArrayAdapter(@NonNull Context context, @NonNull List<Gasolinera> objects, List<String> combustibleSeleccionado) {
        // we know the parameters are not null because of the @NonNull annotation
        this.gasolineras = objects;
        this.context = context;
        this.combustibleSeleccionado = combustibleSeleccionado;
    }

    @Override
    public int getCount() {
        return gasolineras.size();
    }

    @Override
    public Object getItem(int position) {
        return gasolineras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Provides a view for each gas station in the list.
     *
     * @param position the position of the gas station
     * @param convertView the recycled view to populate
     * @param parent the parent view group
     * @return the view for the gas station at the specified position
     */
    @SuppressLint("DiscouragedApi")  // to remove warnings about using getIdentifier
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Gasolinera gasolinera = (Gasolinera) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_main_list_item, parent, false);
        }

        // logo
        setLogo(convertView, gasolinera);

        // status
        setStatus(convertView, gasolinera);

        // name
        {
            TextView tv = convertView.findViewById(R.id.tvName);
            tv.setText(gasolinera.getRotulo());
        }

        // address
        {
            TextView tv = convertView.findViewById(R.id.tvAddress);
            tv.setText(gasolinera.getDireccion());
        }
        if (combustibleSeleccionado == null || combustibleSeleccionado.isEmpty()) {
            // Mostrar precios por defecto si no hay selección
            setFuelPrice(convertView, R.id.tvGas1, R.id.tvPrecio1, context.getString(R.string.gasolina95E5label),
                    String.valueOf(gasolinera.getGasolina95E5()), true);
            setFuelPrice(convertView, R.id.tvGas2, R.id.tvPrecio2, context.getString(R.string.dieselAlabel),
                    String.valueOf(gasolinera.getGasoleoA()), true);
        } else {
            // Inicializa las vistas de Gas y Precio
            TextView tvGas1 = convertView.findViewById(R.id.tvGas1);
            TextView tvPrecio1 = convertView.findViewById(R.id.tvPrecio1);
            TextView tvGas2 = convertView.findViewById(R.id.tvGas2);
            TextView tvPrecio2 = convertView.findViewById(R.id.tvPrecio2);

            // Mostrar solo uno o dos precios según la selección
            int maxCombustiblesAMostrar = Math.min(combustibleSeleccionado.size(), 2);

            for (int i = 0; i < maxCombustiblesAMostrar; i++) {
                String combustible = combustibleSeleccionado.get(i);
                String label = "";
                String precio = "";

                // Obtén la etiqueta y el precio según el tipo de combustible
                switch (combustible) {
                    case "Gasolina 95 E5":
                        label = context.getString(R.string.gasolina95E5label);
                        precio = String.valueOf(gasolinera.getGasolina95E5());
                        break;

                    case "Gasolina 95 E5 Premium":
                        label = context.getString(R.string.gasolina95E5Premlabel);
                        precio = String.valueOf(gasolinera.getGasolina95E5PREM());
                        break;

                    case "Gasolina 95 E10":
                        label = context.getString(R.string.gasolina95E10label);
                        precio = String.valueOf(gasolinera.getGasolina95E10());
                        break;

                    case "Gasolina 98 E5":
                        label = context.getString(R.string.gasolina98E5label);
                        precio = String.valueOf(gasolinera.getGasolina98E5());
                        break;

                    case "Gasolina 98 E10":
                        label = context.getString(R.string.gasolina98E10label);
                        precio = String.valueOf(gasolinera.getGasolina98E10());
                        break;

                    default:
                        label = "Desconocido";
                        precio = "--";
                        break;
                }

                // Asigna los valores a las vistas correspondientes
                if (i == 0) {
                    setFuelPrice(convertView, R.id.tvGas1, R.id.tvPrecio1, label, precio, true);
                } else {
                    setFuelPrice(convertView, R.id.tvGas2, R.id.tvPrecio2, label, precio, true);
                }
            }

            // Oculta las vistas no usadas si hay menos de dos combustibles seleccionados
            if (maxCombustiblesAMostrar < 2) {
                tvGas2.setVisibility(View.GONE);
                tvPrecio2.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    /**
     * Sets the appropriate logo for the given Gasolinera.
     *
     * @param convertView The view containing the ImageView.
     * @param gasolinera The Gasolinera object for the current list item.
     */
    private void setLogo(View convertView, Gasolinera gasolinera) {
        String rotulo = gasolinera.getRotulo().toLowerCase();
        int imageID = context.getResources()
                .getIdentifier(rotulo, "drawable", context.getPackageName());

        // Handle the case where the resource is not found or rotulo is numeric
        if (imageID == 0 || TextUtils.isDigitsOnly(rotulo)) {
            imageID = context.getResources()
                    .getIdentifier("generic", "drawable", context.getPackageName());
        }

        // Set the image resource if a valid ID is found
        if (imageID != 0) {
            ImageView view = convertView.findViewById(R.id.ivLogo);
            view.setImageResource(imageID);
        }
    }


    /**
     * Sets the status text for the Gasolinera and handles exceptions.
     *
     * @param convertView The view containing the TextView.
     * @param gasolinera The Gasolinera object for the current list item.
     */
    private void setStatus(View convertView, Gasolinera gasolinera) {
        TextView tv = convertView.findViewById(R.id.tvEstado);
        try {
            tv.setText(gasolinera.compruebaEstado(gasolinera.getHorario()));
        } catch (IllegalArgumentException e) {
            String errorMessage = String.format("Error de argumento: %s", e.getMessage());
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            tv.setText(errorMessage);
        }
    }

    /**
     * Configura el precio del combustible en un elemento de vista.
     *
     * @param convertView la vista que contiene los elementos a configurar.
     * @param labelId el ID del TextView que muestra la etiqueta del combustible.
     * @param priceId el ID del TextView que muestra el precio del combustible.
     * @param labelText el texto que se usará como etiqueta para el combustible.
     * @param price el precio del combustible a mostrar.
     * @param visible indica si los elementos de etiqueta y precio deben ser visibles.
     */
    private void setFuelPrice(View convertView, int labelId, int priceId, String labelText, String price, boolean visible) {
        TextView tvLabel = convertView.findViewById(labelId);
        tvLabel.setText(String.format("%s:", labelText));
        tvLabel.setVisibility(visible ? View.VISIBLE : View.GONE);

        TextView tvPrice = convertView.findViewById(priceId);
        tvPrice.setText(price);
        tvPrice.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
