package es.unican.gasolineras;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
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

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainPresenter;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.utils.MockRepositories;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter = new MainPresenter();
    @Mock
    private IMainContract.View mockView;
    @Mock
    private IFiltros mockFilters;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Carga el repositorio de prueba con datos del JSON
        repository = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);
        when(mockView.getGasolinerasRepository()).thenReturn(repository);

        presenter.init(mockView);
        presenter.setFiltros(mockFilters);
    }

    @Test
    public void test_UB1A() throws DataAccessException {
        String provincia = "Cantabria";
        String municipio = "Santander";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1B() throws DataAccessException {
        String provincia = "Cantabria";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1D() throws DataAccessException {
        String provincia = "Madrid";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1E() throws DataAccessException {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Tineo"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1G() throws DataAccessException {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Santander"));
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1H() throws DataAccessException {
        String provincia = "-";
        String municipio = "";

        presenter.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }
}
