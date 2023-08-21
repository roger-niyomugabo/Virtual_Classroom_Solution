package com.example.virtualclassroomsolution;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateFragment extends Fragment {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button updateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        titleEditText = view.findViewById(R.id.edit_title);
        contentEditText = view.findViewById(R.id.edit_content);
        updateButton = view.findViewById(R.id.btn_update);

        // Get the subject ID from the arguments (passed when opening the fragment)
        int subjectId = getArguments().getInt("subjectId");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleEditText.getText().toString();
                String newContent = contentEditText.getText().toString();

                // Call the updateSubject method to send the update request
                updateSubject(subjectId, newTitle, newContent);

                // Close the fragment
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void updateSubject(int subjectId, String newTitle, String newContent) {
        // Construct the update URL based on your API endpoint
        String apiUrl = "http://192.168.0.101:3000/api/note/" + subjectId; // Replace with your actual API URL

        // Create a StringRequest for the update request
        StringRequest updateRequest = new StringRequest(Request.Method.PUT, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Update successful
                        Log.d("UPDATE", "Subject updated successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("UPDATE", "Subject update error: " + error.toString() + apiUrl);
                    }
                }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", subjectId); // Include subjectId in the request body
                    jsonObject.put("title", newTitle);
                    jsonObject.put("content", newContent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json"; // Set the content type to JSON
            }
        };

        // Add the request to the Volley request queue
        Volley.newRequestQueue(requireContext()).add(updateRequest);
    }

}
