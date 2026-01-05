package com.example.bai7;

import android.app.AlertDialog;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentVH> {

    private List<Student> students;
    private MainActivity context;

    public StudentAdapter(List<Student> students, MainActivity context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentVH holder, int i) {
        Student s = students.get(i);
        holder.tvName.setText(s.getName());
        holder.tvEmail.setText(s.getEmail());
        holder.imgAvatar.setImageResource(s.getAvatar());

        holder.itemView.setOnClickListener(v -> {
            CharSequence[] opt = {"Sửa", "Xoá"};
            new AlertDialog.Builder(context)
                    .setTitle("Chọn chức năng")
                    .setItems(opt, (d, index) -> {
                        if (index == 0) context.showEditDialog(s);
                        else context.deleteStudent(s.getId());
                    }).show();
        });
    }

    @Override
    public int getItemCount() { return students.size(); }

    static class StudentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail;
        ImageView imgAvatar;

        public StudentVH(@NonNull View item) {
            super(item);
            tvName = item.findViewById(R.id.tvName);
            tvEmail = item.findViewById(R.id.tvEmail);
            imgAvatar = item.findViewById(R.id.imgAvatar);
        }
    }
}

