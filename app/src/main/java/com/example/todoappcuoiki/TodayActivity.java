package com.example.todoappcuoiki;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TodayActivity extends AppCompatActivity {

    private List<Item> itemLst;
    private ItemAdapter itemAdapter;
    private FirebaseUser userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        initUi();
        createRecycleView();
    }

    public void createRecycleView(){
        DatabaseReference todo = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(userEmail.getEmail()).replace(".",","));
        Log.d("something", todo.getKey());
        RecyclerView recyclerview = findViewById(R.id.rvItem);
        itemLst = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, itemLst);
        todo.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    assert item != null;
                    if(item.getDate().equals(getTodayDate())) {
                        itemLst.add(item);
                    }
                }
                itemAdapter.notifyDataSetChanged();
                recyclerview.setAdapter(itemAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initUi() {
        userEmail = FirebaseAuth.getInstance().getCurrentUser();
    }

    private String getTodayDate(){
        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(todayDate);
    }
}