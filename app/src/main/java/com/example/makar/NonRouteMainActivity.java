package com.example.makar;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.makar.Dialog.SetFavoriteStationDialog;
import com.example.makar.databinding.ActivityNonRouteMainBinding;

public class NonRouteMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNonRouteMainBinding binding = ActivityNonRouteMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //경로 설정 버튼 클릭 리스너
        binding.setRouteBtn.setOnClickListener(view -> {
            startActivity(new Intent(NonRouteMainActivity.this, SetRouteActivity.class));
        });

        setSupportActionBar(binding.toolbarNonRouteMain);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        //자주 가는 역 설정 Dialog
        setFavoriteStation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.menu_toolbar, menu);

        return true;
    }

    //자주 가는 역 설정 다이얼로그
    //해당 함수 언제 실행할지 상의필요
    private void setFavoriteStation(){
        SetFavoriteStationDialog setFavoriteStationDialog = new SetFavoriteStationDialog(this);
        setFavoriteStationDialog.show();
    }
}