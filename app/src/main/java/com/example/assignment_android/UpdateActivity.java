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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private DBHelper db;
    private int _id;
    private EditText edName;
    private EditText edRollNumber;
    private Spinner edGender2;
    private List<String> genders = new ArrayList<>(Arrays.asList("Male", "Female", "Other"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Cập nhật sinh viên");
        setContentView(R.layout.activity_update);

        db = new DBHelper(this);

        initView();

        Intent intent = getIntent();
        _id = intent.getIntExtra(DBHelper.ID, 0);
        String name = intent.getStringExtra(DBHelper.NAME);
        String gender = intent.getStringExtra(DBHelper.GENDER);
        String phone = intent.getStringExtra(DBHelper.ROLLNUMBER);

        int index = genders.indexOf(gender);
        edGender2.setSelection(index);
        edName.setText(name);
        edRollNumber.setText(phone);

    }

    private void initView() {
        edName = (EditText) findViewById(R.id.edName);
        edRollNumber = (EditText) findViewById(R.id.edRollnumber);

        edGender2 = (Spinner) findViewById(R.id.edGender2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edGender2.setAdapter(adapter);

        Button btUpdate = (Button) findViewById(R.id.btUpdate);
        btUpdate.setOnClickListener(this);
        Button btDelete = (Button) findViewById(R.id.btDelete);
        btDelete.setOnClickListener(this);
    }

    private void onUpdate() {
        String isUpdate = db.updateUser(_id, edName.getText().toString(),
                edGender2.getSelectedItem().toString(), edRollNumber.getText().toString());
        Toast.makeText(this, isUpdate, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void onDelete() {
        Toast.makeText(this, db.deleteUser(_id), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btUpdate:
                onUpdate();
                break;
            case R.id.btDelete:
                onDelete();
                break;
            default:
                break;
        }
    }
}