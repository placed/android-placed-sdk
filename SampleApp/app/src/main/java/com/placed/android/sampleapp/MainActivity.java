package com.placed.android.sampleapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.placed.client.android.persistent.PlacedAgent;

public class MainActivity extends AppCompatActivity implements SampleDialog.SampleDialogListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final String TERMS_AND_PRIVACY_ACCEPTED_KEY = "terms_and_privacy_accepted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Requesting for ACCESS_FINE_LOCATION permission.");
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE_PERMISSION);
        } else {
            Log.d(TAG, "Already have ACCESS_FINE_LOCATION permission. Proceeding to register app with Placed.");
            registerUser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            for (int idx = 0; idx < permissions.length; idx++) {
                if (permissions[idx].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "User granted ACCESS_FINE_LOCATION permission. Proceeding to register app with Placed.");
                        registerUser();
                    } else {
                        Log.d(TAG, "User denied granting ACCESS_FINE_LOCATION permission. Can't proceed to register app with Placed.");
                    }
                    break;
                }
            }
        }
    }

    private void registerUser() {
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(TERMS_AND_PRIVACY_ACCEPTED_KEY, false)) {
            SampleDialog sampleDialog = new SampleDialog(this);
            sampleDialog.show();
            sampleDialog.onActionListener = this;
        } else {
            PlacedAgent.registerUser(MainActivity.this);
        }
    }

    @Override
    public void onActionReceived(SampleDialogAction action) {
        switch (action) {
            case TERMS:
                Intent termsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.placed.com/terms-of-service"));
                startActivity(termsIntent);
            break;

            case PRIVACY:
                Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.placed.com/privacy-policy"));
                startActivity(privacyIntent);
            break;

            case ACCEPT:
                PlacedAgent.registerUser(MainActivity.this);
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean(TERMS_AND_PRIVACY_ACCEPTED_KEY, true)
                        .apply();
            break;

            default:
                break;
        }
    }
}
