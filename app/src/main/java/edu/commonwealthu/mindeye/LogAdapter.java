package edu.commonwealthu.mindeye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of log entries.
 * Each item shows the log date and a description of the user's feeling.
 *
 * Features:
 * - Handles item click events to navigate to the details of a selected log.
 * - Allows for dynamic updates to the dataset and refreshes the view accordingly.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private final Context context; // Context for starting activities
    private final List<LogEntry> logs; // List of logs to display

    public LogAdapter(Context context, List<LogEntry> logs) {
        this.context = context;
        this.logs = logs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual log items
        View view = LayoutInflater.from(context).inflate(R.layout.log_entry_details, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogEntry log = logs.get(position); // Get the current log entry

        // Set the log's date in the format "YYYY-MM-DD"
        holder.logDate.setText(String.format("%04d-%02d-%02d", log.getYear(), log.getMonth() + 1, log.getDay()));

        // Set the description of the feeling associated with the log
        holder.logFeeling.setText("Feeling: " + LogEntry.getFeelingDescription(log.getFeeling()));

        // Handle click events to navigate to LogDetailActivity with log details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, LogDetailActivity.class);
            // Pass log details to the LogDetailActivity
            intent.putExtra("LOG_ID", log.getId());
            intent.putExtra("FEELING", log.getFeeling());
            intent.putExtra("JOURNAL_TEXT", log.getJournalText());
            intent.putExtra("HOUR", log.getHour());
            intent.putExtra("MINUTE", log.getMinute());
            intent.putExtra("DAY", log.getDay());
            intent.putExtra("MONTH", log.getMonth());
            intent.putExtra("YEAR", log.getYear());
            context.startActivity(intent); // Start the detail activity
        });
    }

    @Override
    public int getItemCount() {
        return logs.size(); // Return the total number of logs
    }

    /**
     * ViewHolder class for holding references to views in each item layout.
     */
    static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView logDate, logFeeling; // Views to display log details

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            logDate = itemView.findViewById(R.id.log_date); // Date of the log
            logFeeling = itemView.findViewById(R.id.log_feeling); // Feeling description
        }
    }
}
