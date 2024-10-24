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
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class onSearchStationsWithFiltersTest {

    @Mock
    private IMainContract.View mockView;

    @Mock
    private IGasolinerasRepository mockRepository;

    private MainPresenter sut;

    private List<Gasolinera> gasolineras;

    @Before
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);

        // Generar las gasolineras de prueba
        gasolineras = Generador.generarGasolineras();

        // Configurar el comportamiento del repositorio mock
        doAnswer(invocation -> {
            ICallBack callBack = invocation.getArgument(0);
            callBack.onSuccess(gasolineras);
            return null;
        }).when(mockRepository).requestGasolineras(any(ICallBack.class));

        // Establecer el mockRepository en la vista
        when(mockView.getGasolinerasRepository()).thenReturn(mockRepository);

        // Crear una instancia del presenter, pasando la vista mock
        sut = new MainPresenter();
    }

    // Test para el caso UD.1a (Gasolineras abiertas)
    @Test
    public void testOnSearchStationsWithFilters_OpenStations_UD1a() throws Exception {
        // Inicializar la vista mock en el presenter
        sut.init(mockView);

        // Simular la búsqueda de gasolineras abiertas (estado = true)
        sut.onSearchStationsWithFilters("-", "", true);

        // Verificar que el filtro se aplica y se llaman los métodos correspondientes
        verify(mockView, times(1)).showStations(Mockito.argThat(stations -> stations.size() == 2));  // 2 gasolineras abiertas
        verify(mockView, times(1)).showLoadCorrect(2);
    }

    // Test para el caso UD.1b (Todas las gasolineras)
    @Test
    public void testOnSearchStationsWithFilters_AllStations_UD1b() throws Exception {

        sut.init(mockView);

        sut.onSearchStationsWithFilters("-", "", false);

        // Verificar que el filtro no se aplica y se llaman los métodos correspondientes
        verify(mockView, times(2)).showStations(Mockito.argThat(stations -> stations.size() == 4));  // 4 gasolineras
        verify(mockView, times(2)).showLoadCorrect(4);  // Verificar que se muestra el número correcto de gasolineras
    }
}
