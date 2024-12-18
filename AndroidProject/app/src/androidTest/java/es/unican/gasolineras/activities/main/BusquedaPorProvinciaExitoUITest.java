
package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@UninstallModules(RepositoriesModule.class)
@HiltAndroidTest
public class BusquedaPorProvinciaExitoUITest {
    @Rule(order = 0)  // the Hilt rule must execute first
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule(order = 1)
    public ActivityScenarioRule<MainView> activityRule = new ActivityScenarioRule<>(MainView.class);

    final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @BindValue
    final IGasolinerasRepository repository = getTestRepository(context, R.raw.gasolineras_test);

    View decorView;

    @Test
    public void test() {
        // TEST_UI2
        onView(withId(R.id.menuFilterButton)).perform(click());

        Espresso.onView(withId(R.id.spnProvincias)).perform(click());

        Espresso.onData(allOf(is(instanceOf(String.class)), is("CANTABRIA")))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(scrollTo(), click());

        onView(withText("Buscar")).perform(click());

        onView(withId(R.id.lvStations)).check(matches(listSize(4)));
        DataInteraction elementoLista1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        elementoLista1.onChildView(withId(R.id.tvName)).check(matches(withText("REPSOL")));
        DataInteraction elementoLista2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        elementoLista2.onChildView(withId(R.id.tvName)).check(matches(withText("CARREFOUR")));
        DataInteraction elementoLista3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        elementoLista3.onChildView(withId(R.id.tvName)).check(matches(withText("BALLENOIL")));
        DataInteraction elementoLista4 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(3);
        elementoLista4.onChildView(withId(R.id.tvName)).check(matches(withText("SHELL")));

    }
}
