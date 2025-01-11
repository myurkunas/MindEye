package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "user_profile";
    private static final String USER_NAME_KEY = "user_name";
    private static final String USER_EMAIL_KEY = "user_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);  // Set the layout for this activity

        // Check if a profile already exists
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = preferences.getString(USER_NAME_KEY, null);

        if (userName != null) {
            // If a profile exists, skip to LogActivity (or MainActivity)
            startActivity(new Intent(WelcomeActivity.this, LogActivity.class));
            finish();  // Close WelcomeActivity to prevent user from returning
            return;
        }

        // Find the profile input fields and start button
        TextInputEditText nameEditText = findViewById(R.id.nameEditText);
        TextInputEditText emailEditText = findViewById(R.id.emailEditText);
        Button startButton = findViewById(R.id.start_button);

        // Set up the start button to store profile information and navigate to LogActivity
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                // Basic validation
                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(WelcomeActivity.this, "Please fill in both fields.", Toast.LENGTH_SHORT).show();
                } else {
                    // Save profile information to SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(USER_NAME_KEY, name);
                    editor.putString(USER_EMAIL_KEY, email);
                    editor.apply();

                    // After saving the profile, navigate to LogActivity
                    Intent intent = new Intent(WelcomeActivity.this, LogActivity.class);
                    startActivity(intent);
                    finish();  // Close WelcomeActivity so the user cannot return to it
                }
            }
        });
    }
}
