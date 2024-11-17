package es.unican.gasolineras;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
import es.unican.gasolineras.activities.main.MainView;
import es.unican.gasolineras.injection.RepositoriesModule;
import es.unican.gasolineras.repository.IGasolinerasRepository;

@HiltAndroidTest
@UninstallModules(RepositoriesModule.class)
@RunWith(AndroidJUnit4.class)
public class OrdenarPorPrecioAscUITest {

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
        public void ordenarPorPrecioAsc() {
                Espresso.onView(withId(R.id.menuOrdenButton)).perform(click());
                Espresso.onView(withId(R.id.spnOrden)).perform(click());
                onView(withText(allOf(is("ASCENDENTE"), instanceOf(String.class))))
                        .inRoot(isPlatformPopup())
                        .perform(click());
                Espresso.onView(withId(R.id.spnCombustible)).perform(click());
                onView(withText(allOf(is("Biodiesel"), instanceOf(String.class))))
                        .inRoot(isPlatformPopup())
                        .perform(click());
                Espresso.onView(withText("Ordenar")).perform(click());
        }
}
