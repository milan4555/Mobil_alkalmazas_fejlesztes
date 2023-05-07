package com.example.stronger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class Search extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        fab = findViewById(R.id.fab);
        nav = findViewById(R.id.bottomNavigationView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });
        nav.getMenu().findItem(R.id.search).setChecked(true);
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
}