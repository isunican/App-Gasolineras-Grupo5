package es.unican.gasolineras;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.regex.Pattern.matches;


import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class InterfazTest {


    @Test
    public void testGasolinerasAbiertas_A1() {
        // Abre el diálogo de filtros
        onView(withId(R.id.menuFilterButton)).perform(click());

        // Selecciona la opción de gasolineras abiertas
        onView(withId(R.id.cbAbierto)).perform(click());

        // Haz click en el botón "Buscar"
        onView(withText("Buscar")).perform(click());

        // Verifica si aparecen las gasolineras "Repsol" y "Carrefour"
        onView(withText("Repsol")).check(matches();
        onView(withText("Carrefour")).check(matches();
    }



    @Test
    public void testTodasLasGasolineras_A2() {
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


    @Test
    public void testNingunaGasolineras_A3() {
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
