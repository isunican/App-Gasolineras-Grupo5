package es.unican.gasolineras.main;

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
import es.unican.gasolineras.common.DataAccessException;
import es.unican.gasolineras.model.Combustible;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.Orden;
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
        when(repsol.getProvincia()).thenReturn("Cantabria");
        when(repsol.getMunicipio()).thenReturn("Santander");

        when(carrefour.getBiodiesel()).thenReturn(1.6);
        when(carrefour.getGasoleoA()).thenReturn(1.6);
        when(carrefour.getGasolina95E5()).thenReturn(1.6);
        when(carrefour.getGasolina98E5()).thenReturn(1.6);
        when(carrefour.getProvincia()).thenReturn("Cantabria");
        when(carrefour.getMunicipio()).thenReturn("Santander");

        when(ballenoil.getBiodiesel()).thenReturn(1.7);
        when(ballenoil.getGasoleoA()).thenReturn(1.7);
        when(ballenoil.getGasolina95E5()).thenReturn(1.7);
        when(ballenoil.getGasolina98E5()).thenReturn(1.7);
        when(ballenoil.getProvincia()).thenReturn("Cantabria");
        when(ballenoil.getMunicipio()).thenReturn("Guarnizo");

        when(shell.getBiodiesel()).thenReturn(1.8);
        when(shell.getGasoleoA()).thenReturn(1.8);
        when(shell.getGasolina95E5()).thenReturn(1.8);
        when(shell.getGasolina98E5()).thenReturn(1.8);
        when(shell.getProvincia()).thenReturn("Cantabria");
        when(shell.getMunicipio()).thenReturn("Maliaño");

        when(petronor.getBiodiesel()).thenReturn(1.9);
        when(petronor.getGasoleoA()).thenReturn(1.9);
        when(petronor.getGasolina95E5()).thenReturn(1.9);
        when(petronor.getGasolina98E5()).thenReturn(1.9);
        when(petronor.getProvincia()).thenReturn("Asturias");
        when(petronor.getMunicipio()).thenReturn("Gijón");

        when(avia.getBiodiesel()).thenReturn(2.0);
        when(avia.getGasoleoA()).thenReturn(2.0);
        when(avia.getGasolina95E5()).thenReturn(2.0);
        when(avia.getGasolina98E5()).thenReturn(2.0);
        when(avia.getProvincia()).thenReturn("Coruña (A)");
        when(avia.getMunicipio()).thenReturn("Carballo");

        when(cepsa.getBiodiesel()).thenReturn(2.1);
        when(cepsa.getGasoleoA()).thenReturn(2.1);
        when(cepsa.getGasolina95E5()).thenReturn(2.1);
        when(cepsa.getGasolina98E5()).thenReturn(2.1);
        when(cepsa.getProvincia()).thenReturn("");
        when(cepsa.getMunicipio()).thenReturn("Ferrol");

        presenter.setGasolineras(gasolineras);

    }

    @Test
    public void testOrdenarGasolinerasPorPrecio() {
        presenter.setGasolinerasFiltradas(gasolineras);
        presenter.ordenarGasolinerasPorPrecio(Combustible.BIODIESEL, Orden.ASCENDENTE);
        assertEquals(presenter.getGasolineras().get(0), repsol);
        assertEquals(presenter.getGasolineras().get(1), carrefour);
        assertEquals(presenter.getGasolineras().get(2), ballenoil);
        assertEquals(presenter.getGasolineras().get(3), shell);
        assertEquals(presenter.getGasolineras().get(4), petronor);
        assertEquals(presenter.getGasolineras().get(5), avia);
        assertEquals(presenter.getGasolineras().get(6), cepsa);

        presenter.ordenarGasolinerasPorPrecio(Combustible.BIODIESEL, Orden.DESCENDENTE);
        assertEquals(presenter.getGasolineras().get(0), cepsa);
        assertEquals(presenter.getGasolineras().get(1), avia);
        assertEquals(presenter.getGasolineras().get(2), petronor);
        assertEquals(presenter.getGasolineras().get(3), shell);
        assertEquals(presenter.getGasolineras().get(4), ballenoil);
        assertEquals(presenter.getGasolineras().get(5), carrefour);
        assertEquals(presenter.getGasolineras().get(6), repsol);
    }

    @Test
    public void testInit() throws DataAccessException {
        when(mockView.getGasolinerasRepository()).thenReturn(repository);
        presenter.init(mockView);
        assertEquals(presenter.getView(), mockView);
    }
}
