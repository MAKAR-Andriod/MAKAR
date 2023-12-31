package com.example.makar.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.makar.onboarding.fragment.PageFragment1;
import com.example.makar.onboarding.fragment.PageFragment2;
import com.example.makar.onboarding.fragment.PageFragment3;

public class OnboardingViewPager extends FragmentStateAdapter {

    public OnboardingViewPager(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PageFragment1();
            case 1:
                return new PageFragment2();
            case 2:
                return new PageFragment3();
            default:
                return new PageFragment1(); // 기본적으로 첫 번째 프래그먼트 반환
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}