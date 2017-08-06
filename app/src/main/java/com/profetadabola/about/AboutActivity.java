package com.profetadabola.about;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.BuildConfig;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.profetadabola.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.textview_version)
    TextView textviewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setupVersion();
    }

    private void setupVersion() {
        PackageInfo pInfo = null;
        String version = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        textviewVersion.setText(version);
    }


    @OnClick(R.id.button_call)
    void callPhone(){
        String versionName = BuildConfig.VERSION_NAME;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.putExtra("ersion",versionName);
        callIntent.setData(Uri.parse("tel:11996509610"));

        if (ActivityCompat.checkSelfPermission(AboutActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        startActivity(callIntent);
    }
}
