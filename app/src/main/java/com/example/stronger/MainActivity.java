package com.example.stronger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    TextView textView;
    FirebaseUser user;
    BottomNavigationView nav;
    FloatingActionButton fab;
    RecyclerView recview;
    ArrayList<Model1> datalist;
    FirebaseFirestore db;

    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        nav = findViewById(R.id.bottomNavigationView);
        user = auth.getCurrentUser();
        fab = findViewById(R.id.fab);

        recview = findViewById(R.id.recview);
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();

        datalist = new ArrayList<>();
        adapter = new myadapter(this,  datalist);
        recview.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recview);

        showData();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void showData() {
        db.collection("gyakorlat").whereEqualTo("email", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                datalist.clear();
                for (DocumentSnapshot snapshot : task.getResult()) {
                    Model1 model = new Model1(snapshot.getString("id") ,snapshot.getString("Gyakorlat"), snapshot.getString("Set"), snapshot.getString("Rep"), snapshot.getString("Suly"));
                    datalist.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Hoppá! Valami balul sült el!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}