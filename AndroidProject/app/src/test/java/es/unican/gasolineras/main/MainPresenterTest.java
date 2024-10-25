package es.unican.gasolineras.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
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

import es.unican.gasolineras.R;
import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainPresenter;
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

    private MainPresenter presenter = new MainPresenter();
    @Mock
    private IMainContract.View mockView;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Carga el repositorio de prueba con datos del JSON
        repository = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);
        when(mockView.getGasolinerasRepository()).thenReturn(repository);

        presenter.init(mockView);
    }

    @Test
    public void test_UB1A() throws DataAccessException {
        String provincia = "Cantabria";
        String municipio = "Santander";

        presenter.onSearchStationsWhithFilters(provincia, municipio, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(2);

    }

    @Test
    public void test_UB1B() throws DataAccessException {
        String provincia = "Cantabria";
        String municipio = "";

        presenter.onSearchStationsWhithFilters(provincia, municipio, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(4);
    }

    @Test
    public void test_UB1C() throws DataAccessException {
        String provincia = "-";
        String municipio = "Santander";

        presenter.onSearchStationsWhithFilters(provincia, municipio, false);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(2);
    }

    @Test
    public void test_UB1D() throws DataAccessException {
        String provincia = "Madrid";
        String municipio = "";

        presenter.onSearchStationsWhithFilters(provincia, municipio, false);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(0);
    }

    @Test
    public void test_UB1E() throws DataAccessException {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter.onSearchStationsWhithFilters(provincia, municipio, false);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(0);
    }

    @Test
    public void test_UB1F() throws DataAccessException {
        String provincia = "-";
        String municipio = "Tineo";

        presenter.onSearchStationsWhithFilters(provincia, municipio, false);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(0);
    }

    @Test
    public void test_UB1G() throws DataAccessException {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter.onSearchStationsWhithFilters(provincia, municipio, false);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(1)).showLoadCorrect(0);
    }

    @Test
    public void test_UB1H() throws DataAccessException {
        String provincia = "-";
        String municipio = "";

        presenter.onSearchStationsWhithFilters(provincia, municipio, true);

        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(7);
    }
}
