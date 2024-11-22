package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
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
import es.unican.gasolineras.R;
import es.unican.gasolineras.common.Generador;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

import static org.hamcrest.CoreMatchers.containsString;


@HiltAndroidTest
@UninstallModules(RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class BusquedaExitosaPorCombustibleUITest {

    //Vista
    @BindValue
    IGasolinerasRepository repository = getTestRepository(
            Generador.generarGasolineras()
    );

    @Rule(order=0)
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order=1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    @Before
    public void inicializa() {

        hiltRule.inject();
    }

    @Test
    public void testBusquedaTipoCombustible_A1() {
        //Selecciona filtros y busca
        Espresso.onView(withId(R.id.menuFilterButton)).perform(click());
        Espresso.onView(withId(R.id.ivArrow)).perform(click());
        Espresso.onView(withText("Gasolina 95 E5")).perform(click());
        Espresso.onView(withText("Aceptar")).perform(click());
        Espresso.onView(withText("Buscar")).perform(click());

        //comprueba que aparece el numero de gasolineras correcta
        Espresso.onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(listSize(4)));
        for (int i = 0; i < 4; i++) {
            DataInteraction elementoLista = onData(anything())
                    .inAdapterView(withId(R.id.lvStations))
                    .atPosition(i);

            elementoLista.onChildView(withId(R.id.tvGas1))
                    .check(matches(withText(containsString("Gasolina95E5"))));;
        }
    }
}