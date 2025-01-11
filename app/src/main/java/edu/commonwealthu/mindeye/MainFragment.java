package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.enums.LabelsOverlapMode;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainFragment extends Fragment {

    private DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize DBHandler using the context from the activity
        dbHandler = new DBHandler(requireContext());  // Using 'requireContext()' for safety

        // If there are no log entries, start the LogActivity
        if (dbHandler.logCount() == 0) { // Changed to == 0 to check if there are no entries
            Intent intent = new Intent(getContext(), WelcomeActivity.class);
            startActivity(intent);
        }

        MaterialCardView headerCard = view.findViewById(R.id.header_card);
        headerCard.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), InfoActivity.class);
            startActivity(intent);
        });

        // Mood Graph
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);  // Use 'view.findViewById()' instead of 'findViewById()'

        // Create a line chart
        Cartesian cartesian = AnyChart.cartesian();

        // Get the line and provide the chart data
        Line line = cartesian.line(getData(dbHandler));  // Pass the logs here to format them for the graph
        line.stroke("10px #dcb8e6");

        // Format Graph
        String backgroundGraphColor = String.format("#%06X", (0xFFFFFF & ContextCompat.getColor(requireContext(), R.color.md_theme_onTertiaryFixedVariant)));
        cartesian.background().fill(backgroundGraphColor);
        cartesian.xAxis(0).labels().fontColor("#FFFFFF");
        cartesian.yAxis(0).labels().fontColor("#FFFFFF");
        cartesian.xAxis(0).title().fontColor("#FFFFFF");
        cartesian.yAxis(0).title().fontColor("#FFFFFF");
        cartesian.title().fontColor("#FFFFFF");

        // Set up the X-axis
        cartesian.xAxis(0).labels().format("{%X}");
        cartesian.xAxis(0).title("Date");
        cartesian.xAxis(0).labels().enabled(true);  // Ensure labels are enabled
        cartesian.xAxis(0).overlapMode(LabelsOverlapMode.NO_OVERLAP); // Ensure no overlap
        cartesian.xAxis(0).drawFirstLabel(true);  // Draw the first label
        cartesian.xAxis(0).drawLastLabel(true);   // Draw the last label

        // Set up the Y-axis
        cartesian.yScale().minimum(0); // Set minimum value for Y-axis
        cartesian.yScale().maximum(1); // Set maximum value for Y-axis
        cartesian.yAxis(0).title("Mood Level");

        // Set up gridlines (optional, for better visualization)
        cartesian.yGrid(true);
        cartesian.xGrid(true);

        // Chart title
        cartesian.title("Mood over Time");

        // Assign the chart to the AnyChartView
        anyChartView.setChart(cartesian);

        // Get most recent log data from the database
        if (dbHandler.logCount() >= 1) {
            LogEntry mostRecentLog = dbHandler.getLog(dbHandler.maxLogID());

            // Most Recent Log Time Dynamic Text
            int mostRecentLogHourInt = mostRecentLog.getHour();
            int mostRecentLogMinuteInt = mostRecentLog.getMinute();
            int mostRecentDayInt = mostRecentLog.getDay();
            int mostRecentMonthInt = mostRecentLog.getMonth();
            int mostRecentYearInt = mostRecentLog.getYear();

            String amPm = (mostRecentLogHourInt >= 12) ? "PM" : "AM";  // Ternary operator for AM/PM check

            // If the hour is greater than 12, convert it to a 12-hour format
            if (mostRecentLogHourInt > 12) {
                mostRecentLogHourInt -= 12;  // Convert hour to 12-hour format if it's PM
            }

            // Construct the final time string
            String mostRecentTimeText = mostRecentLogHourInt + ":" + String.format("%02d", mostRecentLogMinuteInt) + " " + amPm
                    + ", " + (mostRecentMonthInt + 1) + "/" + mostRecentDayInt + "/" + mostRecentYearInt;

            // Set the text in the 'mostRecentLogTime' TextView
            TextView mostRecentLogHour = view.findViewById(R.id.mostRecentLogTime);  // Use 'view.findViewById()'
            mostRecentLogHour.setText(mostRecentTimeText);

            // Most Recent Feeling Dynamic Text
            TextView mostRecentFeeling = view.findViewById(R.id.mostRecentFeeling);  // Use 'view.findViewById()'
            String mostRecentFeelingText = LogEntry.getFeelingDescription(mostRecentLog.getFeeling());
            mostRecentFeeling.setText(mostRecentFeelingText);

            // Most Recent Journal Dynamic Text
            TextView mostRecentJournal = view.findViewById(R.id.mostRecentJournal);  // Use 'view.findViewById()'
            String mostRecentJournalText = mostRecentLog.getJournalText();

            // Check if the journal text is empty
            if (mostRecentJournalText == null || mostRecentJournalText.isEmpty()) {
                mostRecentJournalText = "no journal for this entry";  // Set default message if empty
                mostRecentJournal.setTypeface(null, android.graphics.Typeface.ITALIC);  // Set text to italics
            } else {
                mostRecentJournalText = "\"" + mostRecentJournalText + "\"";
                mostRecentJournal.setTypeface(null, android.graphics.Typeface.NORMAL);  // Reset to normal font style if not empty
            }

            // Set the final journal text
            mostRecentJournal.setText(mostRecentJournalText);
        }

        // Update dynamic text views with data from the database
        updateStats(view);

        return view;
    }

    private void updateStats(View view) {
        // Get logs this year (count of rows)
        int logsThisYear = dbHandler.logCount();

        // Set the "Logs This Year" value
        TextView logsThisYearTextView = view.findViewById(R.id.logs_this_year_value);  // Find your TextView
        logsThisYearTextView.setText(String.valueOf(logsThisYear));

        // Get words written (word count in journal entries)
        int wordsWritten = 0;
        List<LogEntry> allLogs = dbHandler.getAllLogs();  // Retrieve all logs from the database
        for (LogEntry log : allLogs) {
            if (log.getJournalText() != null) {
                wordsWritten += countWords(log.getJournalText());
            }
        }

        // Set the "Words Written" value
        TextView wordsWrittenTextView = view.findViewById(R.id.words_written_value);  // Find your TextView
        wordsWrittenTextView.setText(String.valueOf(wordsWritten));

        // Get days journaled (unique days)
        Set<String> uniqueDays = new HashSet<>();
        for (LogEntry log : allLogs) {
            String date = log.getDay() + "/" + log.getMonth() + "/" + log.getYear();
            uniqueDays.add(date);  // Add date as a unique identifier
        }

        // Set the "Days Journaled" value
        TextView daysJournaledTextView = view.findViewById(R.id.days_journaled_value);  // Find your TextView
        daysJournaledTextView.setText(String.valueOf(uniqueDays.size()));
    }

    // Method to count the words in a string (simple word count based on spaces)
    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.split("\\s+");
        return words.length;
    }

    private List<DataEntry> getData(DBHandler dbHandler) {
        List<DataEntry> data = new ArrayList<>();
        int maxEntry = dbHandler.maxLogID();

        for (int i = 0; i <= maxEntry; i++) {
            LogEntry log = dbHandler.getLog(i); // Fetch log entry for index i
            if (log != null) { // Check if the log entry exists
                // Format date as YYYY-MM-DD
                String formattedDate = String.format(
                        "%04d-%02d-%02d",
                        log.getYear(),
                        log.getMonth() + 1, // Convert 0-based month to 1-based
                        log.getDay()
                );

                // Add entry with formatted date and feeling
                data.add(new ValueDataEntry(formattedDate, log.getFeeling()));
            }
        }
        return data;
    }
}
