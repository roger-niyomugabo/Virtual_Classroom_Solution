package com.example.virtualclassroomsolution;
import com.android.volley.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public void setSubjects(List<Subject> subjects) {
        subjectList = subjects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.subjectName.setText(subject.getTitle());
        holder.subjectContent.setText(subject.getContent());

        LinearLayout subjectLayout = holder.itemView.findViewById(R.id.subject_layout); // Replace with the correct ID

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectLayout.setVisibility(View.GONE); // Hide the individual item layout

                // Open the dialog fragment
                ActionDialogFragment dialogFragment = new ActionDialogFragment();
                dialogFragment.setActionListener(new ActionDialogFragment.ActionListener() {
                    @Override
                    public void onUpdateClicked() {
                        // Open the UpdateFragment and pass the subject ID
                        UpdateFragment updateFragment = new UpdateFragment();
                        Bundle args = new Bundle();
                        args.putInt("subjectId", subject.getId());
                        updateFragment.setArguments(args);

                        // Replace the current fragment with the UpdateFragment
                        FragmentManager fragmentManager = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, updateFragment)
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onDeleteClicked() {
                        deleteSubject(subject.getId(), holder);

                        // Remove the subject from the list and update the adapter
                        subjectList.remove(subject);
                        notifyDataSetChanged();
                    }
                });
                dialogFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "action_dialog");
            }
        });
    }

    private void deleteSubject(int subjectId, SubjectViewHolder holder) {
        // Construct the delete URL based on your API endpoint
        String apiUrl = "http://192.168.0.101:3000/api/note/" + subjectId; // Replace with your actual API URL

        // Create a StringRequest for the delete request
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Delete successful
                        Log.d("DELETE", "Subject deleted successfully");

                        // Remove the subject from the list and update the adapter
                        int position = holder.getAdapterPosition();
                        subjectList.remove(position);
                        notifyItemRemoved(position);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("DELETE", "Subject deletion error: " + error.toString() + apiUrl);
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(holder.itemView.getContext()).add(deleteRequest);
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView subjectContent;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);

            subjectContent = itemView.findViewById(R.id.subject_content);
        }
    }
}
