package es.unican.gasolineras.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.slider.Slider;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.info.InfoView;
import es.unican.gasolineras.activities.details.DetailsView;
import es.unican.gasolineras.common.Filtros;
import es.unican.gasolineras.model.FiltrosSeleccionados;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Municipio;
import es.unican.gasolineras.repository.IGasolinerasRepository;

/**
 * The main view of the application. It shows a list of gas stations.
 */
@AndroidEntryPoint
public class MainView extends AppCompatActivity implements IMainContract.View {

    /** The presenter of this view */
    private MainPresenter presenter;
    private List<String> combustiblesSeleccionados;
    private String combustibleOrdenar;
    private String ordenSeleccionada;
    private Spinner spnMunicipios;
    private static final String CANCELAR = "Cancelar";
    private static final String FILTERSPREFERENCE = "FiltersPreference";
    private static final String MUNICIPIO = "municipio";
    /** The repository to access the data. This is automatically injected by Hilt in this class */
    @Inject
    IGasolinerasRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetSharedPreferences();

        combustiblesSeleccionados = new ArrayList<>();

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
        if (itemId == R.id.menuCoordenadasButton) {
            presenter.onCoordinatesButtonClicked();
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
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, stations, combustiblesSeleccionados);
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

    /**
     * @see IMainContract.View#showFiltersPopUp()
     */
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

        // Cargar filtros previos
        FiltrosSeleccionados filtros = cargarFiltros();
        configurarFiltros(filtros, spnProvincias, spnMunicipios, spnCompanhia, checkEstado, tvCombustible);

        spnProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provinciaSeleccionada = spnProvincias.getSelectedItem().toString();
                presenter.onProvinciaSelected(provinciaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No hace nada
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("Filtrar Gasolineras")
                .setView(view)
                .setPositiveButton("Buscar", (dialog, which) -> {
                    aplicarFiltros(filtros, spnProvincias, spnCompanhia, checkEstado);
                    guardarFiltros(filtros);
                })
                .setNegativeButton(CANCELAR, (dialog, which) -> dialog.dismiss())
                .create()
                .show();

        configureFuelSelection(rlCombustible, tvCombustible, filtros.getCombustibles());
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

        asignaAdapterASpinner(spnCombustible, R.array.lista_combustibles);
        asignaAdapterASpinner(spnOrden, R.array.lista_orden);

        if (combustibleOrdenar != null) {
            spnCombustible.setSelection(getPositionInSpinner(spnCombustible, combustibleOrdenar));
        }
        if (!combustiblesSeleccionados.isEmpty()) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, combustiblesSeleccionados);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCombustible.setAdapter(adapter1);
            if (combustibleOrdenar != null) {
                spnCombustible.setSelection(adapter1.getPosition(combustibleOrdenar));
            }
        }
        if (ordenSeleccionada != null) {
            spnOrden.setSelection(getPositionInSpinner(spnOrden, ordenSeleccionada));
        }

        new AlertDialog.Builder(this)
                .setTitle("Ordenar Gasolineras")
                .setView(view)
                .setPositiveButton("Ordenar", (dialog, which) -> {
                    combustibleOrdenar = spnCombustible.getSelectedItem().toString();
                    ordenSeleccionada = spnOrden.getSelectedItem().toString();

                    presenter.ordenarGasolinerasPorPrecio(combustibleOrdenar, ordenSeleccionada);
                    dialog.dismiss();
                })
                .setNegativeButton(CANCELAR, (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    @Override
    public void showCoordinatesPopUp(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_distancia_coordenadas_popup, null);

        EditText etLongitud = view.findViewById(R.id.etLongitud);
        EditText etLatitud = view.findViewById(R.id.etLatitud);
        Slider slider = view.findViewById(R.id.main_slider);
        TextView tvDistancia = view.findViewById(R.id.tvDistancia);

        // Configurar el listener del Slider
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            // Actualiza el TextView con el valor actual del slider
            tvDistancia.setText("Distancia: " + (int) value);
        });

        new AlertDialog.Builder(this)
                .setTitle("Buscar Por Coordenadas")
                .setView(view)
                .setPositiveButton("Buscar", (dialog, which) -> {
                    applyCoordinates(etLatitud,etLongitud,tvDistancia);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void applyCoordinates(EditText etLongitud, EditText etLatitud, TextView tvDistancia){
        try {
            // Obtener los valores de los EditText como String
            String longitudStr = etLongitud.getText().toString().trim();
            String latitudStr = etLatitud.getText().toString().trim();

            // Convertirlos a double
            double longitud = Double.parseDouble(longitudStr);
            double latitud = Double.parseDouble(latitudStr);

            // Obtener el valor del TextView y convertirlo (si se usa como número)
            String distanciaStr = tvDistancia.getText().toString().replaceAll("[^\\d]", ""); // Extraer solo números
            int distancia = distanciaStr.isEmpty() ? 0 : Integer.parseInt(distanciaStr);

            presenter.searchWithCoordinates(longitud, latitud, distancia);
        } catch (NumberFormatException e) {
            // Manejar casos donde no se pueda convertir
            System.err.println("Error: Entrada no válida.");
        }
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

        SharedPreferences prefs = getSharedPreferences(FILTERSPREFERENCE, MODE_PRIVATE);
        String municipioGuardado = prefs.getString(MUNICIPIO, "-");
        int municipioPosition = getPositionInSpinner(spnMunicipios, municipioGuardado);
        if (municipioPosition >= 0) {
            spnMunicipios.setSelection(municipioPosition);
        }
    }

    /**
     * Obtiene la posicion de un valor especifico dentro del adaptador de un Spinner.
     *
     * @param spinner El Spinner cuyo adaptador se va a buscar.
     * @param value   El valor a encontrar dentro del adaptador.
     * @return La posicion del valor en el adaptador, o -1 si el valor no esta presente o el adaptador es nulo.
     */
    private int getPositionInSpinner(Spinner spinner, String value) {
        if (spinner.getAdapter() == null) {
            return -1;
        }

        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(value)) {
                return i;
            }
        }
        return -1;
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
     * Carga los filtros guardados desde las preferencias.
     *
     * @return Un objeto {@link FiltrosSeleccionados} con los valores almacenados.
     */
    private FiltrosSeleccionados cargarFiltros() {
        SharedPreferences preferences = getSharedPreferences(FILTERSPREFERENCE, MODE_PRIVATE);

        FiltrosSeleccionados filtros = new FiltrosSeleccionados();
        filtros.setProvincia(preferences.getString("provincia", ""));
        filtros.setMunicipio(preferences.getString(MUNICIPIO, ""));
        filtros.setCompanhia(preferences.getString("companhia", ""));
        filtros.setEstadoAbierto(preferences.getBoolean("estado_abierto", false));

        // Cargar combustibles seleccionados y convertirlos a lista
        String combustiblesString = preferences.getString("combustibles_seleccionados", "");
        if (!combustiblesString.isEmpty()) {
            filtros.setCombustibles(new ArrayList<>(Arrays.asList(combustiblesString.split(","))));
        }

        return filtros;
    }
    /**
     * Aplica los filtros seleccionados para realizar la busqueda de gasolineras.
     *
     * @param filtros
     * @param spnProvincias Spinner para la seleccion de provincia.
     * @param spnCompanhia Spinner para la seleccion de companhia.
     * @param checkEstado Checkbox para indicar el estado de apertura.
     * @param tvCombustible TextView que indica los combustibles seleciconados.
     */
    private void configurarFiltros(FiltrosSeleccionados filtros, Spinner spnProvincias, Spinner spnMunicipios,
                                   Spinner spnCompanhia, CheckBox checkEstado, TextView tvCombustible) {
        // Configurar spinner provincia
        if (!filtros.getProvincia().isEmpty()) {
            int posProvincia = ((ArrayAdapter<String>) spnProvincias.getAdapter()).getPosition(filtros.getProvincia());
            spnProvincias.setSelection(posProvincia);
        }
        // Configurar spinner companhia
        if (!filtros.getCompanhia().isEmpty()) {
            int posCompanhia = ((ArrayAdapter<String>) spnCompanhia.getAdapter()).getPosition(filtros.getCompanhia());
            spnCompanhia.setSelection(posCompanhia);
        }
        // Configurar lista combustibles
        combustiblesSeleccionados = filtros.getCombustibles();
        updateFuelText(tvCombustible);
        // Configurar checkbox estado
        checkEstado.setChecked(filtros.isEstadoAbierto());
        // Configurar spinner municipio
        spnMunicipios.post(() -> {
            int posMunicipio = getPositionInSpinner(spnMunicipios, filtros.getMunicipio());
            if (posMunicipio >= 0) spnMunicipios.setSelection(posMunicipio);
        });
    }
    /**
     * Aplica los filtros seleccionados al objeto de filtros y ejecuta la busqueda de estaciones
     * con los valores proporcionados en los controles de la interfaz.
     *
     * @param filtros        Objeto que almacena los valores seleccionados de los filtros.
     * @param spnProvincias  Spinner para seleccionar la provincia.
     * @param spnCompanhia   Spinner para seleccionar la companhia.
     * @param checkEstado    Checkbox que indica si se debe filtrar por estaciones abiertas.
     */
    private void aplicarFiltros(FiltrosSeleccionados filtros, Spinner spnProvincias,
                                Spinner spnCompanhia, CheckBox checkEstado) {
        filtros.setProvincia(spnProvincias.getSelectedItem().toString());
        filtros.setMunicipio(spnMunicipios.getSelectedItem() != null ?
                spnMunicipios.getSelectedItem().toString() : "-");
        filtros.setCompanhia(spnCompanhia.getSelectedItem().toString());
        filtros.setEstadoAbierto(checkEstado.isChecked());

        presenter.onSearchStationsWithFilters(
                filtros.getProvincia(),
                filtros.getMunicipio(),
                filtros.getCompanhia(),
                filtros.getCombustibles(),
                filtros.isEstadoAbierto()
        );
    }

    /**
     * Guarda los filtros seleccionados en las preferencias compartidas.
     *
     * @param filtros Los filtros seleccionados que se deben guardar.
     */
    private void guardarFiltros(FiltrosSeleccionados filtros) {
        SharedPreferences preferences = getSharedPreferences(FILTERSPREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("provincia", filtros.getProvincia());
        editor.putString(MUNICIPIO, filtros.getMunicipio());
        editor.putString("companhia", filtros.getCompanhia());
        editor.putBoolean("estado_abierto", filtros.isEstadoAbierto());

        // Guardar combustibles seleccionados como cadena separada por comas
        String combustiblesString = TextUtils.join(",", filtros.getCombustibles());
        editor.putString("combustibles_seleccionados", combustiblesString);
        editor.apply();
    }

    /**
     * Configura la seleccion de combustibles en un cuadro de dialogo para que el usuario pueda elegir
     * multiples opciones y actualiza la vista correspondiente.
     *
     * @param rlCombustible   el contenedor que activa la seleccion de combustibles.
     * @param tvCombustible   el TextView que muestra la lista de combustibles seleccionados.
     * @param tempCombustiblesSeleccionados una lista para almacenar las opciones de combustibles seleccionadas.
     */
    private void configureFuelSelection(RelativeLayout rlCombustible, TextView tvCombustible, List<String> tempCombustiblesSeleccionados) {
        String[] opcionesCombustibles = getResources().getStringArray(R.array.lista_gasolinas);
        boolean[] seleccionados = new boolean[opcionesCombustibles.length];

        // Marcar como seleccionados los combustibles que ya estan en la lista de seleccionados
        for (int i = 0; i < opcionesCombustibles.length; i++) {
            if (tempCombustiblesSeleccionados.contains(opcionesCombustibles[i])) {
                seleccionados[i] = true;
            }
        }

        rlCombustible.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tipología de Combustible")
                    .setMultiChoiceItems(opcionesCombustibles, seleccionados, (dialog, which, isChecked) -> {
                        if (isChecked) {
                            tempCombustiblesSeleccionados.add(opcionesCombustibles[which]);
                        } else {
                            tempCombustiblesSeleccionados.remove(opcionesCombustibles[which]);
                        }
                    })
                    .setPositiveButton("Aceptar", (dialog, which) -> updateFuelText(tvCombustible))
                    .setNegativeButton(CANCELAR, (dialog, which) -> dialog.dismiss())
                    .setNeutralButton("Borrar", (dialog, which) -> clearSelection(seleccionados, tempCombustiblesSeleccionados, tvCombustible))
                    .create()
                    .show();
        });
    }

    /**
     * Actualiza el texto de un TextView con la lista de combustibles seleccionados.
     * Si no hay combustibles seleccionados, muestra un texto indicando que se deben seleccionar.
     *
     * @param tvCombustible El TextView a actualizar con los combustibles seleccionados.
     */
    private void updateFuelText(TextView tvCombustible) {
        if (combustiblesSeleccionados.isEmpty()) {
            tvCombustible.setText(R.string.selecciona_combustibles);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : combustiblesSeleccionados) {
                stringBuilder.append(s).append(", ");
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 2);
            }
            tvCombustible.setText(stringBuilder.toString());
        }
    }

    /**
     * Limpia la seleccion de combustibles, desmarca todas las opciones seleccionadas y actualiza
     * el texto del TextView para reflejar el cambio.
     *
     * @param seleccionados   un array booleano que representa los elementos seleccionados.
     * @param tempCombustiblesSeleccionados una lista para almacenar las opciones de combustibles seleccionadas.
     * @param tvCombustible   el TextView que muestra la lista de combustibles seleccionados.
     */
    private void clearSelection(boolean[] seleccionados, List<String> tempCombustiblesSeleccionados, TextView tvCombustible) {
        tempCombustiblesSeleccionados.clear();
        Arrays.fill(seleccionados, false);
        tvCombustible.setText(R.string.selecciona_combustibles);
    }

    /**
     * Restablece las preferencias compartidas eliminando todos los filtros guardados.
     */
    private void resetSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences(FILTERSPREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}


