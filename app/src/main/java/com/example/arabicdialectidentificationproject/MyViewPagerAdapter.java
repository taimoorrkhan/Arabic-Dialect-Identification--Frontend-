package com.example.arabicdialectidentificationproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter  extends FragmentStateAdapter {
    String dialect;
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String dialect) {
        super(fragmentActivity);
        this.dialect = dialect;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new PracticeFragment(this.dialect);
        }
        return new LessonsFragment(this.dialect);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}