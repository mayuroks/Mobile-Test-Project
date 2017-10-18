package project.test.mobile.registration;

import android.content.Intent;
import android.os.Bundle;

import project.test.mobile.BaseActivity;
import project.test.mobile.R;
import project.test.mobile.gallery.GalleryActivity;

public class RegistrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // FIXME remove this
        startActivity(new Intent(this, GalleryActivity.class));
    }
}
