package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/**
 * Activity for displaying the details of a log entry.
 *
 * Features:
 * - Shows log details such as ID, feeling, journal text, time, and date.
 * - Allows users to delete the log or navigate to an edit screen.
 * - Supports navigating back to the previous screen.
 * @author myurkunas
 */
public class LogDetailActivity extends AppCompatActivity {
    private DBHandler dbHandler; // Database handler for managing log data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);

        // Initialize the database handler
        dbHandler = new DBHandler(LogDetailActivity.this);

        // Set up the Back button to finish the current activity
        MaterialButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());

        // Retrieve log details passed from the previous activity
        int logId = getIntent().getIntExtra("LOG_ID", -1);
        float feeling = getIntent().getFloatExtra("FEELING", 0);
        String journalText = getIntent().getStringExtra("JOURNAL_TEXT");
        int hour = getIntent().getIntExtra("HOUR", 0);
        int minute = getIntent().getIntExtra("MINUTE", 0);
        int day = getIntent().getIntExtra("DAY", 0);
        int month = getIntent().getIntExtra("MONTH", 0);
        int year = getIntent().getIntExtra("YEAR", 0);

        // Format the time and date for display
        String time = String.format("%02d:%02d", hour, minute);
        String date = String.format("%04d-%02d-%02d", year, month + 1, day);

        // Populate the UI with log details
        ((TextView) findViewById(R.id.log_id)).setText("Log ID: " + logId);
        ((TextView) findViewById(R.id.log_feeling)).setText("Feeling: " + LogEntry.getFeelingDescription(feeling));
        ((TextView) findViewById(R.id.log_time)).setText("Time: " + time);
        ((TextView) findViewById(R.id.log_date)).setText("Date: " + date);
        ((TextView) findViewById(R.id.log_journal_text)).setText("Journal: " + journalText);

        // Handle the Delete button click event
        MaterialButton deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view -> {
            // Delete the log using the database handler
            boolean isDeleted = dbHandler.deleteLog(logId);

            if (isDeleted) {
                Toast.makeText(LogDetailActivity.this, "Log Deleted Successfully", Toast.LENGTH_SHORT).show();
                // Navigate back to the main activity after deletion
                Intent intent = new Intent(LogDetailActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LogDetailActivity.this, "Failed to Delete Log", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle the Edit button click event
        MaterialButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            // Start EditLogActivity, passing the log details
            Intent intent = new Intent(LogDetailActivity.this, EditLogActivity.class);
            intent.putExtra("LOG_ID", logId);
            intent.putExtra("FEELING", feeling);
            intent.putExtra("JOURNAL_TEXT", journalText);
            intent.putExtra("HOUR", hour);
            intent.putExtra("MINUTE", minute);
            intent.putExtra("DAY", day);
            intent.putExtra("MONTH", month);
            intent.putExtra("YEAR", year);
            startActivity(intent);
        });
    }
}
