package com.example.virtualclassroomsolution;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ActionDialogFragment extends DialogFragment {

    public interface ActionListener {
        void onUpdateClicked();
        void onDeleteClicked();
    }

    private ActionListener listener;

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String courseDescription = args.getString("courseDescription", "");
        String professor = args.getString("professor", "");
        String title = args.getString("title","");
        String message = "Course Title: "+ title  + "\n" + " Course Description: " + courseDescription + "\n" +
                "Professor: " + professor;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Course Details")
                .setMessage(message) // Display the course details
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onUpdateClicked();
                        }
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDeleteClicked();
                        }
                    }
                });

        return builder.create();
    }
}
