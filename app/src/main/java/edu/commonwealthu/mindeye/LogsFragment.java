package edu.commonwealthu.mindeye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.List;

public class LogsFragment extends Fragment {

    private DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logs, container, false);

        // Initialize DBHandler using the context from the fragment
        dbHandler = new DBHandler(requireContext());  // use 'requireContext()' or 'getContext()'

        // Retrieve logs from the database
        List<LogEntry> logs = dbHandler.getAllLogs();

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_logs); // Use 'view.findViewById()' instead of 'findViewById()'
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new LogAdapter(getContext(), logs)); // Make sure to pass the correct context

        // Add Material Divider decoration to RecyclerView
        MaterialDividerItemDecoration dividerItemDecoration = new MaterialDividerItemDecoration(getContext(), MaterialDividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDividerColor(ContextCompat.getColor(requireContext(), R.color.md_theme_onSurfaceVariant)); // Adjust color as needed
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;  // Return the inflated view at the end of the method
    }
}
