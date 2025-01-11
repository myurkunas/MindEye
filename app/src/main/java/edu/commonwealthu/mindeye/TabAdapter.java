package edu.commonwealthu.mindeye;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {

    public TabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new LogsFragment();
            case 2:
                return new SettingsFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
