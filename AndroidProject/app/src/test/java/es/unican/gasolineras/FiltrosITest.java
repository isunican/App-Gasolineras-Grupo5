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

public class FiltrosITest {

    private List<Gasolinera> gasolineras;
    private Gasolinera cepsa1;
    private Gasolinera repsol;
    private Gasolinera carrefour;
    private Gasolinera ballenoil;
    private Gasolinera shell;
    private Gasolinera petronor;
    private Gasolinera avia;
    private Gasolinera cepsa2;

    @Before
    public void setUp() {
        // Mock de las gasolineras
        cepsa1 = Mockito.mock(Gasolinera.class);
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa2 = Mockito.mock(Gasolinera.class);

        // Mock de datos
        when(cepsa1.getProvincia()).thenReturn("Cantabria");
        when(cepsa1.getMunicipio()).thenReturn("Santander");

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

        when(cepsa2.getProvincia()).thenReturn("Coruña (A)");
        when(cepsa2.getMunicipio()).thenReturn("");

        gasolineras = new ArrayList<>(Arrays.asList(cepsa1, repsol, carrefour, ballenoil, shell, petronor, avia, cepsa2));
    }

    // Caso A: Provincia y municipio válidos
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaYMunicipioValidos() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Cantabria", "Santander");
        assertEquals(Arrays.asList(cepsa1, repsol, carrefour), resultado);
    }

    // Caso B: Provincia válida y municipio vacío
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaValidaMunicipioVacio() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Cantabria", null);
        assertEquals(Arrays.asList(cepsa1, repsol, carrefour, ballenoil, shell), resultado);
    }

    // Caso C: Provincia vacía y municipio válido
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaVaciaMunicipioValido() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, "Santander");
        assertEquals(Arrays.asList(cepsa1, repsol, carrefour), resultado);
    }

    // Caso D: Provincia válida y municipio vacío sin resultados
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaValidaMunicipioVacioSinResultados() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Madrid", null);
        assertTrue(resultado.isEmpty());
    }

    // Caso E: Provincia vacía y municipio válido sin resultados
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaVaciaMunicipioValidoSinResultados() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso F: Municipio no perteneciente a la provincia
    @Test
    public void testFiltrarPorProvinciaYMunicipio_municipioNoPerteneceProvincia() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Asturias", "Santander");
        assertTrue(resultado.isEmpty());
    }

    // Caso G: Provincia y municipio válidos sin resultados
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaYMunicipioValidosSinResultados() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, "Asturias", "Tineo");
        assertTrue(resultado.isEmpty());
    }

    // Caso H: Provincia y municipio vacíos
    @Test
    public void testFiltrarPorProvinciaYMunicipio_provinciaYMunicipioVacios() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(gasolineras, null, null);
        assertEquals(gasolineras, resultado);
    }

    // Caso I: Lista de gasolineras vacía
    @Test
    public void testFiltrarPorProvinciaYMunicipio_listaGasolinerasVacia() {
        List<Gasolinera> resultado = Filtros.filtrarPorProvinciaYMunicipio(Collections.emptyList(), "Cantabria", "Santander");
        assertNull(resultado);
    }
}
