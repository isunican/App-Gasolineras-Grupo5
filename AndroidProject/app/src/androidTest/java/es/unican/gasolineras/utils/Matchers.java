package es.unican.gasolineras.utils;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Custom matchers for Espresso tests.
 */
public class Matchers {
    public static Matcher<View> listSize(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                return ((ListView) view).getAdapter().getCount() == size;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("ListView con " + size + " items");
            }
        };
    }
}
