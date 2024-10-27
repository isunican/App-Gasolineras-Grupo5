package es.unican.gasolineras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.unican.gasolineras.common.Filtros;
import es.unican.gasolineras.model.Gasolinera;

public class FiltrosTest {

    private List<Gasolinera> gasolineras;
    private Gasolinera repsol;
    private Gasolinera carrefour;
    private Gasolinera ballenoil;
    private Gasolinera shell;
    private Gasolinera petronor;
    private Gasolinera avia;
    private Gasolinera cepsa;

    @Before
    public void setUp() {
        // Mock de las gasolineras
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa = Mockito.mock(Gasolinera.class);

        // Mock de datos
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

        gasolineras = new ArrayList<>(Arrays.asList(repsol, carrefour, ballenoil, shell, petronor, avia, cepsa));
    }

    // Caso A: Provincia y municipio válidos
    @Test
    public void testUD1A() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Cantabria", "Santander");
        assertEquals(Arrays.asList(repsol, carrefour), resultado);
    }

    // Caso B: Provincia válida y municipio vacío
    @Test
    public void testUD1B() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Cantabria", null);
        assertEquals(Arrays.asList(repsol, carrefour, ballenoil, shell), resultado);
    }

    // Caso C: Provincia vacía y municipio válido
    @Test
    public void testUD1C() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, "Santander");
        assertEquals(Arrays.asList(repsol, carrefour), resultado);
    }

    // Caso D: Provincia válida y municipio vacío sin resultados
    @Test
    public void testUD1D() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Madrid", null);
        assertTrue(resultado.isEmpty());
    }

    // Caso E: Provincia vacía y municipio válido sin resultados
    @Test
    public void testUD1E() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso F: Municipio no perteneciente a la provincia
    @Test
    public void testUD1F() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Asturias", "Santander");
        assertTrue(resultado.isEmpty());
    }

    // Caso G: Provincia y municipio válidos sin resultados
    @Test
    public void testUD1G() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Asturias", "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso H: Provincia y municipio vacíos
    @Test
    public void testUD1H() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, null);
        assertEquals(gasolineras, resultado);
    }

    // Caso I: Lista de gasolineras vacía
    @Test
    public void testUD1I() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(Collections.emptyList(), "Cantabria", "Santander");
        assertNull(resultado);
    }

}
