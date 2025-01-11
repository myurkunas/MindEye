package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import android.net.Uri;


public class SettingsFragment extends Fragment {

    private DBHandler dbHandler;

    private static final String PREFS_NAME = "user_profile";
    private static final String USER_NAME_KEY = "user_name";

    private TextView userNameTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize the DBHandler with the activity context
        dbHandler = new DBHandler(requireContext());

        // Find the userName TextView and the buttons
        userNameTextView = view.findViewById(R.id.userName);
        MaterialButton deleteLogsButton = view.findViewById(R.id.deleteAllLogs);
        MaterialButton resetDataButton = view.findViewById(R.id.resetAllData);

        // Retrieve the stored username from SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        String userName = preferences.getString(USER_NAME_KEY, "No Name Set");

        // Set the retrieved username to the TextView
        userNameTextView.setText(userName);

        // Set up the onClickListener for the "Delete All Logs" button
        deleteLogsButton.setOnClickListener(v -> {
            // Show the confirmation dialog for deleting logs
            showDeleteLogsConfirmationDialog();
        });

        // Set up the onClickListener for the "Reset All Data" button
        resetDataButton.setOnClickListener(v -> {
            // Show the confirmation dialog for resetting all data
            showResetDataConfirmationDialog();
        });

        return view;  // Return the inflated view at the end of the method
    }

    private void showDeleteLogsConfirmationDialog() {
        // Create a confirmation dialog for deleting logs
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Log Deletion")
                .setMessage("Are you sure you want to delete all logs? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If the user confirms, delete all logs
                    deleteAllLogs();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // If the user cancels, dismiss the dialog
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void showResetDataConfirmationDialog() {
        // Create a confirmation dialog for resetting all data
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Data Reset")
                .setMessage("Are you sure you want to delete all logs and user data? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If the user confirms, reset all data
                    resetAllData();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // If the user cancels, dismiss the dialog
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void deleteAllLogs() {
        // Delete all logs from the database
        dbHandler.deleteAllLogs();

        // Show a toast message confirming deletion
        Toast.makeText(getContext(), "All logs deleted", Toast.LENGTH_SHORT).show();
        // Redirect to the WelcomeActivity to allow the user to set up a new profile
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();  // Close the current activity
    }

    private void resetAllData() {
        // Delete all logs
        deleteAllLogs();

        // Clear the stored username and other user data from SharedPreferences
        SharedPreferences preferences = requireContext().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_NAME_KEY);  // Remove the username
        editor.apply();

        // Show a toast message confirming the reset
        Toast.makeText(getContext(), "All logs and user data deleted", Toast.LENGTH_SHORT).show();

        // Redirect to the WelcomeActivity to allow the user to set up a new profile
        Intent intent = new Intent(getContext(), WelcomeActivity.class);
        startActivity(intent);
        requireActivity().finish();  // Close the current activity
    }

}
