package com.example.assignment_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "asm_test";
    public static final int DB_VERSION = 1;

    public static String TABLE_NAME = "student";
    public static String ID = "_id";
    public static String NAME= "name";
    public static String GENDER= "gender";
    public static String ROLLNUMBER = "rollNumber";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+ TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " +
                GENDER + " TEXT, " +
                ROLLNUMBER + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public String addUser(String user, String gender, String phone) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, user);
            contentValues.put(GENDER, gender);
            contentValues.put(ROLLNUMBER, phone);
            long isAdd = db.insert(TABLE_NAME, null, contentValues);
            if(isAdd == -1) {
                return "Thêm mới thất bại";
            }
            db.close();
            return "Thêm mới thành công";
        }catch (Exception e) {
            System.out.println(e);
        }
        return "ok";
    }

    public String updateUser(int id, String user, String gender, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, user);
        contentValues.put(GENDER, gender);
        contentValues.put(ROLLNUMBER, phone);
        int isUpdate = db.update(TABLE_NAME, contentValues, ID + " = ? ", new String[] {id+""});
        if(isUpdate > 0) {
            return "Cập nhật thành công";
        }
        db.close();
        return "Cập nhật thất bại";
    }

    public String deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete(TABLE_NAME, ID+ " = ? ", new String[]{id+""});
        if(isDelete > 0 ) {
            return "Xoá thành công";
        }
        db.close();
        return "Xoá thất bại";
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(sql,null);
        return c;
    }
}
