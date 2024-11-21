package es.unican.gasolineras.activities.main;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

    @Mock
    private IMainContract.View mockView;
    @Mock
    private IMainContract.View mockView2;

    @Mock
    private IGasolinerasRepository mockRepository;
    @Mock
    private IGasolinerasRepository mockRepository2;

    @Mock
    private IFiltros mockFilters;
    @Mock
    private IFiltros mockFilters2;
    @Mock

    private MainPresenter sut;
    @Mock
    private MainPresenter sut2;

    private List<Gasolinera> gasolineras;
    private List<Gasolinera> gasolineras2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        gasolineras = Generador.generarGasolineras();

        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras);
            return null;
        }).when(mockRepository).requestGasolineras(any(ICallBack.class));

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
    }

    //Test onSearchStationsWithFilters Horario
    // Test para el caso UD.1a (Gasolineras abiertas)
    @Test
    public void testOnSearchStationsWithFilters_OpenStations_UD1a() {


        sut.onSearchStationsWithFilters("-", "", null, null, true);
        verify(mockFilters).filtrarPorEstado(anyList());
        // Verificar que showStations fue llamado con 2 gasolineras abiertas
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.1b (Todas las gasolineras)
    @Test
    public void testOnSearchStationsWithFilters_AllStations_UD1b() {

        sut.onSearchStationsWithFilters("-", "", null, null, false);
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras
    }

    //Test onSearchStationsWithFilters con companhia
    // Test para el caso UD.1a
    @Test
    public void testUD2A() {
        sut2.onSearchStationsWithFilters("Cantabria", "Santander", "Repsol", null, true);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Repsol"));
        verify(mockFilters2).filtrarPorEstado(anyList());
        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));

        // Verificar que showStations fue llamado con 2 gasolineras
        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }


    // Test para el caso UD.1B
    @Test
    public void testUD2B() {
        // Llamada al método onSearchStationsWithFilters con los parámetros adecuados
        sut2.onSearchStationsWithFilters("Madrid", "Alcobendas", "", null,false);
        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq("Alcobendas"));
        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }

    // Test para el caso UD.2C
    @Test
    public void testUD2C() {
        sut2.onSearchStationsWithFilters("-", "-", "Otros", null,false);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Otros"));
        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras

    }

}
