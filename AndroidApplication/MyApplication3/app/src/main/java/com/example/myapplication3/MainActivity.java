package com.example.myapplication3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Switches to Joystick controller screen when pressed
        findViewById(R.id.button_flight_controller).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FlightControllerScreen.class))
        );
        //Switches to Block Coding flight screen when pressed
        findViewById(R.id.button_block_coding).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, BlockCoding.class))
        );
        //Goes to our Github website for instructions
        findViewById(R.id.button_instructions).setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Team5CU/Build-A-Drone")));
        });
    }
}