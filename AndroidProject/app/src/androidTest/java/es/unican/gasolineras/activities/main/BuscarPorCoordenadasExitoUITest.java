package es.unican.gasolineras.activities.main;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static es.unican.gasolineras.utils.Matchers.listSize;
import static es.unican.gasolineras.utils.MockRepositories.getTestRepository;

import android.content.Context;
import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;
import es.unican.gasolineras.R;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@HiltAndroidTest
@UninstallModules(RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class BuscarPorCoordenadasExitoUITest {

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
    public void test_AT1A() {
        Espresso.onView(withId(R.id.menuCoordenadasButton)).perform(click());
        Espresso.onView(withId(R.id.etLatitud)).perform(click());
        onView(withId(R.id.etLatitud)).perform(typeText("43.20"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.etLongitud)).perform(click());
        onView(withId(R.id.etLongitud)).perform(typeText("-4.03"), closeSoftKeyboard());
        onView(withId(R.id.main_slider)).perform(swipeRight());
        Espresso.onView(withText("Buscar")).perform(click());

        // compruebo el display de gasolineras
        Espresso.onView(withId(R.id.lvStations)).check(matches(isDisplayed())).check(matches(listSize(7)));
        DataInteraction gasolinera1 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(0);
        gasolinera1.onChildView(withId(R.id.tvName)).check(matches(withText("REPSOL")));
        DataInteraction gasolinera2 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(1);
        gasolinera2.onChildView(withId(R.id.tvName)).check(matches(withText("CARREFOUR")));
        DataInteraction gasolinera3 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(2);
        gasolinera3.onChildView(withId(R.id.tvName)).check(matches(withText("BALLENOIL")));
        DataInteraction gasolinera4 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(3);
        gasolinera4.onChildView(withId(R.id.tvName)).check(matches(withText("SHELL")));
        DataInteraction gasolinera5 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(4);
        gasolinera5.onChildView(withId(R.id.tvName)).check(matches(withText("PETRONOR")));
        DataInteraction gasolinera6 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(5);
        gasolinera6.onChildView(withId(R.id.tvName)).check(matches(withText("AVIA")));
        DataInteraction gasolinera7 = onData(anything()).inAdapterView(withId(R.id.lvStations)).atPosition(6);
        gasolinera7.onChildView(withId(R.id.tvName)).check(matches(withText("CEPSA")));
    }
}

