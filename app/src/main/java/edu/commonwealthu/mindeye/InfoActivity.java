package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Handle back button click to return to the previous screen
        MaterialButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> finish());

        // Handle Contact Us button click to open email client
        MaterialButton contactButton = findViewById(R.id.contactButton); // Make sure the button ID matches your layout
        contactButton.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:my19458@commonwealthu.edu")); // Replace with your email address
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Support"); // Optional subject
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I need help with..."); // Optional message body

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            } else {
                Toast.makeText(this, "No email app installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
