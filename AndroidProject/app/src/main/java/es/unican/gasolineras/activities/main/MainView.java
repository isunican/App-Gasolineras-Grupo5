package es.unican.gasolineras.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.parceler.Parcels;
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
        Spinner spnMunicipios = view.findViewById(R.id.spnMunicipio);
        Spinner spnCompanhia = view.findViewById(R.id.spnCompanhia);
        CheckBox checkEstado = view.findViewById(R.id.cbAbierto);

        String[] provinciasArray = getResources().getStringArray(R.array.provincias_espana);
        ArrayAdapter<String> provinciasAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, provinciasArray);
        provinciasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProvincias.setAdapter(provinciasAdapter);

        String[] municipiosArray = getResources().getStringArray(R.array.lista_municipios);
        ArrayAdapter<String> municipiosAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, municipiosArray);
        municipiosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMunicipios.setAdapter(municipiosAdapter);

        String[] companhiasArray = getResources().getStringArray(R.array.lista_companhias);
        ArrayAdapter<String> companhiasAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, companhiasArray);
        companhiasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCompanhia.setAdapter(companhiasAdapter);

        new AlertDialog.Builder(this)
                .setTitle("Filtrar Gasolineras")
                .setView(view)
                .setPositiveButton("Buscar", (dialog, which) -> {
                    String provincia = spnProvincias.getSelectedItem().toString();
                    String municipio = spnMunicipios.getSelectedItem().toString();
                    String companhia = spnCompanhia.getSelectedItem().toString();
                    boolean abierto = checkEstado.isChecked();

                    try {
                        presenter.onSearchStationsWithFilters(provincia, municipio, companhia, abierto);
                    } catch (DataAccessException e) {
                        throw new RuntimeException(e);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

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
}
