package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;



import androidx.test.espresso.DataInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.model.Gasolinera;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@HiltAndroidTest
@UninstallModules (RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class InterfazTest {

    @BindValue
    IGasolinerasRepository repository = getTestRepository(
            InstrumentationRegistry.getInstrumentation().getTargetContext(),
            Generador.generarGasolineras()
    ); // Initialize the repository here instead of in @Before

    @Rule(order=0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order=1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    @Before
    public void inicializa() {
        // Perform additional setup if necessary
        hiltRule.inject();  // This injects the @BindValue dependency into Hilt's component
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
        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(hasChildCount(2)));
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
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("Repsol")));
        //Segundo elemento
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("Carrefour")));
        //Tercer elemento
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("Shell")));
        //Cuarto elemento
        DataInteraction elementoLista4 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(3);
        elementoLista4.onChildView(withId(R.id.tvName)).check(matches(withText("Petronor")));


    }



}