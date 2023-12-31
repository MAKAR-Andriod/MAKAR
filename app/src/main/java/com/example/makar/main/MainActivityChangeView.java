package com.example.makar.main;

import android.view.View;

import com.example.makar.databinding.ActivityMainBinding;

public class MainActivityChangeView {
    static void changeView(ActivityMainBinding mainBinding, boolean isRouteSet, long leftTime, String source, String destination) {
        if (isRouteSet) {
            //route 설정된 메인화면
            mainBinding.resetRouteBtn.setVisibility(View.VISIBLE);
            mainBinding.mainRouteView.setText(source + "  ->  " + destination); //출발지, 도착지
            mainBinding.mainTitleText.setText("막차까지 " + leftTime + "분 남았어요!"); //막차까지 남은 시간
            mainBinding.mainDestinationText.setText(source); //도착지 이름
            mainBinding.changeRouteBtn.setVisibility(View.VISIBLE);
            mainBinding.setAlarmBtn.setVisibility(View.VISIBLE);
            mainBinding.setRouteBtn.setVisibility(View.GONE);
            mainBinding.favoriteRouteLinearLayout.setVisibility(View.GONE);
            mainBinding.recentRouteLinearLayout.setVisibility(View.GONE);
            mainBinding.favoriteRouteLinearLayout.setVisibility(View.GONE);
            mainBinding.recentRouteLinearLayout.setVisibility(View.GONE);
        } else {
            //route 미설정 화면
            mainBinding.resetRouteBtn.setVisibility(View.GONE);
            mainBinding.mainRouteView.setText("출발역  ->  도착역");
            mainBinding.mainTitleText.setText("경로를 설정해주세요");
            mainBinding.mainDestinationText.setText("MAKAR");
            mainBinding.changeRouteBtn.setVisibility(View.GONE);
            mainBinding.setAlarmBtn.setVisibility(View.GONE);
            mainBinding.setRouteBtn.setVisibility(View.VISIBLE);
            mainBinding.favoriteRouteRecyclerView.setVisibility(View.VISIBLE);
            mainBinding.recentRouteRecyclerView.setVisibility(View.VISIBLE);
            mainBinding.favoriteRouteLinearLayout.setVisibility(View.VISIBLE);
            mainBinding.recentRouteLinearLayout.setVisibility(View.VISIBLE);
            //false를 리턴해 MainActivity에서 setFavoriteStation() 실행
        }
    }
}
