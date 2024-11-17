package es.unican.gasolineras;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.unican.gasolineras.activities.main.IMainContract;
import es.unican.gasolineras.activities.main.MainPresenter;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;


public class OrdenarTest {

    private MainPresenter presenter = new MainPresenter();
    private IMainContract.View mockView = Mockito.mock(IMainContract.View.class);
    private IGasolinerasRepository repository = Mockito.mock(IGasolinerasRepository.class);
    private Gasolinera repsol, carrefour, ballenoil, shell, petronor, avia, cepsa;
    private List<Gasolinera> gasolineras;
    // mockea la main view
    private IMainContract.View view = Mockito.mock(IMainContract.View.class);

    @Before
    public void setUp() {
        presenter.setView(view);
        repsol = Mockito.mock(Gasolinera.class);
        carrefour = Mockito.mock(Gasolinera.class);
        ballenoil = Mockito.mock(Gasolinera.class);
        shell = Mockito.mock(Gasolinera.class);
        petronor = Mockito.mock(Gasolinera.class);
        avia = Mockito.mock(Gasolinera.class);
        cepsa = Mockito.mock(Gasolinera.class);

        gasolineras = new ArrayList<>(Arrays.asList(repsol, carrefour, ballenoil, shell, petronor, avia, cepsa));

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

        presenter.setGasolineras(gasolineras);

    }

    @Test
    public void testOrdenarGasolinerasPorPrecio() {
        presenter.setGasolinerasFiltradas(gasolineras);

        // Test ordenar por precio de gasolina 95 ascendente
        presenter.ordenarGasolinerasPorPrecio("Biodiesel", "Ascendente");
        assertEquals(presenter.getGasolineras().get(0), repsol);
        assertEquals(presenter.getGasolineras().get(1), carrefour);
        assertEquals(presenter.getGasolineras().get(2), ballenoil);
        assertEquals(presenter.getGasolineras().get(3), shell);
        assertEquals(presenter.getGasolineras().get(4), petronor);
        assertEquals(presenter.getGasolineras().get(5), avia);
        assertEquals(presenter.getGasolineras().get(6), cepsa);

        // Test ordenar por precio de gasolina 95 descendente
        presenter.ordenarGasolinerasPorPrecio("Biodiesel", "Descendente");
        assertEquals(presenter.getGasolineras().get(0), cepsa);
        assertEquals(presenter.getGasolineras().get(1), avia);
        assertEquals(presenter.getGasolineras().get(2), petronor);
        assertEquals(presenter.getGasolineras().get(3), shell);
        assertEquals(presenter.getGasolineras().get(4), ballenoil);
        assertEquals(presenter.getGasolineras().get(5), carrefour);
        assertEquals(presenter.getGasolineras().get(6), repsol);

        // Test ordenar por precio de gasolina 98 ascendente con precios 0.0 que se colocan al final
        presenter.ordenarGasolinerasPorPrecio("Gasóleo A", "Ascendente");
        assertEquals(presenter.getGasolineras().get(0), repsol);
        assertEquals(presenter.getGasolineras().get(1), carrefour);
        assertEquals(presenter.getGasolineras().get(2), ballenoil);
        assertEquals(presenter.getGasolineras().get(3), shell);
        assertEquals(presenter.getGasolineras().get(4), cepsa);
        assertEquals(presenter.getGasolineras().get(5), avia);
        assertEquals(presenter.getGasolineras().get(6), petronor);

        // Test ordenar por precio de gasolina 98 descendente con precios 0.0 que se colocan al final
        presenter.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertEquals(presenter.getGasolineras().get(0), cepsa);
        assertEquals(presenter.getGasolineras().get(1), shell);
        assertEquals(presenter.getGasolineras().get(2), ballenoil);
        assertEquals(presenter.getGasolineras().get(3), carrefour);
        assertEquals(presenter.getGasolineras().get(4), repsol);
        assertEquals(presenter.getGasolineras().get(5), avia);
        assertEquals(presenter.getGasolineras().get(6), petronor);

        // Test ordenar lista vacía
        presenter.setGasolinerasFiltradas(new ArrayList<>());
        presenter.ordenarGasolinerasPorPrecio("Gasóleo A", "Descendente");
        assertTrue(presenter.getGasolinerasFiltradas().isEmpty());
    }

    @Test
    public void testInit() {
        when(mockView.getGasolinerasRepository()).thenReturn(repository);
        presenter.init(mockView);
        assertEquals(presenter.getView(), mockView);
    }
}
