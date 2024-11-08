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
import es.unican.gasolineras.model.Combustible;
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
    private Combustible combustibleSeleccionado;

    /**
     * Constructs an adapter to handle a list of gasolineras
     * @param context the application context
     * @param objects the list of gas stations
     */
    public GasolinerasArrayAdapter(@NonNull Context context, @NonNull List<Gasolinera> objects, Combustible combustibleSeleccionado) {
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
        {
            String rotulo = gasolinera.getRotulo().toLowerCase();

            int imageID = context.getResources()
                    .getIdentifier(rotulo, "drawable", context.getPackageName());

            // Si el rotulo son sólo numeros, el método getIdentifier simplemente devuelve
            // como imageID esos números, pero eso va a fallar porque no tendré ningún recurso
            // que coincida con esos números
            if (imageID == 0 || TextUtils.isDigitsOnly(rotulo)) {
                imageID = context.getResources()
                        .getIdentifier("generic", "drawable", context.getPackageName());
            }

            if (imageID != 0) {
                ImageView view = convertView.findViewById(R.id.ivLogo);
                view.setImageResource(imageID);
            }
        }
        // status
        {
            TextView tv = convertView.findViewById(R.id.tvEstado);
            try {
                tv.setText(gasolinera.compruebaEstado(gasolinera.getHorario()));
            } catch (IllegalArgumentException e) {
                Toast.makeText(context, "Error de argumento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                tv.setText("Error de argumento: " + e.getMessage());
            }
        }

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
        if (combustibleSeleccionado == null) {
            setFuelPrice(convertView, R.id.tv95Label, R.id.tv95, context.getString(R.string.gasolina95label),
                    String.valueOf(gasolinera.getGasolina95E5()), true);
            setFuelPrice(convertView, R.id.tvDieselALabel, R.id.tvDieselA, context.getString(R.string.dieselAlabel),
                    String.valueOf(gasolinera.getGasoleoA()), true);
        } else {
            switch (combustibleSeleccionado) {
                case GASOLEOA:
                    setFuelPrice(convertView, R.id.tv95Label, R.id.tv95, context.getString(R.string.dieselAlabel),
                            String.valueOf(gasolinera.getGasoleoA()), true);
                    setFuelPrice(convertView, R.id.tvDieselALabel, R.id.tvDieselA, context.getString(R.string.dieselAlabel),
                            String.valueOf(gasolinera.getGasoleoA()), false);
                    break;
                case GASOLINA95E:
                    setFuelPrice(convertView, R.id.tv95Label, R.id.tv95, context.getString(R.string.gasolina95label),
                            String.valueOf(gasolinera.getGasolina95E5()), true);
                    setFuelPrice(convertView, R.id.tvDieselALabel, R.id.tvDieselA, context.getString(R.string.dieselAlabel),
                            String.valueOf(gasolinera.getGasoleoA()), false);
                    break;
                case GASOLINA98E:
                    setFuelPrice(convertView, R.id.tv95Label, R.id.tv95, context.getString(R.string.gasolina98label),
                            String.valueOf(gasolinera.getGasolina98E5()), true);
                    setFuelPrice(convertView, R.id.tvDieselALabel, R.id.tvDieselA, context.getString(R.string.dieselAlabel),
                            String.valueOf(gasolinera.getGasoleoA()), false);
                    break;
                case BIODIESEL:
                    setFuelPrice(convertView, R.id.tv95Label, R.id.tv95, context.getString(R.string.biolabel),
                            String.valueOf(gasolinera.getBiodiesel()), true);
                    setFuelPrice(convertView, R.id.tvDieselALabel, R.id.tvDieselA, context.getString(R.string.dieselAlabel),
                            String.valueOf(gasolinera.getGasoleoA()), false);
                    break;
            }

        }
        return convertView;
        }


    private void setFuelPrice(View convertView, int labelId, int priceId, String labelText, String price, boolean visible) {
        TextView tvLabel = convertView.findViewById(labelId);
        tvLabel.setText(String.format("%s:", labelText));
        tvLabel.setVisibility(visible ? View.VISIBLE : View.GONE);

        TextView tvPrice = convertView.findViewById(priceId);
        tvPrice.setText(price);
        tvPrice.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
