package es.unican.gasolineras.activities.main;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterCombustiblesTest {

    @Mock
    private IMainContract.View mockView;
    @Mock
    private IGasolinerasRepository mockRepository;
    @Mock
    private IFiltros mockFilters;
    @Mock
    private MainPresenter presenter;

    private List<Gasolinera> gasolineras;


    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        gasolineras = Generador.generarGasolinerasCompanhia();

        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras);
            return null;
        }).when(mockRepository).requestGasolineras(any(ICallBack.class));

        when(mockView.getGasolinerasRepository()).thenReturn(mockRepository);

        presenter = new MainPresenter();
        presenter.init(mockView);
        presenter.setFiltros(mockFilters);
    }

    // Test onSearchStationsWithFilters con combustibles
    // Test para el caso UD.1A
    @Test
    public void testUD1A() throws Exception {
        presenter.onSearchStationsWithFilters("Cantabria", "Santander", "Repsol", List.of("Gasolina 95 E5", "Gasolina 95 E10", "Gasolina 95 E5 Premium"), true);
        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockFilters).filtrarPorCompanhia(anyList(), eq("Repsol"));
        verify(mockFilters).filtrarPorCombustibles(anyList(), eq(List.of("Gasolina 95 E5", "Gasolina 95 E10", "Gasolina 95 E5 Premium")));

        // Si se pidió filtrar por estado, se verifica que el filtro fue aplicado
        verify(mockFilters).filtrarPorEstado(anyList());

        // Se verifica que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1B
    @Test
    public void testUD1B() throws Exception {
        // Ejecutar el método bajo prueba con los parámetros de entrada especificados
        presenter.onSearchStationsWithFilters("Cantabria", "Santander", "", null, false);

        // Verificar que filtrarPorProvinciaYMunicipio se llama con finalProvincia=Cantabria y finalMunicipio=Santander
        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1C
    @Test
    public void testUD1C() throws Exception {
        // Configuración de los parámetros del filtro
        presenter.onSearchStationsWithFilters("Madrid", "", "Shell", List.of("Gasolina 98 E5"), false);

        // Verificar que los métodos de filtro son llamados con los valores esperados
        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null)); // Municipio vacío
        verify(mockFilters).filtrarPorCompanhia(anyList(), eq("Shell"));
        verify(mockFilters).filtrarPorCombustibles(anyList(), eq(List.of("Gasolina 98 E5")));

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }


    // Test para el caso UD.1D
    @Test
    public void testUD1D() throws Exception {
        // Configuración de los parámetros del filtro
        presenter.onSearchStationsWithFilters("Cantabria", "Santander", "Campsa", List.of("Gasolin 95 E5"), true);

        // Verificar que los métodos de filtro son llamados con los valores esperados
        verify(mockFilters).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockFilters).filtrarPorCompanhia(anyList(), eq("Campsa"));
        verify(mockFilters).filtrarPorCombustibles(anyList(), eq(List.of("Gasolin 95 E5")));

        // Si se pidió filtrar por estado, se verifica que el filtro fue aplicado
        verify(mockFilters).filtrarPorEstado(anyList());

        // Verificar que las gasolineras resultantes se muestran correctamente en la vista
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }
}
