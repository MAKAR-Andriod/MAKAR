package com.example.makar.data;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.example.makar.databinding.CustomToolbarBinding;

public class ActivityUtil {
    public static void setHideKeyboard(View rootView) {
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View view = v.getRootView().findFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    public static void setToolbar(CustomToolbarBinding binding, String title) {
        binding.toolbarText.setText(title);
        binding.toolbarImage.setVisibility(View.GONE);
        binding.toolbarButton.setVisibility(View.GONE);
    }

    public static void mainSetToolbar(CustomToolbarBinding binding) {
        binding.toolbarText.setVisibility(View.GONE);
    }
}