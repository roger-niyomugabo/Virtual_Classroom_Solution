package com.example.virtualclassroomsolution;
import com.android.volley.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateSubjectFragment extends Fragment {

        private String subjectId;
        private EditText editTextSubjectName;
        private EditText editTextSubjectContent;
        private Button btnUpdateSubject;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            if (args != null) {
                subjectId = args.getString("subjectId");
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_update_subject, container, false);

            editTextSubjectName = view.findViewById(R.id.editTextCourseDescription);
            editTextSubjectContent = view.findViewById(R.id.editTextCourseDescription);
            btnUpdateSubject = view.findViewById(R.id.btnAddCourse);

            btnUpdateSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateSubject();
                }
            });

            return view;
        }

        private void updateSubject() {
            String updatedSubjectName = editTextSubjectName.getText().toString();
            String updatedSubjectContent = editTextSubjectContent.getText().toString();

            // Create a JSON object containing the updated data
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("id",subjectId);
                requestData.put("name", updatedSubjectName);
                requestData.put("description", updatedSubjectContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Make a Volley request to update the subject
            String url = "http://172.20.10.9:3000/api/note/" + subjectId; // Replace with your actual API URL
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, requestData,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getActivity(), "Course updated successfully", Toast.LENGTH_SHORT).show();
                            // Navigate to CourseActivity
                            Intent intent = new Intent(getActivity(), CourseActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            Toast.makeText(getActivity(), "Error updating course", Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(requireContext()).add(request);
        }
    }

