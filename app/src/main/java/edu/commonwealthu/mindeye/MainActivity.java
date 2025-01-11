package edu.commonwealthu.mindeye;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Install the splash screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set the layout for your main activity
        setContentView(R.layout.activity_main);

        // Set up the TabLayout and ViewPager2 as in your new implementation
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Set up the adapter for the ViewPager
        TabAdapter adapter = new TabAdapter(this);
        viewPager.setAdapter(adapter);

        // Link the TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setIcon(R.drawable.ic_home);
                            tab.setText("Home");
                            break;
                        case 1:
                            tab.setIcon(R.drawable.logs);
                            tab.setText("Logs");
                            break;
                        case 2:
                            tab.setIcon(R.drawable.settings);
                            tab.setText("Settings");
                            break;
                    }
                }).attach();

        // Handle Floating Action Button
        FloatingActionButton fab = findViewById(R.id.floating_action_button);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
        });
    }
}
