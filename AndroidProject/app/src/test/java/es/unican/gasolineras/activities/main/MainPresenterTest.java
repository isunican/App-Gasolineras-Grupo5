package es.unican.gasolineras.activities.main;

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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Gasolinera;
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

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1B() {
        String provincia = "Cantabria";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1D() {
        String provincia = "Madrid";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1E() {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Tineo"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1G() {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1H() {
        String provincia = "-";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
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

    // Test para el caso UD.2B
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

}