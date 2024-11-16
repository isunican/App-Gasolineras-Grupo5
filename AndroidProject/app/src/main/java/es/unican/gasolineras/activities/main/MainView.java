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
import android.widget.Switch;
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
    List<Combustible> combustibleSeleccionado = new ArrayList<>(); // guarda la seleccion si se reabre el popup
    private Combustible combustibleOrdenar;
    private Orden ordenSeleccionada;
    private Spinner spnMunicipios;
    boolean[] seleccionados;
    List<String> combustiblesSeleccionados = new ArrayList<>();
    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seleccionados = new boolean[getResources().getStringArray(R.array.lista_gasolinas).length];
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
        List<String> combustiblesSeleccionados = new ArrayList<>();

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
                .setPositiveButton("Buscar", (dialog, which) -> applyFilters(spnProvincias, spnCompanhia, checkEstado))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();

         configureFuelSelection(rlCombustible, tvCombustible);
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

        ArrayAdapter<Combustible> adapterCombustible;

        adapterCombustible = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Combustible.values());


        adapterCombustible.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCombustible.setAdapter(adapterCombustible);

        ArrayAdapter<Orden> adapterOrden = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Orden.values());
        adapterOrden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrden.setAdapter(adapterOrden);

        // Establecer las selecciones previas si existen
        if (combustibleOrdenar != null) {
            spnCombustible.setSelection(combustibleOrdenar.ordinal());
        }

        if (ordenSeleccionada != null) {
            spnOrden.setSelection(ordenSeleccionada.ordinal());
        }

        new AlertDialog.Builder(this)
                .setTitle("Ordenar Gasolineras")
                .setView(view)
                .setPositiveButton("Ordenar", (dialog, which) -> {
                    combustibleOrdenar = (Combustible) spnCombustible.getSelectedItem();;
                    ordenSeleccionada = (Orden) spnOrden.getSelectedItem();

                    presenter.ordenarGasolinerasPorPrecio(combustibleOrdenar, ordenSeleccionada);
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

    /**
     * Aplica los filtros seleccionados en la interfaz de usuario y realiza la busqueda de estaciones de servicio.
     *
     * @param spnProvincias   el Spinner que contiene la lista de provincias.
     * @param spnCompanhia    el Spinner que contiene la lista de companhias.
     * @param checkEstado     el CheckBox que indica si el estado "abierto" esta seleccionado.

     */
    private void applyFilters(Spinner spnProvincias, Spinner spnCompanhia, CheckBox checkEstado) {
        String provincia = spnProvincias.getSelectedItem().toString();
        String municipio = spnMunicipios.getSelectedItem() != null ?
                spnMunicipios.getSelectedItem().toString() : "";
        String companhia = spnCompanhia.getSelectedItem().toString();
        boolean abierto = checkEstado.isChecked();

        presenter.onSearchStationsWithFilters(provincia, municipio, companhia, this.combustiblesSeleccionados, abierto);
    }

    /**
     * Configura la seleccion de combustibles en un cuadro de dialogo para que el usuario pueda elegir
     * multiples opciones y actualiza la vista correspondiente.
     *
     * @param rlCombustible   el contenedor que activa la seleccion de combustibles.
     * @param tvCombustible   el TextView que muestra la lista de combustibles seleccionados.
     *
     */
    private void configureFuelSelection(RelativeLayout rlCombustible, TextView tvCombustible) {
        String[] opcionesCombustibles = getResources().getStringArray(R.array.lista_gasolinas);



        rlCombustible.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tipología de Combustible")
                    .setMultiChoiceItems(opcionesCombustibles, seleccionados, (dialog, which, isChecked) -> {
                        if (isChecked) {
                            this.combustiblesSeleccionados.add(opcionesCombustibles[which]);
                        } else {
                            this.combustiblesSeleccionados.remove(opcionesCombustibles[which]);
                        }

                    })
                    .setPositiveButton("Aceptar", (dialog, which) -> updateFuelText(tvCombustible, this.combustiblesSeleccionados))
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .setNeutralButton("Borrar", (dialog, which) -> clearSelection(seleccionados, this.combustiblesSeleccionados, tvCombustible))
                    .create()
                    .show();
        });




        for(String s : combustiblesSeleccionados){

            switch (s) {
                case ("Gasolina 95 E5"):
                    combustibleSeleccionado.add(Combustible.GASOLINA95E5);
                    break;

                case ("Gasolina 95 E5 Premium"):
                    combustibleSeleccionado.add(Combustible.GASOLINA95E5PREM);
                    break;

                case ("Gasolina 95 E10"):
                    combustibleSeleccionado.add(Combustible.GASOLINA95E10);
                    break;

                case ("Gasolina 98 E5"):
                    combustibleSeleccionado.add(Combustible.GASOLINA98E5);
                    break;

                case ("Gasolina 98 E10"):
                    combustibleSeleccionado.add(Combustible.GASOLINA98E10);
                    break;


            }
        }
        }



    /**
     * Actualiza el texto del TextView para reflejar los combustibles seleccionados.
     *
     * @param tvCombustible   el TextView que mostrara los combustibles seleccionados.
     * @param combustiblesSeleccionados una lista con los combustibles seleccionados por el usuario.
     */
    public void updateFuelText(TextView tvCombustible, List<String> combustiblesSeleccionados) {
        if (combustiblesSeleccionados.isEmpty()) {
            tvCombustible.setText(R.string.selecciona_combustibles);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : combustiblesSeleccionados) {
                stringBuilder.append(s).append(", ");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 2); // Elimina la última coma
            }
            tvCombustible.setText(stringBuilder.toString());
        }
    }

    /**
     * Limpia la seleccion de combustibles, desmarca todas las opciones seleccionadas y actualiza
     * el texto del TextView para reflejar el cambio.
     *
     * @param seleccionados   un array booleano que representa los elementos seleccionados.
     * @param combustiblesSeleccionados una lista para almacenar las opciones de combustibles seleccionadas.
     * @param tvCombustible   el TextView que muestra la lista de combustibles seleccionados.
     */
    private void clearSelection(boolean[] seleccionados, List<String> combustiblesSeleccionados, TextView tvCombustible) {
        combustiblesSeleccionados.clear();
        Arrays.fill(seleccionados, false);
        tvCombustible.setText(R.string.selecciona_combustibles);
    }
}
