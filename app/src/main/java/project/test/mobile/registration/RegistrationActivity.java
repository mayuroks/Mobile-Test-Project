package project.test.mobile.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import project.test.mobile.BaseActivity;
import project.test.mobile.R;
import project.test.mobile.gallery.GalleryActivity;
import project.test.mobile.utils.TextUtils;

public class RegistrationActivity extends BaseActivity {

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.tietFullName)
    TextInputEditText tietFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Picasso.with(context)
                .load(R.drawable.octocat)
                .resize(250, 250)
                .transform(new CropCircleTransformation())
                .into(ivProfile, new Callback() {
                    @Override
                    public void onSuccess() {
                        ivProfile.setBackground(ContextCompat
                                .getDrawable(context, R.drawable.bg_circle));
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @OnClick(R.id.btnStart)
    public void goToGallery() {
        String fullName = tietFullName.getText().toString().trim();
        if (!TextUtils.isValidString(fullName)) {
            Alerter.create(this)
                    .setTitle("Enter your full name to proceed")
                    .setIcon(R.drawable.ic_frown)
                    .setBackgroundColorRes(R.color.colorError)
                    .show();
        } else {
            startActivity(new Intent(context, GalleryActivity.class));
        }
    }
}
