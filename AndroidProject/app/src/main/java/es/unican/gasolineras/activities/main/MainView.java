package es.unican.gasolineras.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.info.InfoView;
import es.unican.gasolineras.activities.details.DetailsView;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.model.Combustible;
import es.unican.gasolineras.common.Filtros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Municipio;
import es.unican.gasolineras.model.Orden;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The main view of the application. It shows a list of gas stations.
 */
@AndroidEntryPoint
public class MainView extends AppCompatActivity implements IMainContract.View {

    /** The presenter of this view */
    private MainPresenter presenter;
    private Combustible combustibleSeleccionado; // guarda la seleccion si se reabre el popup
    private Orden ordenSeleccionada;
    private Spinner spnMunicipios;

    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The default theme does not include a toolbar.
        // In this app the toolbar is explicitly declared in the layout
        // Set this toolbar as the activity ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate presenter and launch initial business logic
        presenter = new MainPresenter();
        presenter.init(this);
        presenter.setFiltros(new Filtros());
    }

    /**
     * This creates the menu that is shown in the action bar (the upper toolbar)
     * @param menu The options menu in which you place your items.
     *
     * @return true because we are defining a new menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * This is called when an item in the action bar menu is selected.
     * @param item The menu item that was selected.
     *
     * @return true if we have handled the selection
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuItemInfo) {
            presenter.onMenuInfoClicked();
            return true;
        }
        if (itemId == R.id.menuFilterButton) {
            presenter.onFilterButtonClicked();
            return true;
        }
        if (itemId == R.id.menuOrdenButton) {
            presenter.onOrdenarButtonClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @see IMainContract.View#init()
     */
    @Override
    public void init() {
        // initialize on click listeners (when clicking on a station in the list)
        ListView list = findViewById(R.id.lvStations);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Gasolinera station = (Gasolinera) parent.getItemAtPosition(position);
            presenter.onStationClicked(station);
        });
    }

    /**
     * @see IMainContract.View#getGasolinerasRepository()
     * @return the repository to access the data
     */
    @Override
    public IGasolinerasRepository getGasolinerasRepository() {
        return repository;
    }

    /**
     * @see IMainContract.View#showStations(List)
     * @param stations the list of charging stations
     */
    @Override
    public void showStations(List<Gasolinera> stations) {
        ListView list = findViewById(R.id.lvStations);
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, stations, combustibleSeleccionado);
        list.setAdapter(adapter);
    }

    /**
     * @see IMainContract.View#showLoadCorrect(int)
     */
    @Override
    public void showLoadCorrect(int stations) {
        Toast.makeText(this, "Cargadas " + stations + " gasolineras", Toast.LENGTH_SHORT).show();
    }

    /**
     * @see IMainContract.View#showLoadError()
     */
    @Override
    public void showLoadError() {
        Toast.makeText(this, "Error cargando las gasolineras", Toast.LENGTH_SHORT).show();
    }

    /**
     * @see IMainContract.View#showStationDetails(Gasolinera)
     * @param station the charging station
     */
    @Override
    public void showStationDetails(Gasolinera station) {
        Intent intent = new Intent(this, DetailsView.class);
        intent.putExtra(DetailsView.INTENT_STATION, Parcels.wrap(station));
        startActivity(intent);
    }

    /**
     * @see IMainContract.View#showInfoActivity()
     */
    @Override
    public void showInfoActivity() {
        Intent intent = new Intent(this, InfoView.class);
        startActivity(intent);

    }

    @Override
    public void showFiltersPopUp() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_filters_popup, null);

        Spinner spnProvincias = view.findViewById(R.id.spnProvincias);
        spnMunicipios = view.findViewById(R.id.spnMunicipio);
        Spinner spnCompanhia = view.findViewById(R.id.spnCompanhia);
        CheckBox checkEstado = view.findViewById(R.id.cbAbierto);
        RelativeLayout rlCombustible = view.findViewById(R.id.rlCombustible);
        TextView tvCombustible = rlCombustible.findViewById(R.id.tvListaCombustibles);

        asignaAdapterASpinner(spnProvincias, R.array.provincias_espana);
        asignaAdapterASpinner(spnCompanhia, R.array.lista_companhias);
        List<String> seleccionadosList = new ArrayList<>();

        spnProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provinciaSeleccionada = spnProvincias.getSelectedItem().toString();
                presenter.onProvinciaSelected(provinciaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        new AlertDialog.Builder(this)
                .setTitle("Filtrar Gasolineras")
                .setView(view)
                .setPositiveButton("Buscar", (dialog, which) -> applyFilters(spnProvincias, spnCompanhia, checkEstado, seleccionadosList))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();

        configureFuelSelection(rlCombustible, tvCombustible, seleccionadosList);
    }

    /**
     * @see IMainContract.View#showOrdenarPopUp()
     */
    @Override
    public void showOrdenarPopUp() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_ordenar_popup, null);

        Spinner spnCombustible = view.findViewById(R.id.spnCombustible);
        Spinner spnOrden = view.findViewById(R.id.spnOrden);

        ArrayAdapter<Combustible> adapterCombustible = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Combustible.values());
        adapterCombustible.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCombustible.setAdapter(adapterCombustible);

        ArrayAdapter<Orden> adapterOrden = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Orden.values());
        adapterOrden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrden.setAdapter(adapterOrden);

        // Establecer las selecciones previas si existen
        if (combustibleSeleccionado != null) {
            spnCombustible.setSelection(combustibleSeleccionado.ordinal());
        }
        if (ordenSeleccionada != null) {
            spnOrden.setSelection(ordenSeleccionada.ordinal());
        }

        new AlertDialog.Builder(this)
                .setTitle("Ordenar Gasolineras")
                .setView(view)
                .setPositiveButton("Ordenar", (dialog, which) -> {
                    combustibleSeleccionado = (Combustible) spnCombustible.getSelectedItem();
                    ordenSeleccionada = (Orden) spnOrden.getSelectedItem();

                    presenter.ordenarGasolinerasPorPrecio(combustibleSeleccionado, ordenSeleccionada);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    /**
     * @see IMainContract.View#updateMunicipiosSpinner(List municipios) 
     */
    @Override
    public void updateMunicipiosSpinner(List<Municipio> municipios) {
        List<String> nombresMunicipios = new ArrayList<>();
        nombresMunicipios.add("-");
        for (Municipio municipio : municipios) {
            nombresMunicipios.add(municipio.getNombre());
        }
        ArrayAdapter<String> municipiosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresMunicipios);
        municipiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMunicipios.setAdapter(municipiosAdapter);
    }

    /**
     * Configura un Spinner con un ArrayAdapter basado en un recurso de array de strings.
     *
     * @param spinner El spinner al que se asignará el adapter.
     * @param arrayResourceId El identificador del recurso del array.
     */
    private void asignaAdapterASpinner (Spinner spinner, int arrayResourceId) {
        String[] array = getResources().getStringArray(arrayResourceId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void applyFilters(Spinner spnProvincias, Spinner spnCompanhia, CheckBox checkEstado, List<String> seleccionadosList) {
        String provincia = spnProvincias.getSelectedItem().toString();
        String municipio = spnMunicipios.getSelectedItem() != null ?
                spnMunicipios.getSelectedItem().toString() : "";
        String companhia = spnCompanhia.getSelectedItem().toString();
        boolean abierto = checkEstado.isChecked();
        List<String> combustiblesSeleccionados = new ArrayList<>(seleccionadosList);

        try {
            presenter.onSearchStationsWithFilters(provincia, municipio, companhia, combustiblesSeleccionados, abierto);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureFuelSelection(RelativeLayout rlCombustible, TextView tvCombustible, List<String> seleccionadosList) {
        String[] opcionesCombustibles = getResources().getStringArray(R.array.lista_gasolinas);
        boolean[] seleccionados = new boolean[opcionesCombustibles.length];

        rlCombustible.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tipología de Combustible")
                    .setMultiChoiceItems(opcionesCombustibles, seleccionados, (dialog, which, isChecked) -> {
                        if (isChecked) {
                            seleccionadosList.add(opcionesCombustibles[which]);
                        } else {
                            seleccionadosList.remove(opcionesCombustibles[which]);
                        }
                    })
                    .setPositiveButton("Aceptar", (dialog, which) -> updateFuleText(tvCombustible, seleccionadosList))
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .setNeutralButton("Borrar", (dialog, which) -> clearSelection(seleccionados, seleccionadosList, tvCombustible))
                    .create()
                    .show();
        });
    }

    private void updateFuleText(TextView tvCombustible, List<String> seleccionadosList) {
        if (seleccionadosList.isEmpty()) {
            tvCombustible.setText("Selecciona combustibles");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : seleccionadosList) {
                stringBuilder.append(s).append(", ");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 2); // Elimina la última coma
            }
            tvCombustible.setText(stringBuilder.toString());
        }
    }

    private void clearSelection(boolean[] seleccionados, List<String> seleccionadosList, TextView tvCombustible) {
        seleccionadosList.clear();
        Arrays.fill(seleccionados, false);
        tvCombustible.setText("Selecciona combustibles");
    }
}
