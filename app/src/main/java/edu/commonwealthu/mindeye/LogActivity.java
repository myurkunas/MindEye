package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

/**
 * The LogActivity class handles the user interface for creating and logging journal entries.
 *
 * Features:
 * - Allows users to input their feelings using a slider.
 * - Users can write a journal entry and specify the date and time.
 * - Saves the journal entry to the SQLite database via DBHandler.
 * - Prints debug information to the Logcat for testing.
 * - Redirects the user back to the main activity after saving the log.
 * @author myurkunas
 */
public class LogActivity extends AppCompatActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // Initialize the database handler
        dbHandler = new DBHandler(LogActivity.this);

        // Initialize UI components
        MaterialButton backButton = findViewById(R.id.back_button);
        Slider slider = findViewById(R.id.feelingSlider);
        TextInputLayout journal = findViewById(R.id.journalTextBox);
        TimePicker time = findViewById(R.id.journalTime);
        DatePicker date = findViewById(R.id.journalDate);
        MaterialButton logButton = findViewById(R.id.logButton);

        // Handle back button click to return to the previous screen
        backButton.setOnClickListener(view -> finish());

        // Handle log button click to save journal entry
        logButton.setOnClickListener(view -> {
            // Retrieve values from UI components
            float sliderValue = slider.getValue();
            String journalText = journal.getEditText() != null
                    ? journal.getEditText().getText().toString() : "";
            int hour = time.getHour();
            int minute = time.getMinute();
            int month = date.getMonth();
            int day = date.getDayOfMonth();
            int year = date.getYear();

            // Save the log entry to the database
            dbHandler.newLog(sliderValue, journalText, hour, minute, month, day, year);

            Toast.makeText(LogActivity.this, "Logged", Toast.LENGTH_SHORT).show();

            // Navigate back to the MainActivity
            Intent intent = new Intent(LogActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
