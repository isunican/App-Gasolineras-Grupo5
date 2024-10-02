package es.unican.gasolineras.activities.info;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.gasolineras.R;

/**
 * View that shows application general information. Since this view does not have business logic,
 * it can be implemented as an activity directly, without the MVP pattern.
 */
public class InfoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);
    }
}