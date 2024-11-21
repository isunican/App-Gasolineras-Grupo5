package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
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
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Combustible;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Orden;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.utils.MockRepositories;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    private MainPresenter presenter2;

    @Mock
    private IMainContract.View mockView;
    @Mock
    private IMainContract.View mockView2;

    @Mock
    private IFiltros mockFilters;
    @Mock
    private IFiltros mockFilters2;

    @Mock
    private IGasolinerasRepository mockRepository;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository;
    private List<Gasolinera> gasolineras;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar el primer presenter
        repository = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);
        when(mockView.getGasolinerasRepository()).thenReturn(repository);

        presenter = new MainPresenter();
        presenter.init(mockView);
        presenter.setFiltros(mockFilters);

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
    }

    // Test provincia y municipio
    @Test
    public void test_UB1A() {
        String provincia = "Cantabria";
        String municipio = "Santander";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1B() {
        String provincia = "Cantabria";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1D() {
        String provincia = "Madrid";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1E() {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Tineo"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1G() {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1H() {
        String provincia = "-";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }


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

    //Test onSearchStationsWithFilters Horario
    // Test para el caso UD.1a (Gasolineras abiertas)
    @Test
    public void testOnSearchStationsWithFilters_OpenStations_UD1a() throws Exception {
        presenter.onSearchStationsWithFilters("-", "", null, true);
        verify(mockFilters).filtrarPorEstado(anyList());
        // Verificar que showStations fue llamado con 2 gasolineras abiertas
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1b (Todas las gasolineras)
    @Test
    public void testOnSearchStationsWithFilters_AllStations_UD1b() throws Exception {

        presenter.onSearchStationsWithFilters("-", "", null, false);
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras


    }

    //Test onSearchStationsWithFilters con companhia
    // Test para el caso UD.1a
    @Test
    public void testUD2A() throws Exception {
        presenter2.onSearchStationsWithFilters("Cantabria", "Santander", "Repsol", true);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Repsol"));
        verify(mockFilters2).filtrarPorEstado(anyList());
        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));

        // Verificar que showStations fue llamado con 2 gasolineras
        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.2B
    @Test
    public void testUD2B() {
        // Llamada al método onSearchStationsWithFilters con los parámetros adecuados
        presenter2.onSearchStationsWithFilters("Madrid", "Alcobendas", "", false);

        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq("Alcobendas"));

        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.2C
    @Test
    public void testUD2C() {
        presenter2.onSearchStationsWithFilters("-", "-", "Otros",false);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Otros"));

        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras
    }

    // Test Coordenadas
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
}