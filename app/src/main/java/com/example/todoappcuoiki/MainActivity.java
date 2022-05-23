package com.example.todoappcuoiki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemLst;
    private ItemAdapter itemAdapter;
    private FirebaseUser userEmail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btnAdd, logOutBtn, todayBtn;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        createRecycleView();
        showInfo();
        btnListener();
        refreshApp();
    }

    public void createRecycleView(){
        DatabaseReference todo = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(userEmail.getEmail()).replace(".",","));
        RecyclerView  recyclerview = findViewById(R.id.rvItem);
        itemLst = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, itemLst);
        todo.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    itemLst.add(item);
                }
                itemAdapter.notifyDataSetChanged();
                recyclerview.setAdapter(itemAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void refreshApp(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createRecycleView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void initUi() {
        btnAdd = findViewById(R.id.add_todo);
        logOutBtn = findViewById(R.id.logoutBtn);
        todayBtn = findViewById(R.id.today_btn);
        userName = findViewById(R.id.username);
        userEmail = FirebaseAuth.getInstance().getCurrentUser();
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
    }

    private  void showInfo(){
        if (userEmail == null){
            return;
        }
        userName.setText(userEmail.getEmail());
    }

    public void btnListener(){
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });
        logOutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this ,SignInActivity.class);
            startActivity(intent);
        });
        todayBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TodayActivity.class);
            startActivity(intent);
        });
    }

}

