package com.example.virtualclassroomsolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


    public class UpdateSubjectActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_subject);

            Toolbar toolbar = findViewById(R.id.toolbar_course);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Update course");
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate back to MainActivity
                    Intent intent = new Intent(UpdateSubjectActivity.this, CourseActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // Check if the fragment container exists
            if (findViewById(R.id.update_fragment_container) != null) {
                if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        String subjectId = extras.getString("subjectId");

                        // Create a new UpdateSubjectFragment and pass the subjectId
                        UpdateSubjectFragment fragment = new UpdateSubjectFragment();
                        Bundle args = new Bundle();
                        args.putString("subjectId", subjectId);
                        fragment.setArguments(args);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.update_fragment_container, fragment)
                                .commit();
                    }
                }
            }
        }
    }

