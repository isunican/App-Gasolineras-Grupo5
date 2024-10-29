package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.not;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class BusquedaPorMunicipioExitoUITest {

    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    // I need the context to access resources, such as the json with test gas stations
    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    // Mock repository that provides data from a JSON file instead of downloading it from the internet.
    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_test);

    View decorView;

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void test() throws InterruptedException {
        // TEST_UI3
        onView(withId(R.id.menuFilterButton)).perform(click());

        onView(withId(R.id.etLocalidad)).perform(typeText("Santander"));
        Thread.sleep(1000);
        onView(withText("Buscar")).perform(click());

        onView(withId(R.id.lvStations)).check(matches(listSize(2)));
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("REPSOL")));
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("CARREFOUR")));

        onView(withText("Cargadas 2 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
    }

}
