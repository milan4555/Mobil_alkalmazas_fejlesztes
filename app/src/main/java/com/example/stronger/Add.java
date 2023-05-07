package com.example.stronger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Add extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton fab, save;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    TextInputEditText editTextGyakorlat, editTextRep, editTextSet, editTextSuly;
    String uGyakorlat, uSet, uRep, uSuly, uId, uEmail;
    EditText mGyakorlat, mSet, mRep, mSuly;
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        fab = findViewById(R.id.fab);
        nav = findViewById(R.id.bottomNavigationView);
        save = findViewById(R.id.save);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mGyakorlat = findViewById(R.id.gyakorlat_nev);
        mSet = findViewById(R.id.szett);
        mRep = findViewById(R.id.rep);
        mSuly = findViewById(R.id.suly);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uGyakorlat = bundle.getString("uGyakorlat");
            uId = bundle.getString("uId");
            uSet = bundle.getString("uSet");
            uRep = bundle.getString("uRep");
            uSuly = bundle.getString("uSuly");
            mGyakorlat.setText(uGyakorlat);
            mSet.setText(uSet);
            mRep.setText(uRep);
            mSuly.setText(uSuly);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String gyakorlat = mGyakorlat.getText().toString();
                String set = mSet.getText().toString();
                String rep = mRep.getText().toString();
                String suly = mSuly.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uId;
                    updateToFireStore(id, gyakorlat, set, rep, suly);
                } else {
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, gyakorlat, set, rep, suly);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String gyakorlat = mGyakorlat.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myCh", "My channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myCh")
                .setSmallIcon(R.drawable.gymrat)
                .setContentTitle("Sikeres gyakorlat felvétel az applikációban!")
                .setContentText("Gratulálok a teljesítéséhez, szép munka!");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                startActivity(intent);
                finish();
            }
        });
        nav.getMenu().findItem(R.id.placeholder).setChecked(true);
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
                    Intent intent = new Intent(getApplicationContext(), Stopwatch.class);
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

    private void updateToFireStore(String id, String gyakorlat, String set, String rep, String suly) {
        firestore.collection("gyakorlat").document(id).update("Gyakorlat", gyakorlat, "Set", set, "Rep", rep, "Suly", suly)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Add.this, "Adat módosítva!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Add.this, "Hiba : " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToFireStore(String id, String gyakorlat, String set, String rep, String suly) {
        if (!gyakorlat.isEmpty() && !set.isEmpty() && !rep.isEmpty() && !suly.isEmpty()) {
            HashMap<String, Object> Gyakorlat = new HashMap<>();
            Gyakorlat.put("email", user.getEmail());
            Gyakorlat.put("id", id);
            Gyakorlat.put("Gyakorlat", gyakorlat);
            Gyakorlat.put("Rep", rep);
            Gyakorlat.put("Set", set);
            Gyakorlat.put("Suly", suly);
            firestore.collection("gyakorlat").document(id).set(Gyakorlat)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Add.this, "Sikeres felvétel!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManagerCompat.notify(1, notification);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBack(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}