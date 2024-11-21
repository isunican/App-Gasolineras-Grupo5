package es.unican.gasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.utils.MockRepositories;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterCoordenadasTest {

    private MainPresenter presenter;
    @Mock
    private IMainContract.View mockView;

    @Mock
    private IFiltros mockFilters;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar el presenter
        repository = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);
        when(mockView.getGasolinerasRepository()).thenReturn(repository);

        presenter = new MainPresenter();
        presenter.init(mockView);
        presenter.setFiltros(mockFilters);
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
}
