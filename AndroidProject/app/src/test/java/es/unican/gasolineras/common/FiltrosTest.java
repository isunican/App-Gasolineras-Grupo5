package es.unican.gasolineras.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.unican.gasolineras.model.Gasolinera;

public class FiltrosTest {

    private Filtros filtros;
    private List<Gasolinera> listaGasolineras;
    private Gasolinera repsol;
    private Gasolinera carrefour;
    private Gasolinera ballenoil;
    private Gasolinera shell;
    private Gasolinera petronor;
    private Gasolinera avia;
    private Gasolinera cepsa;

    private List<Gasolinera> listaGasolineras2;
    private Gasolinera repsol1;
    private Gasolinera repsol2;
    private Gasolinera gasofa;
    private Gasolinera sinRotulo;


    @Before
    public void setUp() {
        filtros = new Filtros();

        // Mock de las gasolineras filtrarPorProvinciaYMunicipio
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa = Mockito.mock(Gasolinera.class);
        repsol1 = Mockito.mock(Gasolinera.class);
        repsol2 = Mockito.mock(Gasolinera.class);
        gasofa = Mockito.mock(Gasolinera.class);
        sinRotulo = Mockito.mock(Gasolinera.class);

        // Mock de datos filtrarPorProvinciaYMunicipio
        when(repsol.getProvincia()).thenReturn("Cantabria");
        when(repsol.getMunicipio()).thenReturn("Santander");

        when(carrefour.getProvincia()).thenReturn("Cantabria");
        when(carrefour.getMunicipio()).thenReturn("Santander");

        when(ballenoil.getProvincia()).thenReturn("Cantabria");
        when(ballenoil.getMunicipio()).thenReturn("Guarnizo");

        when(shell.getProvincia()).thenReturn("Cantabria");
        when(shell.getMunicipio()).thenReturn("Maliaño");

        when(petronor.getProvincia()).thenReturn("Asturias");
        when(petronor.getMunicipio()).thenReturn("Gijón");

        when(avia.getProvincia()).thenReturn("Coruña (A)");
        when(avia.getMunicipio()).thenReturn("Carballo");

        when(cepsa.getProvincia()).thenReturn("");
        when(cepsa.getMunicipio()).thenReturn("Ferrol");

        listaGasolineras = new ArrayList<>(Arrays.asList(repsol, carrefour, ballenoil, shell, petronor, avia, cepsa));

        // Mock de las gasolineras filtrarPorCompanhia
        //Mock de los datos
        when(repsol1.getRotulo()).thenReturn("Repsol");
        when(repsol2.getRotulo()).thenReturn("Repsol");
        when(cepsa.getRotulo()).thenReturn("Cepsa");
        when(carrefour.getRotulo()).thenReturn("Carrefour");
        when(gasofa.getRotulo()).thenReturn("Otros");
        when(sinRotulo.getRotulo()).thenReturn(null);

        listaGasolineras2 = new ArrayList<>(Arrays.asList(repsol1, repsol2, cepsa, carrefour, gasofa, sinRotulo));
    }

    //Test filtrarPorProvinciaYMunicipio
    // Caso A: Provincia y municipio válidos
    @Test
    public void testUD1A() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, "Cantabria", "Santander");
        assertEquals(Arrays.asList(repsol, carrefour), resultado);
    }

    // Caso B: Provincia válida y municipio vacío
    @Test
    public void testUD1B() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, "Cantabria", null);
        assertEquals(Arrays.asList(repsol, carrefour, ballenoil, shell), resultado);
    }

    // Caso C: Provincia vacía y municipio válido
    @Test
    public void testUD1C() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, null, "Santander");
        assertEquals(Arrays.asList(repsol, carrefour), resultado);
    }

    // Caso D: Provincia válida y municipio vacío sin resultados
    @Test
    public void testUD1D() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, "Madrid", null);
        assertTrue(resultado.isEmpty());
    }

    // Caso E: Provincia vacía y municipio válido sin resultados
    @Test
    public void testUD1E() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, null, "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso F: Municipio no perteneciente a la provincia
    @Test
    public void testUD1F() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, "Asturias", "Santander");
        assertTrue(resultado.isEmpty());
    }

    // Caso G: Provincia y municipio válidos sin resultados
    @Test
    public void testUD1G() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, "Asturias", "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso H: Provincia y municipio vacíos
    @Test
    public void testUD1H() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(listaGasolineras, null, null);
        assertEquals(listaGasolineras, resultado);
    }

    // Caso I: Lista de gasolineras vacía
    @Test
    public void testUD1I() {
        List<Gasolinera> resultado = filtros.filtrarPorProvinciaYMunicipio(Collections.emptyList(), "Cantabria", "Santander");
        assertEquals(resultado, Collections.emptyList());
    }


    //Test filtrarPorCompanhia
    //Caso A: Compañia conocida
    @Test
    public void testUD2A() {

        List<Gasolinera> resultado = filtros.filtrarPorCompanhia(listaGasolineras2, "REPSOL");
        assertEquals(Arrays.asList(repsol1, repsol2), resultado);

    }

    //CASO B: Compañía vacia
    @Test
    public void testUD2B() {

        List<Gasolinera> resultado = filtros.filtrarPorCompanhia(listaGasolineras2, "");
        assertEquals(Arrays.asList(repsol1, repsol2, cepsa, carrefour, gasofa, sinRotulo), resultado);

    }

    //CASO C: COMPAÑÍA "OTROS"
    @Test
    public void testUD2C() {

        List<Gasolinera> resultado = filtros.filtrarPorCompanhia(listaGasolineras2, "Otros");
        assertEquals(Arrays.asList(gasofa, sinRotulo), resultado);

    }

    //CASO D: LISTA GASOLINERAS VACIA
    @Test
    public void testUD2D() {
        List<Gasolinera> resultado = filtros.filtrarPorCompanhia(Collections.emptyList(), "-");
        assertEquals(Collections.emptyList(), resultado);

    }

    // test filtrarPorCombustibles
    // A. Prueba con solo un tipo de combustible valido
    @Test
    public void testFiltrarPorUnTipoDeCombustible() {
        List<Gasolinera> gasolineras = List.of(
                crearGasolinera(1.35, 0, 0, 0, 0), // Gasolina 95 E5
                crearGasolinera(0, 1.45, 0, 0, 0)  // Gasolina 95 E5 Premium
        );
        List<String> combustibles = List.of("Gasolina 95 E5");

        List<Gasolinera> resultado = filtros.filtrarPorCombustibles(gasolineras, combustibles);

        assertEquals(1, resultado.size());
        assertEquals(1.35, resultado.get(0).getGasolina95E5(), 0.001); // Delta = 0.001
    }

    // B. Prueba con solo mas de un tipo de combustible valido
    @Test
    public void testFiltrarPorMultiplesTiposDeCombustible() {
        List<Gasolinera> gasolineras = List.of(
                crearGasolinera(1.35, 0, 0, 0, 0), // Gasolina 95 E5
                crearGasolinera(0, 0, 1.75, 0, 0), // Gasolina 95 E10
                crearGasolinera(0, 0, 0, 1.65, 0)   // Gasolina 95 E5 Premium
        );
        List<String> combustibles = List.of("Gasolina 95 E5", "Gasolina 95 E10");

        List<Gasolinera> resultado = filtros.filtrarPorCombustibles(gasolineras, combustibles);

        assertEquals(2, resultado.size());
        assertEquals(1.35, resultado.get(0).getGasolina95E5(), 0.001); // Gasolina 95 E5
        assertEquals(1.75, resultado.get(1).getGasolina95E10(), 0.001); // Gasolina 95 E10
    }

    // C. Prueba con solo tipo de combustible vacio
    @Test
    public void testFiltrarPorCombustiblesVacio() {
        List<Gasolinera> gasolineras = List.of(
                crearGasolinera(1.35, 0, 0, 0, 0), // Gasolina 95 E5
                crearGasolinera(0, 0, 1.75, 0, 0), // Gasolina 95 E10
                crearGasolinera(0, 0, 0, 1.65, 0)   // Gasolina 95 E5 Premium
        );
        List<String> combustibles = List.of();  // Lista vacía de combustibles

        List<Gasolinera> resultado = filtros.filtrarPorCombustibles(gasolineras, combustibles);

        assertEquals(3, resultado.size());  // Todas las gasolineras deben ser incluidas
    }

    // D. Prueba con solo una lista de salida vacia
    @Test
    public void testFiltrarPorCombustiblesNoEncontrados() {
        List<Gasolinera> gasolineras = List.of(
                crearGasolinera(0, 0, 0, 0, 0),  // Sin combustibles válidos
                crearGasolinera(0, 0, 0, 0, 0),  // Sin combustibles válidos
                crearGasolinera(0, 0, 0, 0, 0)   // Sin combustibles válidos
        );
        List<String> combustibles = List.of("Gasolina 98 E5");

        List<Gasolinera> resultado = filtros.filtrarPorCombustibles(gasolineras, combustibles);

        assertEquals(0, resultado.size());  // Ninguna gasolinera tiene Gasolina 98 E5
    }


    // Método auxiliar para crear gasolineras
    private Gasolinera crearGasolinera(double gasolina95E5, double gasolina95E5PREM, double gasolina95E10,
                                       double gasolina98E5, double gasolina98E10) {
        Gasolinera gasolinera = new Gasolinera();
        gasolinera.setGasolina95E5(gasolina95E5);
        gasolinera.setGasolina95E5PREM(gasolina95E5PREM);
        gasolinera.setGasolina95E10(gasolina95E10);
        gasolinera.setGasolina98E5(gasolina98E5);
        gasolinera.setGasolina98E10(gasolina98E10);
        return gasolinera;
    }
}

