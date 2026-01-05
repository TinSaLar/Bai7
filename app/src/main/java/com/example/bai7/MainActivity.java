package com.example.bai7;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtEmail;
    Button btnAdd;
    RecyclerView rv;
    DatabaseHandler db;
    StudentAdapter adapter;
    ArrayList<Student> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        rv = findViewById(R.id.rvStudent);

        db = new DatabaseHandler(this);
        rv.setLayoutManager(new LinearLayoutManager(this));

        loadStudents();

        btnAdd.setOnClickListener(v -> {
            String n = edtName.getText().toString();
            String e = edtEmail.getText().toString();
            if (n.isEmpty() || e.isEmpty()) {
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            db.addStudent(n, e, R.drawable.avatar);
            Toast.makeText(this, "Đã thêm sinh viên!", Toast.LENGTH_SHORT).show();
            edtName.setText("");
            edtEmail.setText("");
            loadStudents();
        });
    }

    private void loadStudents() {
        list = db.getAllStudents();
        adapter = new StudentAdapter(list, this);
        rv.setAdapter(adapter);
    }

    // ✏ Dialog sửa
    public void showEditDialog(Student s) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20,20,20,20);

        EditText e1 = new EditText(this);
        e1.setText(s.getName());
        EditText e2 = new EditText(this);
        e2.setText(s.getEmail());

        layout.addView(e1);
        layout.addView(e2);

        new AlertDialog.Builder(this)
                .setTitle("Sửa thông tin sinh viên")
                .setView(layout)
                .setPositiveButton("Lưu", (d, i) -> {
                    db.updateStudent(s.getId(), e1.getText().toString(), e2.getText().toString());
                    Toast.makeText(this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                    loadStudents();
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

    // ❌ Xoá
    public void deleteStudent(int id) {
        db.deleteStudent(id);
        Toast.makeText(this, "Đã xoá sinh viên!", Toast.LENGTH_SHORT).show();
        loadStudents();
    }
}
