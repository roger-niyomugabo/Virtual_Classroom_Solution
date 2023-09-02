package com.example.virtualclassroomsolution;
import com.android.volley.Response;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// ClassesActivity.java
public class ClassesActivity extends AppCompatActivity {

    private List<ClassItem> classList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        Toolbar toolbar = findViewById(R.id.toolbar_course);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Classes");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        classAdapter = new ClassAdapter(classList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classAdapter);

        fetchClassesFromAPI(); // Make Volley request to fetch classes
    }

    private void fetchClassesFromAPI() {
        String url = "http://172.20.10.9:3000/api/class"; // Replace with your API URL

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON response and populate classList
                        classList = parseJSONResponse(response);

                        // Update classAdapter with the new data
                        classAdapter.setClasses(classList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
//                        Toast.makeText(ClassesActivity.this, "Error fetching classes", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley queue
        Volley.newRequestQueue(this).add(request);
    }

    private List<ClassItem> parseJSONResponse(JSONArray response) {
        // Parse the JSON response and create a list of Class objects
        List<ClassItem> classes = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject classJson = response.getJSONObject(i);
                String className = classJson.getString("date");
                String videoLink = classJson.getString("intoLink");
                // ... Parse other fields if needed
                classes.add(new ClassItem(className, videoLink));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return classes;
    }
}

