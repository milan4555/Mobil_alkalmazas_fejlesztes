package com.example.stronger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {
    private MainActivity mainActivity;

    ArrayList<Model1> datalist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public myadapter(MainActivity mainActivity, ArrayList<Model1> datalist) {
        this.mainActivity = mainActivity;
        this.datalist = datalist;
    }

    public void updateData(int position) {
        Model1 item = datalist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uGyakorlat", item.getGyakorlat());
        bundle.putString("uSet", item.getSet());
        bundle.putString("uRep", item.getRep());
        bundle.putString("uSuly", item.getSuly());
        Log.v("Item adadtok", String.valueOf(bundle));
        Intent intent  = new Intent(mainActivity, Add.class);
        intent.putExtras(bundle);
        mainActivity.startActivity(intent);
    }

    public void deleteData(int position) {
        Model1 item = datalist.get(position);
        db.collection("gyakorlat").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mainActivity, "Gyakorlat kitörölve!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mainActivity, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent  = new Intent(mainActivity, MainActivity.class);
        mainActivity.startActivity(intent);
    }

    private void notifyRemoved(int position) {
        datalist.remove(position);
        notifyItemRemoved(position);
        mainActivity.showData();
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getGyakorlat());
        holder.t2.setText("Teljesített szettek száma: " + String.valueOf(datalist.get(position).getSet()) + " db");
        holder.t3.setText("Teljesített ismétlések száma: " + String.valueOf(datalist.get(position).getRep()) + " db");
        holder.t4.setText("Legnagyobb súly: " + String.valueOf(datalist.get(position).getSuly()) + " kg");
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView t1, t2, t3, t4;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t2 = itemView.findViewById(R.id.t2);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
        }
    }
}
