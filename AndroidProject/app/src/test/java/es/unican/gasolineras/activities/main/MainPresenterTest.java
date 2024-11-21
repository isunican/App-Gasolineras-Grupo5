package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.utils.MockRepositories;


@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    private MainPresenter presenter2;

    @Mock
    private IMainContract.View mockView;
    @Mock
    private IMainContract.View mockView2;
    @Mock
    private IMainContract.View mockView3;

    @Mock
    private IGasolinerasRepository mockRepository;
    @Mock
    private IGasolinerasRepository mockRepository2;
    @Mock
    private IGasolinerasRepository mockRepository3;
    private MainPresenter presenter4 = new MainPresenter();
    @Mock
    private IMainContract.View mockView4;
    @Mock
    private IFiltros mockFilters4;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository4;

    @Mock
    private IFiltros mockFilters;
    @Mock
    private IFiltros mockFilters2;

    @Mock
    private IFiltros mockFilters3;
    @Mock
    private MainPresenter sut;
    @Mock
    private MainPresenter sut2;
    @Mock
    private MainPresenter sut3;

    private IGasolinerasRepository repository;
    private List<Gasolinera> gasolineras;
    private List<Gasolinera> gasolineras2;
    private List<Gasolinera> gasolineras3;
    private MainPresenter presenter5 = new MainPresenter();
    private IMainContract.View mockView5 = Mockito.mock(IMainContract.View.class);
    private IGasolinerasRepository repository5 = Mockito.mock(IGasolinerasRepository.class);
    private Gasolinera repsol, carrefour, ballenoil, shell, petronor, avia, cepsa;
    private List<Gasolinera> gasolineras5;
    // mockea la main view
    private IMainContract.View view5 = Mockito.mock(IMainContract.View.class);

    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);

// Inicializar el primer presenter
        repository = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);
        when(mockView.getGasolinerasRepository()).thenReturn(repository);

        presenter = new MainPresenter();
        presenter.init(mockView);
        presenter.setFiltros(mockFilters);

        // Carga el repositorio de prueba con datos del JSON
        repository4 = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);

        when(mockView4.getGasolinerasRepository()).thenReturn(repository4);

        presenter4.init(mockView4);
        presenter4.setFiltros(mockFilters4);

        // Inicializar el segundo presenter
        gasolineras = Generador.generarGasolineras();


        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras);
            return null;
        }).when(mockRepository).requestGasolineras(any(ICallBack.class));


        when(mockView2.getGasolinerasRepository()).thenReturn(mockRepository);

        presenter2 = new MainPresenter();
        presenter2.init(mockView2);
        presenter2.setFiltros(mockFilters2);

        when(mockView.getGasolinerasRepository()).thenReturn(mockRepository);

        sut = new MainPresenter();
        sut.init(mockView);
        sut.setFiltros(mockFilters);

        gasolineras2 = Generador.generarGasolinerasCompanhia();

        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras2);
            return null;
        }).when(mockRepository2).requestGasolineras(any(ICallBack.class));

        when(mockView2.getGasolinerasRepository()).thenReturn(mockRepository2);

        sut2 = new MainPresenter();
        sut2.init(mockView2);
        sut2.setFiltros(mockFilters2);

        gasolineras3 = Generador.generarGasolinerasCompanhia();

        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras3);
            return null;
        }).when(mockRepository3).requestGasolineras(any(ICallBack.class));

        when(mockView3.getGasolinerasRepository()).thenReturn(mockRepository3);

        sut3 = new MainPresenter();
        sut3.init(mockView3);
        sut3.setFiltros(mockFilters3);
        presenter5.setView(view5);
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa = Mockito.mock(Gasolinera.class);

        gasolineras5 = new ArrayList<>(Arrays.asList(repsol, carrefour, ballenoil, shell, petronor, avia, cepsa));

        when(repsol.getBiodiesel()).thenReturn(1.5);
        when(repsol.getGasoleoA()).thenReturn(1.5);
        when(repsol.getGasolina95E5()).thenReturn(1.5);
        when(repsol.getGasolina98E5()).thenReturn(1.5);

        when(carrefour.getBiodiesel()).thenReturn(1.6);
        when(carrefour.getGasoleoA()).thenReturn(1.6);
        when(carrefour.getGasolina95E5()).thenReturn(1.6);
        when(carrefour.getGasolina98E5()).thenReturn(1.6);

        when(ballenoil.getBiodiesel()).thenReturn(1.7);
        when(ballenoil.getGasoleoA()).thenReturn(1.7);
        when(ballenoil.getGasolina95E5()).thenReturn(1.7);
        when(ballenoil.getGasolina98E5()).thenReturn(1.7);

        when(shell.getBiodiesel()).thenReturn(1.8);
        when(shell.getGasoleoA()).thenReturn(1.8);
        when(shell.getGasolina95E5()).thenReturn(1.8);
        when(shell.getGasolina98E5()).thenReturn(1.8);

        when(petronor.getBiodiesel()).thenReturn(1.9);
        when(petronor.getGasoleoA()).thenReturn(0.0);
        when(petronor.getGasolina95E5()).thenReturn(1.9);
        when(petronor.getGasolina98E5()).thenReturn(1.9);

        when(avia.getBiodiesel()).thenReturn(2.0);
        when(avia.getGasoleoA()).thenReturn(0.0);
        when(avia.getGasolina95E5()).thenReturn(2.0);
        when(avia.getGasolina98E5()).thenReturn(2.0);

        when(cepsa.getBiodiesel()).thenReturn(2.1);
        when(cepsa.getGasoleoA()).thenReturn(2.1);
        when(cepsa.getGasolina95E5()).thenReturn(2.1);
        when(cepsa.getGasolina98E5()).thenReturn(2.1);

        presenter5.setGasolineras(gasolineras5);
    }

    //Test onSearchStationsWithFilters Horario
    // Test para el caso UD.1a (Gasolineras abiertas)
    @Test
    public void testOnSearchStationsWithFilters_OpenStations_UD1a() {
        presenter.onSearchStationsWithFilters("-", "", null, null, true);
        verify(mockFilters).filtrarPorEstado(anyList());
        // Verificar que showStations fue llamado con 2 gasolineras abiertas
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1b (Todas las gasolineras)
    @Test
    public void testOnSearchStationsWithFilters_AllStations_UD1b() {
        presenter.onSearchStationsWithFilters("-", "", null, null, false);
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras
    }

    //Test onSearchStationsWithFilters con companhia
    // Test para el caso UD.1a
    @Test
    public void testUD2A() {
        presenter2.onSearchStationsWithFilters("Cantabria", "Santander", "Repsol", null, true);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Repsol"));
        verify(mockFilters2).filtrarPorEstado(anyList());
        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));

        // Verificar que showStations fue llamado con 2 gasolineras
        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void testUD2B() {
        // Llamada al método onSearchStationsWithFilters con los parámetros adecuados
        presenter2.onSearchStationsWithFilters("Madrid", "Alcobendas", "", null, false);

        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq("Alcobendas"));

        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.2C
    @Test
    public void testUD2C() {
        presenter2.onSearchStationsWithFilters("-", "-", "Otros", null, false);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Otros"));

        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras

    }


    // Test onSearchStationsWithFilters con combustibles
    // Test para el caso UD.1A
    @Test
    public void testUD1A() throws Exception {
        sut3.onSearchStationsWithFilters("Cantabria", "Santander", "Repsol", List.of("Gasolina 95 E5", "Gasolina 95 E10", "Gasolina 95 E5 Premium"), true);
        verify(mockFilters3).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockFilters3).filtrarPorCompanhia(anyList(), eq("Repsol"));
        verify(mockFilters3).filtrarPorCombustibles(anyList(), eq(List.of("Gasolina 95 E5", "Gasolina 95 E10", "Gasolina 95 E5 Premium")));

        // Si se pidió filtrar por estado, se verifica que el filtro fue aplicado
        verify(mockFilters3).filtrarPorEstado(anyList());

        // Se verifica que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView3, times(2)).showStations(anyList());
        verify(mockView3, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1B
    @Test
    public void testUD1B() throws Exception {
        // Ejecutar el método bajo prueba con los parámetros de entrada especificados
        sut3.onSearchStationsWithFilters("Cantabria", "Santander", "", null, false);

        // Verificar que filtrarPorProvinciaYMunicipio se llama con finalProvincia=Cantabria y finalMunicipio=Santander
        verify(mockFilters3).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView3, times(2)).showStations(anyList());
        verify(mockView3, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1C
    @Test
    public void testUD1C() throws Exception {
        // Configuración de los parámetros del filtro
        sut3.onSearchStationsWithFilters("Madrid", "", "Shell", List.of("Gasolina 98 E5"), false);

        // Verificar que los métodos de filtro son llamados con los valores esperados
        verify(mockFilters3).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null)); // Municipio vacío
        verify(mockFilters3).filtrarPorCompanhia(anyList(), eq("Shell"));
        verify(mockFilters3).filtrarPorCombustibles(anyList(), eq(List.of("Gasolina 98 E5")));

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView3, times(2)).showStations(anyList());
        verify(mockView3, times(2)).showLoadCorrect(anyInt());
    }


    // Test para el caso UD.1D
    @Test
    public void testUD1D() throws Exception {
        // Configuración de los parámetros del filtro
        sut3.onSearchStationsWithFilters("Cantabria", "Santander", "Campsa", List.of("Gasolin 95 E5"), true);

        // Verificar que los métodos de filtro son llamados con los valores esperados
        verify(mockFilters3).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockFilters3).filtrarPorCompanhia(anyList(), eq("Campsa"));
        verify(mockFilters3).filtrarPorCombustibles(anyList(), eq(List.of("Gasolin 95 E5")));

        // Si se pidió filtrar por estado, se verifica que el filtro fue aplicado
        verify(mockFilters3).filtrarPorEstado(anyList());

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView3, times(2)).showStations(anyList());
        verify(mockView3, times(2)).showLoadCorrect(anyInt());
    }


    @Test
    public void test_UB1A() {
        String provincia = "Cantabria";
        String municipio = "Santander";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1B() {
        String provincia = "Cantabria";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq(null));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1D() {
        String provincia = "Madrid";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1E() {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Tineo"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1G() {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Santander"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1H() {
        String provincia = "-";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioBiodiselAscendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 95 ascendente
        presenter5.ordenarGasolinerasPorPrecio("Biodiesel", "Ascendente");
        assertEquals(presenter5.getGasolineras().get(0), repsol);
        assertEquals(presenter5.getGasolineras().get(1), carrefour);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), petronor);
        assertEquals(presenter5.getGasolineras().get(5), avia);
        assertEquals(presenter5.getGasolineras().get(6), cepsa);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioBiodiselDescendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 95 descendente
        presenter5.ordenarGasolinerasPorPrecio("Biodiesel", "Descendente");
        assertEquals(presenter5.getGasolineras().get(0), cepsa);
        assertEquals(presenter5.getGasolineras().get(1), avia);
        assertEquals(presenter5.getGasolineras().get(2), petronor);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), ballenoil);
        assertEquals(presenter5.getGasolineras().get(5), carrefour);
        assertEquals(presenter5.getGasolineras().get(6), repsol);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioGasoleoAAscendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 98 ascendente con precios 0.0 que se colocan al final
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Ascendente");
        assertEquals(presenter5.getGasolineras().get(0), repsol);
        assertEquals(presenter5.getGasolineras().get(1), carrefour);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), cepsa);
        assertEquals(presenter5.getGasolineras().get(5), petronor);
        assertEquals(presenter5.getGasolineras().get(6), avia);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioGasoleoADescendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 98 descendente con precios 0.0 que se colocan al final
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertEquals(presenter5.getGasolineras().get(0), cepsa);
        assertEquals(presenter5.getGasolineras().get(1), shell);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), carrefour);
        assertEquals(presenter5.getGasolineras().get(4), repsol);
        assertEquals(presenter5.getGasolineras().get(5), petronor);
        assertEquals(presenter5.getGasolineras().get(6), avia);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioListaVacia() {
        // Test ordenar lista vacía
        presenter5.setGasolinerasFiltradas(new ArrayList<>());
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertTrue(presenter5.getGasolinerasFiltradas().isEmpty());
    }

    // Test Coordenadas
    @Test
    public void test_UT1A() {
        Double longitud = -4.03;
        Double latitud = 43.20;
        int distancia = 0;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han anadido gasolineras
        assertEquals(0, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        assertTrue(gasolinerasCapturadas.isEmpty());

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(0, sizeCapturado);
    }

    @Test
    public void test_UT1B() {
        Double longitud = -4.03;
        Double latitud = 43.20;
        int distancia = 100;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(7, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        // quiero que esten todas las gasolineras
        assertTrue(gasolinerasCapturadas.containsAll(presenter.getGasolineras()));

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(7, sizeCapturado);
    }

    @Test
    public void test_UT1C() {
        Double longitud = -4.03;
        Double latitud = 43.20;
        int distancia = 5;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(0, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        assertTrue(gasolinerasCapturadas.isEmpty());

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(0, sizeCapturado);
    }

    @Test
    public void test_UT1D() {
        Double longitud = -4.03;
        Double latitud = 43.20;
        int distancia = 50;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(4, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        /*
            Quiero que esten las gasolineras:
                REPSOL, LAT: 43.40, LONG: -3.86 [0]
                CARREFOUR, LAT: 43.32, LONG: -4.07 [1]
                BALLENOIL, LAT: 43.36, LONG: -3.76 [2]
                SHELL, LAT: 43.17, LONG:-4.6 [3]
             */
        List<Gasolinera> gasolinerasDeseadas = Arrays.asList(presenter.getGasolineras().get(0),
                presenter.getGasolineras().get(1), presenter.getGasolineras().get(2),
                presenter.getGasolineras().get(3));
        assertTrue(gasolinerasCapturadas.containsAll(gasolinerasDeseadas));

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(4, sizeCapturado);
    }

    @Test
    public void test_UT1E() {
        Double longitud = 0.0;
        Double latitud = 0.0;
        int distancia = 50;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(0, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        assertTrue(gasolinerasCapturadas.isEmpty());

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(0, sizeCapturado);

    }

    @Test
    public void test_UT1F() {
        Double longitud = -99.99;
        Double latitud = 89.99;
        int distancia = 100;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(0, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        assertTrue(gasolinerasCapturadas.isEmpty());

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(0, sizeCapturado);

    }

    @Test
    public void test_UT1G() {
        Double longitud = -4.03;
        Double latitud = 43.20;
        int distancia = 29;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(3, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        presenter.getGasolineras().get(0);
            /*
            Quiero que esten las gasolineras:
                REPSOL, LAT: 43.40, LONG: -3.86 [0]
                CARREFOUR, LAT: 43.32, LONG: -4.07 [1]
                BALLENOIL, LAT: 43.36, LONG: -3.76 [2]
             */
        List<Gasolinera> gasolinerasDeseadas = Arrays.asList(presenter.getGasolineras().get(0),
                presenter.getGasolineras().get(1), presenter.getGasolineras().get(2));
        assertTrue(gasolinerasCapturadas.containsAll(gasolinerasDeseadas));

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(3, sizeCapturado);

    }

    @Test
    public void test_UT1H() {
        Double longitud = -99.99;
        Double latitud = 99.99;
        int distancia = 50;

        presenter.searchWithCoordinates(longitud, latitud, distancia);
        // compruebo que no se han aadido gasolineras
        assertEquals(0, presenter.getGasolinerasCoordenadas().size());

        //hago captura del view para comprobar que no le paso gasolineras

        // creo capture para ver el argumento
        ArgumentCaptor<List<Gasolinera>> captor = ArgumentCaptor.forClass(List.class);

        // Verificar que se llamó al método y capturar el argumento
        verify(mockView, times(2)).showStations(captor.capture());

        List<Gasolinera> gasolinerasCapturadas = captor.getValue();
        assertTrue(gasolinerasCapturadas.isEmpty());

        // creo capture para ver arg
        ArgumentCaptor<Integer> captor2 = ArgumentCaptor.forClass(Integer.class);
        // verifico las llamadas
        verify(mockView, times(2)).showLoadCorrect(captor2.capture());

        //verifico el arg capturado
        int sizeCapturado = captor2.getValue();
        assertEquals(0, sizeCapturado);

    }

    @Test
    public void test_UT2A() {
        Double longitudSelec = 10.00;
        Double latitudSelec = 20.00;
        int distancia = 0;
        Double longitudGasolinera = 10.00;
        Double latitudGasolinera = 20.00;

        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertTrue(result);
    }

    @Test
    public void test_UT2B() {
        Double longitudSelec = 10.00;
        Double latitudSelec = 20.00;
        int distancia = 10;
        Double longitudGasolinera = 10.00;
        Double latitudGasolinera = 20.00;
        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertTrue(result);
    }

    @Test
    public void test_UT2C() {
        Double longitudSelec = 10.00;
        Double latitudSelec = 20.00;
        int distancia = 5;
        Double longitudGasolinera = 10.02;
        Double latitudGasolinera = 20.02;
        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertTrue(result);
    }

    @Test
    public void test_UT2D() {
        Double longitudSelec = 10.00;
        Double latitudSelec = 20.00;
        int distancia = 5;
        Double longitudGasolinera = 10.03;
        Double latitudGasolinera = 20.04;
        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertFalse(result);
    }

    @Test
    public void test_UT2E() {
        Double longitudSelec = 10.00;
        Double latitudSelec = 20.00;
        int distancia = 15;
        Double longitudGasolinera = 10.50;
        Double latitudGasolinera = 20.50;
        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertFalse(result);
    }

    @Test
    public void test_UT2F() {
        Double longitudSelec = 200.00;
        Double latitudSelec = -200.00;
        int distancia = 50;
        Double longitudGasolinera = 10.00;
        Double latitudGasolinera = 20.00;
        Boolean result = presenter.estaEnCoordenadas(longitudSelec, latitudSelec, distancia,
                longitudGasolinera, latitudGasolinera);
        assertFalse(result);
    }

    @Test
    public void testInit() {
        when(mockView5.getGasolinerasRepository()).thenReturn(repository5);
        presenter5.init(mockView5);
        assertEquals(presenter5.getView(), mockView5);
    }
}