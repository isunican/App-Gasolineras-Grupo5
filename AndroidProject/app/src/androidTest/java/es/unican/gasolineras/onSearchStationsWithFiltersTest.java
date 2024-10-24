package es.unican.gasolineras;

import static org.junit.Assert.assertTrue;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.common.Horario;
import es.unican.gasolineras.common.Tiempo;
import es.unican.gasolineras.repository.IGasolinerasRepository;

public class onSearchStationsWithFiltersTest {

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @BindValue
    IGasolinerasRepository repository;
    @Before
    public void inicializa() {
        repository = getTestRepository(context, Generador.generarGasolineras());
    }


    @Test
    public void testUD1a() {



    }


    @Test
    public void testUD1b() {




    }


}
