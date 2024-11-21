package es.unican.gasolineras.activities.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.IFiltros;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.ICallBack;
import es.unican.gasolineras.repository.IGasolinerasRepository;
import es.unican.gasolineras.utils.MockRepositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;


@RunWith(RobolectricTestRunner.class)
public class MainPresenterTest {

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
    private MainPresenter presenter4 = new MainPresenter();
    @Mock
    private IMainContract.View mockView4;
    @Mock
    private IFiltros mockFilters4;

    private Context context = ApplicationProvider.getApplicationContext();
    private IGasolinerasRepository repository4;

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
    private MainPresenter presenter5 = new MainPresenter();
    private IMainContract.View mockView5 = Mockito.mock(IMainContract.View.class);
    private IGasolinerasRepository repository5 = Mockito.mock(IGasolinerasRepository.class);
    private Gasolinera repsol, carrefour, ballenoil, shell, petronor, avia, cepsa;
    private List<Gasolinera> gasolineras5;
    // mockea la main view
    private IMainContract.View view5 = Mockito.mock(IMainContract.View.class);

    @Before
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        // Carga el repositorio de prueba con datos del JSON
        repository4 = MockRepositories.getTestRepository(context, R.raw.gasolineras_test);

        when(mockView4.getGasolinerasRepository()).thenReturn(repository4);

        presenter4.init(mockView4);
        presenter4.setFiltros(mockFilters4);

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
        presenter5.setView(view5);
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa = Mockito.mock(Gasolinera.class);

        gasolineras5 = new ArrayList<>(Arrays.asList(repsol, carrefour, ballenoil, shell, petronor, avia, cepsa));

        when(repsol.getBiodiesel()).thenReturn(1.5);
        when(repsol.getGasoleoA()).thenReturn(1.5);
        when(repsol.getGasolina95E5()).thenReturn(1.5);
        when(repsol.getGasolina98E5()).thenReturn(1.5);

        when(carrefour.getBiodiesel()).thenReturn(1.6);
        when(carrefour.getGasoleoA()).thenReturn(1.6);
        when(carrefour.getGasolina95E5()).thenReturn(1.6);
        when(carrefour.getGasolina98E5()).thenReturn(1.6);

        when(ballenoil.getBiodiesel()).thenReturn(1.7);
        when(ballenoil.getGasoleoA()).thenReturn(1.7);
        when(ballenoil.getGasolina95E5()).thenReturn(1.7);
        when(ballenoil.getGasolina98E5()).thenReturn(1.7);

        when(shell.getBiodiesel()).thenReturn(1.8);
        when(shell.getGasoleoA()).thenReturn(1.8);
        when(shell.getGasolina95E5()).thenReturn(1.8);
        when(shell.getGasolina98E5()).thenReturn(1.8);

        when(petronor.getBiodiesel()).thenReturn(1.9);
        when(petronor.getGasoleoA()).thenReturn(0.0);
        when(petronor.getGasolina95E5()).thenReturn(1.9);
        when(petronor.getGasolina98E5()).thenReturn(1.9);

        when(avia.getBiodiesel()).thenReturn(2.0);
        when(avia.getGasoleoA()).thenReturn(0.0);
        when(avia.getGasolina95E5()).thenReturn(2.0);
        when(avia.getGasolina98E5()).thenReturn(2.0);

        when(cepsa.getBiodiesel()).thenReturn(2.1);
        when(cepsa.getGasoleoA()).thenReturn(2.1);
        when(cepsa.getGasolina95E5()).thenReturn(2.1);
        when(cepsa.getGasolina98E5()).thenReturn(2.1);

        presenter5.setGasolineras(gasolineras5);
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


    @Test
    public void test_UB1A() {
        String provincia = "Cantabria";
        String municipio = "Santander";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq("Santander"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1B() {
        String provincia = "Cantabria";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Cantabria"), eq(null));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1D() {
        String provincia = "Madrid";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Madrid"), eq(null));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1E() {
        String provincia = "Asturias";
        String municipio = "Tineo";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Tineo"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1G() {
        String provincia = "Asturias";
        String municipio = "Santander";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, false);

        verify(mockFilters4).filtrarPorProvinciaYMunicipio(anyList(), eq("Asturias"), eq("Santander"));
        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void test_UB1H() {
        String provincia = "-";
        String municipio = "";

        presenter4.onSearchStationsWithFilters(provincia, municipio, null, null, true);

        verify(mockView4, times(2)).showStations(anyList());
        verify(mockView4, times(2)).showLoadCorrect(anyInt());
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioBiodiselAscendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 95 ascendente
        presenter5.ordenarGasolinerasPorPrecio("Biodiesel", "Ascendente");
        assertEquals(presenter5.getGasolineras().get(0), repsol);
        assertEquals(presenter5.getGasolineras().get(1), carrefour);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), petronor);
        assertEquals(presenter5.getGasolineras().get(5), avia);
        assertEquals(presenter5.getGasolineras().get(6), cepsa);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioBiodiselDescendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 95 descendente
        presenter5.ordenarGasolinerasPorPrecio("Biodiesel", "Descendente");
        assertEquals(presenter5.getGasolineras().get(0), cepsa);
        assertEquals(presenter5.getGasolineras().get(1), avia);
        assertEquals(presenter5.getGasolineras().get(2), petronor);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), ballenoil);
        assertEquals(presenter5.getGasolineras().get(5), carrefour);
        assertEquals(presenter5.getGasolineras().get(6), repsol);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioGasoleoAAscendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 98 ascendente con precios 0.0 que se colocan al final
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Ascendente");
        assertEquals(presenter5.getGasolineras().get(0), repsol);
        assertEquals(presenter5.getGasolineras().get(1), carrefour);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), shell);
        assertEquals(presenter5.getGasolineras().get(4), cepsa);
        assertEquals(presenter5.getGasolineras().get(5), petronor);
        assertEquals(presenter5.getGasolineras().get(6), avia);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioGasoleoADescendente() {
        presenter5.setGasolinerasFiltradas(gasolineras5);
        // Test ordenar por precio de gasolina 98 descendente con precios 0.0 que se colocan al final
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertEquals(presenter5.getGasolineras().get(0), cepsa);
        assertEquals(presenter5.getGasolineras().get(1), shell);
        assertEquals(presenter5.getGasolineras().get(2), ballenoil);
        assertEquals(presenter5.getGasolineras().get(3), carrefour);
        assertEquals(presenter5.getGasolineras().get(4), repsol);
        assertEquals(presenter5.getGasolineras().get(5), petronor);
        assertEquals(presenter5.getGasolineras().get(6), avia);
    }

    @Test
    public void testOrdenarGasolinerasPorPrecioListaVacia() {
        // Test ordenar lista vacía
        presenter5.setGasolinerasFiltradas(new ArrayList<>());
        presenter5.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertTrue(presenter5.getGasolinerasFiltradas().isEmpty());
    }

    @Test
    public void testInit() {
        when(mockView5.getGasolinerasRepository()).thenReturn(repository5);
        presenter5.init(mockView5);
        assertEquals(presenter5.getView(), mockView5);
    }
}
