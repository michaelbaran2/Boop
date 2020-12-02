package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String LOG = "log";
    static final String FIRST_PLACE = "first place";
    static final String SECOND_PLACE = "second place";
    static final String THIRD_PLACE = "third place";
    private static final String LOGIN_ERROR_DIFF_INPUT = "The input fields should be identical.";
    private static final String LOGIN_ERROR_EMPTY = "The input fields may not be empty.";
    private static final String EMPTY_STRING = "";
    static String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // checks if there is a permission for gps
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // if not, ask for it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        } else { // start the gps service
            Intent serviceIntent = new Intent(this, GpsService.class);
            this.startService(serviceIntent);
        }
        // only if received permission start gps service
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Intent serviceIntent = new Intent(this, GpsService.class);
            this.startService(serviceIntent);
        }
    }

    public void checkLogin(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        EditText editText = findViewById(R.id.editText);
        String text = editText.getText().toString();
        intent.putExtra(NAME, text);
        EditText editText2 = findViewById(R.id.editText2);
        String text2 = editText2.getText().toString();
        if (text.equals(text2)) {
            if (text.equals(EMPTY_STRING)) {
                Toast toast = Toast.makeText(getApplicationContext(), LOGIN_ERROR_EMPTY, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                startActivity(intent);
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), LOGIN_ERROR_DIFF_INPUT, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
