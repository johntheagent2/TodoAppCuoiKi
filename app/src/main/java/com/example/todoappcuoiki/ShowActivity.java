package com.example.todoappcuoiki;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ShowActivity extends AppCompatActivity {

    TextView TittleActivity, TimeActivity, DateActivity;
    Button EditButton, RemoveButton;
    private DatabaseReference Todo;
    private FirebaseUser currentUser;
    Item item;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        bundle();
        initUI();
        showInfo();
        onClickListener();
    }

    private void bundle(){
        bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        item = (Item) bundle.get("Todo Info");

    }

    private void initUI(){
        TittleActivity = findViewById(R.id.title_activity);
        TimeActivity = findViewById(R.id.time_activity);
        DateActivity = findViewById(R.id.date_activity);
        EditButton = findViewById(R.id.edit_data);
        RemoveButton = findViewById(R.id.remove_data);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Todo = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(currentUser.getEmail()).replace(".", ",")+"/"+item.getChildKey());
    }

    private  void showInfo(){
        TittleActivity.setText(item.getTodo());
        TimeActivity.setText(item.getTime());
        DateActivity.setText(item.getDate());
    }

    private void onClickListener(){
        DateActivity.setOnClickListener(view -> pickDate());

        TimeActivity.setOnClickListener(view -> pickTime());

        EditButton.setOnClickListener(view -> {
            String nameTodoActivity = TittleActivity.getText().toString();
            String timeTodoActivity = TimeActivity.getText().toString();
            String dateTodoActivity = DateActivity.getText().toString();
            Item newItem = new Item(nameTodoActivity, dateTodoActivity, timeTodoActivity, item.getChildKey(), item.isCheck());
            Todo.setValue(newItem);
            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        RemoveButton.setOnClickListener(view -> {
            Todo.removeValue();
            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        });
    }

    private void pickTime() {
        Calendar calendar = Calendar.getInstance();
        int HH = calendar.get(Calendar.HOUR_OF_DAY);
        int MM = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, i, i1) -> {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            calendar.set(0, 0, 0, i, i1);
            TimeActivity.setText(simpleDateFormat.format(calendar.getTime()));
        }, HH, MM, true);
        timePickerDialog.show();
    }

    private void pickDate(){
        Calendar calender = Calendar.getInstance();
        int dd = calender.get(Calendar.DATE);
        int mm = calender.get(Calendar.MONTH);
        int yy = calender.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            calender.set(i,i1,i2);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateActivity.setText(simpleDateFormat.format(calender.getTime()));
        }, yy, mm, dd);
        datePickerDialog.show();
    }

}