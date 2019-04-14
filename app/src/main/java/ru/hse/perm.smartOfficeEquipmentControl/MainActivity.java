package ru.hse.perm.smartOfficeEquipmentControl;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.devicesupport.Feature;
import com.wikitude.common.permission.PermissionManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {

    private ArchitectView architectView;

    //private final PermissionManager permissionManager = ArchitectView.getPermissionManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.architectView = (ArchitectView)this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(getString(R.string.wikitude_license_key));
        config.setCameraPosition(CameraSettings.CameraPosition.DEFAULT);       // The default camera is the first camera available for the system.
        config.setCameraResolution(CameraSettings.CameraResolution.HD_1280x720);   // The default resolution is 640x480.
        config.setCameraFocusMode(CameraSettings.CameraFocusMode.CONTINUOUS);     // The default focus mode is continuous focusing.
        config.setCamera2Enabled(true);        // The camera2 api is disabled by default (old camera api is used).
        this.architectView.onCreate(config);
    }
    //SD_640x480 | HD_1280x720
    //CameraPosition.BACK

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();

        try {
            architectView.load("file:///android_asset/demo/index.html");
            //this.architectView.load( "https://devredowl.ru/tparser/ar/example.html" );
        } catch (IOException e) {
            Toast.makeText(this, "Could not load AR experience.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        architectView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Очистка кэша и памяти
        architectView.clearCache();
        architectView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        architectView.onPause();
    }

}
