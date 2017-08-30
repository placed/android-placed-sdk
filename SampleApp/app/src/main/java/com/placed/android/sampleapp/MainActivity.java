package com.placed.android.sampleapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.placed.client.android.persistent.PlacedAgent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_PERMISSION = 1;

    private static final String PREF_KEY_PLACED_DIALOG_SHOWN = "placed_dialog_shown";

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
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREF_KEY_PLACED_DIALOG_SHOWN, false)) {
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putBoolean(PREF_KEY_PLACED_DIALOG_SHOWN, true)
                    .apply();

            new AlertDialog.Builder(this)
                    .setTitle("Asking your users for consent")
                    .setMessage("Implement your own user experience to ask your users to collect Placed data.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PlacedAgent.registerUser(MainActivity.this);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
    }
}
