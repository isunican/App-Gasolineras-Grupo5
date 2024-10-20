package es.unican.gasolineras.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;

/**
 * View that shows the filters that you can apply to search gas stations.
 * Since this view does not have business logic, it can be implemented as
 * an activity directly, without the MVP pattern.
 */
public class FiltersView extends AppCompatActivity {

    /**
     * @see AppCompatActivity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_popup);

        //Link to view elements
        TextView tvProvincia = findViewById(R.id.tvProvincia);
        Spinner spnProvincias = findViewById(R.id.spnProvincias);
        TextView tvLocalidad = findViewById(R.id.tvLocalidad);
        EditText etLocalidad = findViewById(R.id.etLocalidad);
        CheckBox cbAbierto = findViewById(R.id.cbAbierto);

        // Crear un ArrayAdapter usando el array de provincias y un layout por defecto
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.provincias_espana,  // Array de provincias definido en strings.xml
                android.R.layout.simple_spinner_item // Layout estándar para los elementos del Spinner
        );

        // Establecer el layout a usar cuando se despliega el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplicar el adaptador al Spinner
        spnProvincias.setAdapter(adapter);

        // Listener para cambios en la CheckBox
        cbAbierto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Acción a realizar cuando la CheckBox cambia su estado
                if (isChecked) {
                    // La CheckBox está activada
                    // Puedes realizar alguna acción aquí si se marca
                    // Ejemplo: habilitar un botón, cambiar un texto, etc.
                } else {
                    // La CheckBox está desactivada
                    // Puedes realizar alguna acción aquí si se desmarca
                }
            }
        });
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
