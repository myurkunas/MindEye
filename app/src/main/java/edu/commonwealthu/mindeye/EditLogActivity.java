package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.slider.Slider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for editing an existing journal log entry.
 * Allows the user to modify details like feeling score, journal text,
 * and log date and time, then save changes to the database.
 *
 * Features:
 * - Back button to return without saving changes.
 * - Pre-fills fields with data from the selected log.
 * - Saves updated information to the database and redirects to the main screen.
 * @author myurkunas
 */
public class EditLogActivity extends AppCompatActivity {
    private DBHandler dbHandler; // Database handler for performing CRUD operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_log);

        dbHandler = new DBHandler(this);

        // Handle Back Action Button
        MaterialButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish()); // Closes the current activity

        // Retrieve data passed from LogDetailActivity
        int logId = getIntent().getIntExtra("LOG_ID", -1);
        float feeling = getIntent().getFloatExtra("FEELING", 0);
        String journalText = getIntent().getStringExtra("JOURNAL_TEXT");
        int hour = getIntent().getIntExtra("HOUR", 0);
        int minute = getIntent().getIntExtra("MINUTE", 0);
        int day = getIntent().getIntExtra("DAY", 0);
        int month = getIntent().getIntExtra("MONTH", 0);
        int year = getIntent().getIntExtra("YEAR", 0);

        // Initialize UI elements
        Slider feelingSlider = findViewById(R.id.feelingSlider);
        feelingSlider.setValue(feeling); // Set initial slider value based on passed data

        TextInputLayout journalTextBoxLayout = findViewById(R.id.journalTextBox);
        TextInputEditText journalTextBox = (TextInputEditText) journalTextBoxLayout.getEditText();
        if (journalTextBox != null) {
            journalTextBox.setText(journalText); // Set initial journal text
        }

        TimePicker timePicker = findViewById(R.id.journalTime);
        timePicker.setHour(hour); // Set initial hour
        timePicker.setMinute(minute); // Set initial minute

        DatePicker datePicker = findViewById(R.id.journalDate);
        datePicker.updateDate(year, month, day); // Set initial date

        // Save Button functionality
        MaterialButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> {
            // Get updated values from UI
            float newFeeling = feelingSlider.getValue();
            String updatedJournal = journalTextBox != null ? journalTextBox.getText().toString() : "";
            int updatedHour = timePicker.getHour();
            int updatedMinute = timePicker.getMinute();
            int updatedDay = datePicker.getDayOfMonth();
            int updatedMonth = datePicker.getMonth();
            int updatedYear = datePicker.getYear();

            // Update the log in the database
            boolean isUpdated = dbHandler.updateLog(logId, newFeeling, updatedJournal, updatedHour, updatedMinute, updatedMonth, updatedDay, updatedYear);

            if (isUpdated) {
                // Inform the user of success and navigate back to main screen
                Toast.makeText(this, "Log updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditLogActivity.this, MainActivity.class);
                startActivity(intent); // Redirect to MainActivity
            } else {
                // Show failure message
                Toast.makeText(this, "Failed to update log.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
