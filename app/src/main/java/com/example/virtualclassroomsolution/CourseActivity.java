package com.example.virtualclassroomsolution;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Set up the Toolbar with a back button
        Toolbar toolbar = findViewById(R.id.toolbar_course);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All courses");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize RecyclerView and set up adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SubjectAdapter adapter = new SubjectAdapter(new ArrayList<>()); // Create an empty adapter for now
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fetch and update data from the server
        fetchSubjectsAndUpdateUI(requestQueue, adapter);
    }


    private void fetchSubjectsAndUpdateUI(RequestQueue requestQueue, SubjectAdapter adapter) {
        String apiUrl = "http://192.168.0.101:3000/api/notes"; // Replace with your server IP
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Subject> subjects = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String content = jsonObject.getString("content");
                                subjects.add(new Subject(id, title, content));
                            }

                            // Update your RecyclerView with the fetched subjects
                            adapter.setSubjects(subjects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Add the request to the Volley request queue
        requestQueue.add(jsonArrayRequest);
    }
}
