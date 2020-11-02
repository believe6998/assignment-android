package com.example.assignment_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assignment_android.db.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edName;
    private Button btRegister;
    private Button toList;
    private Spinner spinner;
    private DBHelper db;
    private EditText rollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Thêm mới sinh viên");
        setContentView(R.layout.activity_main);
        initView();
        db = new DBHelper(this);
        db.getReadableDatabase();

    }
    private void initView() {
        edName = (EditText) findViewById(R.id.fullName);
        btRegister = (Button) findViewById(R.id.btRegister);
        toList = (Button) findViewById(R.id.toList);
        rollNumber = (EditText) findViewById(R.id.rollNumber);
        btRegister.setOnClickListener(this);
        toList.setOnClickListener(this);

        String[] genders = {"Nam", "Nữ", "Khác"};
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if(view == btRegister) {
            onRegister();
        }else if(view == toList) {
            goToList();
        }
    }

    private void onRegister() {
        if(edName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your username",Toast.LENGTH_LONG).show();
            return;
        }

        String isAdd = db.addUser(edName.getText().toString(), spinner.getSelectedItem().toString(),
                rollNumber.getText().toString());
        Toast.makeText(this, isAdd, Toast.LENGTH_LONG).show();
        edName.setText("");
        rollNumber.setText("");

        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
        startActivity(intent);
    }

    private void goToList() {
        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
        startActivity(intent);
    }
}