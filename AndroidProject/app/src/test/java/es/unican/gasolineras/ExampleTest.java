package es.unican.gasolineras;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.model.GasolinerasResponse;

@RunWith(RobolectricTestRunner.class)
public class ExampleTest {

    @Test
    public void test() {
        Context context = ApplicationProvider.getApplicationContext();

        GasolinerasResponse gasolinerasResponse = new GasolinerasResponse();
        assertEquals(0, gasolinerasResponse.getGasolinerasCount());
    }
}
