package es.unican.gasolineras.activities.details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.parceler.Parcels;

import es.unican.gasolineras.R;
import es.unican.gasolineras.model.Gasolinera;

/**
 * View that shows the details of one gas station. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
public class DetailsView extends AppCompatActivity {

    /** Key for the intent that contains the gas station */
    public static final String INTENT_STATION = "INTENT_STATION";

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;  // to avoid warning in the line below
        bar.setDisplayHomeAsUpEnabled(true);  // show back button in action bar

        // Link to view elements
        ImageView ivRotulo = findViewById(R.id.ivRotulo);
        TextView tvRotulo = findViewById(R.id.tvRotulo);
        TextView tvMunicipio = findViewById(R.id.tvMunicipio);
        TextView tvDireccion = findViewById(R.id.tvDireccion);
        TextView tvHorario = findViewById(R.id.tvHorario);
        TextView tvPrecioSumario = findViewById(R.id.tvPrecioSumario);
        TextView tvPrecioDiesel = findViewById(R.id.tvPrecioDiesel);
        TextView tvPrecioGasolina = findViewById(R.id.tvPrecioGasolina);

        // Get Gas Station from the intent that triggered this activity
        Gasolinera gasolinera = Parcels.unwrap(getIntent().getExtras().getParcelable(INTENT_STATION));

        // Set logo
        @SuppressLint("DiscouragedApi") int imageID =
                getResources().getIdentifier("generic", "drawable", getPackageName());
        ivRotulo.setImageResource(imageID);

        // Set Texts
        tvRotulo.setText(gasolinera.getRotulo());
        tvMunicipio.setText(gasolinera.getMunicipio());
        tvDireccion.setText(gasolinera.getDireccion());
        tvHorario.setText(gasolinera.getHorario());
        tvPrecioSumario.setText(toString().valueOf(gasolinera.calculateSummarizedPrice()));

        // Si el precio de un combustible es 0, se muestra - en vez de 0
        if (gasolinera.getGasoleoA() == 0 || gasolinera.getGasoleoA() < 0.0) {
            tvPrecioDiesel.setText("-");
            Toast.makeText(this, "Error: Datos de diesel no disponibles.", Toast.LENGTH_SHORT).show();
        } else {
            tvPrecioDiesel.setText(toString().valueOf(gasolinera.getGasoleoA()));
        }
        if (gasolinera.getGasolina95E5() == 0 || gasolinera.getGasolina95E5() < 0.0) {
            tvPrecioGasolina.setText("-");
            Toast.makeText(this, "Error: Datos de gasolina no disponibles.", Toast.LENGTH_SHORT).show();
        } else {
            tvPrecioGasolina.setText(toString().valueOf(gasolinera.getGasolina95E5()));
        }
        if (gasolinera.getGasoleoA() == 0 && gasolinera.getGasolina95E5() == 0) {
            tvPrecioSumario.setText("-");
            Toast.makeText(this, "Error: Datos no disponibles.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @see AppCompatActivity#onOptionsItemSelected(MenuItem)
     * @param item The menu item that was selected.
     *
     * @return true if we are handling the selection
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}