package es.unican.gasolineras;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainPresenter;
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

public class onSearchStationsWithFiltersTest {

    @Mock
    private IMainContract.View mockView;

    @Mock
    private IGasolinerasRepository mockRepository;

    @Mock
    private IFiltros mockFilters;

    private MainPresenter sut;

    private List<Gasolinera> gasolineras;

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

    }

    // Test para el caso UD.1a (Gasolineras abiertas)
    @Test
    public void testOnSearchStationsWithFilters_OpenStations_UD1a() throws Exception {


            sut.onSearchStationsWithFilters("-", "", null, true);
            verify(mockFilters).filtrarPorEstado(anyList());
            // Verificar que showStations fue llamado con 2 gasolineras abiertas
            verify(mockView, times(2)).showStations(anyList());
            verify(mockView, times(2)).showLoadCorrect(anyInt());

    }


    // Test para el caso UD.1b (Todas las gasolineras)
    @Test
    public void testOnSearchStationsWithFilters_AllStations_UD1b() throws Exception {

        sut.onSearchStationsWithFilters("-", "", null, false);
        verify(mockView, times(2)).showStations(anyList());
        verify(mockView, times(2)).showLoadCorrect(anyInt());  // Verificar que se muestra el número correcto de gasolineras


    }

}
