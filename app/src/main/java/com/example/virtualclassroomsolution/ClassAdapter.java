package com.example.virtualclassroomsolution;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassItem> classList;

    public ClassAdapter(List<ClassItem> classList) {
        this.classList = classList;
    }

    public void setClasses(List<ClassItem> classes) {
        classList = classes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassItem classItem = classList.get(position);

        // Bind class data to views in the ViewHolder
        holder.classNameTextView.setText(classItem.getClassName());
        holder.videoLinkTextView.setText(classItem.getVideoLink());
        // ... Bind other views
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    static class ClassViewHolder extends RecyclerView.ViewHolder {

        private TextView classNameTextView;
        private  TextView videoLinkTextView;
        // ... Declare other views

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            classNameTextView = itemView.findViewById(R.id.classNameTextView);
            videoLinkTextView = itemView.findViewById(R.id.videoLinkTextView);


            // ... Initialize other views
        }
    }


}
