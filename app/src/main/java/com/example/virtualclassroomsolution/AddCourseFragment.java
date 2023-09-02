package com.example.virtualclassroomsolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCourseFragment extends Fragment {

    public AddCourseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        // Initialize UI elements
        EditText courseNameEditText = view.findViewById(R.id.editTextCourseName);
        EditText courseDescriptionEditText = view.findViewById(R.id.editTextCourseDescription);
        EditText professorEditText = view.findViewById(R.id.editTexProfessor);
        Button addCourseButton = view.findViewById(R.id.btnAddCourse);

        // Set up button click listener
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from UI elements
                String courseName = courseNameEditText.getText().toString();
                String courseDescription = courseDescriptionEditText.getText().toString();
                String professor = professorEditText.getText().toString();

                // Call the method to add the course using Volley
                addCourse(courseName, courseDescription, professor);
            }
        });

        return view;
    }

    private void addCourse(String courseName, String courseDescription, String professor) {
        // Replace this URL with your API endpoint for adding courses
        String url = "http://172.20.10.9:3000/api/create";

        // Create a JSON object to hold the course details
        JSONObject courseData = new JSONObject();
        try {
            courseData.put("name", courseName);
            courseData.put("description", courseDescription);
            courseData.put("professor", professor);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, courseData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the API response
                        Toast.makeText(getActivity(), "Course added successfully", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Error adding course", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley queue
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
