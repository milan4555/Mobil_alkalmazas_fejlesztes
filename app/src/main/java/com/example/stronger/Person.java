package com.example.stronger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;

import java.util.ArrayList;

public class Person extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab;
    Button button;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView felhasznalo, email, kor, suly, magassag;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<FelhasznaloModel> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        fab = findViewById(R.id.fab);
        nav = findViewById(R.id.bottomNavigationView);
        button = findViewById(R.id.logout);
        felhasznalo = findViewById(R.id.felhasznalonev);
        email = findViewById(R.id.email);
        kor = findViewById(R.id.eletkor);
        suly = findViewById(R.id.testsuly);
        magassag = findViewById(R.id.magassag);
        datalist = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        db.collection("users").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                datalist.clear();
                for (DocumentSnapshot snapshot : task.getResult()) {
                    FelhasznaloModel model = new FelhasznaloModel(snapshot.getString("felhasznalonev") ,snapshot.getString("email"), snapshot.getString("jelszo"), snapshot.getString("eletkor"), snapshot.getString("suly"), snapshot.getString("magassag"));
                    felhasznalo.setText("Felhasznalonév: " + model.getFelhasznalonev());
                    email.setText(model.getEmail());
                    kor.setText("Életkor: " + model.getEletkor());
                    magassag.setText("Magasság: " + model.getMagassag() + " cm");
                    suly.setText("Testsúly: " + model.getSuly() + " kg");
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });
        nav.getMenu().findItem(R.id.person).setChecked(true);
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
    public void showData() {
        db.collection("users")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        datalist.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            FelhasznaloModel model = new FelhasznaloModel(snapshot.getString("felhasznalonev") ,snapshot.getString("email"), snapshot.getString("jelszo"), snapshot.getString("eletkor"), snapshot.getString("suly"), snapshot.getString("magassag"));
                            datalist.add(model);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Person.this, "Hoppá! Valami balul sült el!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}