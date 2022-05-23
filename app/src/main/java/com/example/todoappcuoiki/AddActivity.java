package com.example.todoappcuoiki;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private Button btnAddActivity;
    private EditText activityName, activityDate, activityTime;
    private DatabaseReference Todo;
    private FirebaseUser userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initUI();
        initListener();
    }

    private void initListener() {

        btnAddActivity.setOnClickListener(view -> {
            addTodo();
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        activityDate.setOnClickListener(view -> pickDate());

        activityTime.setOnClickListener(view -> pickTime());

    }

    private void addTodo() {
        String nameTodoActivity = activityName.getText().toString();
        String dateTodoActivity = activityDate.getText().toString();
        String timeTodoActivity = activityTime.getText().toString();
        String key = Todo.push().getKey();

        Toast.makeText(this, "Added Todo Successfully", Toast.LENGTH_SHORT).show();

        Item item = new Item(nameTodoActivity, dateTodoActivity, timeTodoActivity, key, false);
        assert key != null;
        Todo.child(key).setValue(item);
    }


    private void pickTime() {
        Calendar calendar = Calendar.getInstance();
        int HH = calendar.get(Calendar.HOUR_OF_DAY);
        int MM = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, i, i1) -> {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            calendar.set(0, 0, 0, i, i1);
            activityTime.setText(simpleDateFormat.format(calendar.getTime()));
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
            activityDate.setText(simpleDateFormat.format(calender.getTime()));
        }, yy, mm, dd);
        datePickerDialog.show();
    }

    private void initUI(){
        btnAddActivity = findViewById(R.id.add_activity_detail);
        activityName = findViewById(R.id.activity_name);
        activityDate = findViewById(R.id.activity_date);
        activityTime = findViewById(R.id.activity_time);
        userEmail = FirebaseAuth.getInstance().getCurrentUser();
        assert userEmail != null;
        String userName = userEmail.getEmail();
        assert userName != null;
        String child = userName.replace(".", ",");
        Todo = FirebaseDatabase.getInstance().getReference(child);
    }
}