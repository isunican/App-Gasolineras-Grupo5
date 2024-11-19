package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
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
public class BusquedaListaVaciaUITest {
    View decorView;
    @BindValue
    IGasolinerasRepository repository = getTestRepository(
            Generador.generarGasolinerasCerradas()
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
    public void testNingunaGasolineras_A3()   {

        //Selecciona filtros y busca
        onView(withId(R.id.menuFilterButton)).perform(click());
        onView(withId(R.id.cbAbierto)).perform(click());

        onView(withText("Buscar")).perform(click());

        // Verifica que no haya gasolineras en la lista
        onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(listSize(0)));

    }


}