package es.unican.gasolineras;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
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

public class OnSearchStationsWithFiltersTest {

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
    private List<Gasolinera> gasolineras;
    private List<Gasolinera> gasolineras2;
    private List<Gasolinera> gasolineras3;



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
        sut2.onSearchStationsWithFilters("Asturias", "Gijon", "", null,false);


        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Gijon"));

        verify(mockView2, times(2)).showStations(anyList());
        verify(mockView2, times(2)).showLoadCorrect(anyInt());
    }


    // Test para el caso UD.2C
    @Test
    public void testUD2C() {

        sut2.onSearchStationsWithFilters("Murcia", "-", "Otros", null,false);
        verify(mockFilters2).filtrarPorCompanhia(anyList(), eq("Otros"));

        verify(mockFilters2).filtrarPorProvinciaYMunicipio(anyList(), eq("Murcia"), eq(null));

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



}
