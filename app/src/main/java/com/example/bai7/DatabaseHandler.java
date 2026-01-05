package com.example.bai7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "StudentDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "TBL_STUDENT";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + "(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Email TEXT, Avatar INTEGER)";
        db.execSQL(sql);

        // Thêm 10 sinh viên mặc định
        for (int i = 1; i <= 10; i++) {
            ContentValues v = new ContentValues();
            v.put("Name", "Sinh viên " + i);
            v.put("Email", "sv"+i+"@gmail.com");
            v.put("Avatar", R.drawable.avatar);
            db.insert(TABLE, null, v);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // ➕ CREATE
    public void addStudent(String name, String email, int avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("Name", name);
        v.put("Email", email);
        v.put("Avatar", avatar);
        db.insert(TABLE, null, v);
        db.close();
    }

    // 📖 READ
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE, null, null, null, null, null, "Id DESC");

        if (c.moveToFirst()) {
            do {
                list.add(new Student(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3)
                ));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    // ✏ UPDATE
    public void updateStudent(int id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("Name", name);
        v.put("Email", email);
        db.update(TABLE, v, "Id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ❌ DELETE
    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "Id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
