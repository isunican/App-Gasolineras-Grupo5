package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static java.util.regex.Pattern.matches;


import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;

import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules (RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class InterfazTest {

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    @BindValue
    IGasolinerasRepository repository;
    @Before
    public void inicializa() {
        repository = getTestRepository(context, Generador.generarGasolineras());
    }



    @Test
    public void testGasolinerasAbiertas_A1() {
        // Abre el diálogo de filtros
        onView(withId(R.id.menuFilterButton)).perform(click());

        // Selecciona la opción de gasolineras abiertas
        onView(withId(R.id.cbAbierto)).perform(click());

        // Haz click en el botón "Buscar"
        onView(withText("Buscar")).perform(click());

        // Verifica si aparecen las gasolineras "Repsol" y "Carrefour"
        onView(withId(R.id.lvStations)).check(matches(hasChildCount(2)));
        //Primer elemento
        DataInteraction elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("Repsol")));
        //Segundo elemento
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("Carrefour")));
    }



    @Test
    public void testTodasLasGasolineras_A2() {
        // Abre el diálogo de filtros
        onView(withId(R.id.menuFilterButton)).perform(click());


        // Haz click en el botón "Buscar"
        onView(withText("Buscar")).perform(click());


        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(4)));
        //Primer elemento
        DataInteraction elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("Shell")));
        //Segundo elemento
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("Petronor")));
        //Tercer elemento
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("Repsol")));
        //Cuarto elemento
        DataInteraction elementoLista4 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(3);
        elementoLista4.onChildView(withId(R.id.tvName)).check(matches(withText("Carrefour")));

    }



    @Test
    public void testNingunaGasolineras_A3() {
        // Abre el diálogo de filtros
        onView(withId(R.id.menuFilterButton)).perform(click());


        // Haz click en el botón "Buscar"
        onView(withText("Buscar")).perform(click());


    }


    @Test
    public void testMensajeFaltanDatosGasolineras_A4() {
        // Abre el diálogo de filtros
        onView(withId(R.id.menuFilterButton)).perform(click());
        // Haz click en el botón "Buscar"
        onView(withText("Buscar")).perform(click());

        // Verifica si aparecen las gasolineras "Shell" y "Petronor"
        //onView(withText("Shell")).check(matches(isDisplayed()));
        //onView(withText("Petronor")).check(matches(isDisplayed()));
        //onView(withText("Repsol")).check(matches(isDisplayed()));
        //onView(withText("Carrefour")).check(matches(isDisplayed()));
    }


}
