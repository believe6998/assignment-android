package com.example.assignment_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.assignment_android.db.DBHelper;

public class ListUserActivity extends AppCompatActivity {
    private DBHelper db;
    private Cursor c;
    private SimpleCursorAdapter adapter;

    public ListUserActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Danh sách sinh viên");
        setContentView(R.layout.activity_list_user);

        db = new DBHelper(this);
        ListView lvUser = (ListView) findViewById(R.id.lvUser);

        c = db.getAllUsers();

        adapter = new SimpleCursorAdapter(this, R.layout.item_user, c, new String[]{
                DBHelper.ID, DBHelper.ROLLNUMBER, DBHelper.NAME, DBHelper.GENDER
        }, new int[]{R.id.tvId, R.id.tvRollNumber, R.id.tvName, R.id.tvGender}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvUser.setAdapter(adapter);

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                int _id = cursor.getInt(cursor.getColumnIndex(DBHelper.ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME));
                String gender = cursor.getString(cursor.getColumnIndex(DBHelper.GENDER));
                String rollNumber = cursor.getString(cursor.getColumnIndex(DBHelper.ROLLNUMBER));

                Intent intent = new Intent(ListUserActivity.this, UpdateActivity.class);
                intent.putExtra(DBHelper.ID, _id);
                intent.putExtra(DBHelper.NAME, name);
                intent.putExtra(DBHelper.GENDER, gender);
                intent.putExtra(DBHelper.ROLLNUMBER, rollNumber);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        c = db.getAllUsers();
        adapter.changeCursor(c);
        adapter.notifyDataSetChanged();
        db.close();
    }
}