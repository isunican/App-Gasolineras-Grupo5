package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.not;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@HiltAndroidTest
@UninstallModules(RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class BusquedaTodasLasGasolinerasUITest {

    View decorView;
    @BindValue
    IGasolinerasRepository repository = getTestRepository(
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
    public void testTodasLasGasolineras_A2() throws InterruptedException {
        //Selecciona filtros y busca
        Espresso.onView(withId(R.id.menuFilterButton)).perform(click());
        Espresso.onView(withText("Buscar")).perform(click());

        //comprueba que aparece el numero de gasolineras correcta
        Espresso.onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(listSize(4)));
        DataInteraction elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("Repsol")));
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("Carrefour")));
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("Shell")));
        DataInteraction elementoLista4 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(3);
        elementoLista4.onChildView(withId(R.id.tvName)).check(matches(withText("Petronor")));

        Espresso.onView(withText("Cargadas 4 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        Thread.sleep(1000);
    }

}
