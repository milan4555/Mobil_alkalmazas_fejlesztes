package com.example.stronger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class Stopwatch extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab;
    private int seconds;
    private boolean running;
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null) {
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasRunning");

        }

        runTimer();

        fab = findViewById(R.id.fab);
        nav = findViewById(R.id.bottomNavigationView);

        nav.getMenu().findItem(R.id.stopwatch).setChecked(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav.getMenu().findItem(R.id.stopwatch).setChecked(false);
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.stopwatch) {
                    Intent intent = new Intent(getApplicationContext(), Stopwatch.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.person) {
                    Intent intent = new Intent(getApplicationContext(), Person.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.search) {
                    Intent intent = new Intent(getApplicationContext(), Search.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    public void onStart(View view) {
        running = true;
    }

    public void onStop(View view) {
        running = false;
    }

    public void onReset(View view) {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void runTimer() {

        TextView timeView = findViewById(R.id.textView);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);

                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}