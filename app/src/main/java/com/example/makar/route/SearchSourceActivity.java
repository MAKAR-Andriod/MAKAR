package com.example.makar.route;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.makar.data.OdsayStation;
import com.example.makar.data.SearchAdapter;
import com.example.makar.data.Station;
import com.example.makar.databinding.ActivitySearchSourceBinding;
import com.example.makar.mypage.SetFavoriteStationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchSourceActivity extends AppCompatActivity {
    ActivitySearchSourceBinding binding;
    static Station sourceStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchSourceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setActionBar();
        setToolBar();
        setHideKeyBoard();
        setSearchView(); //searchView request focus


        RecyclerView recyclerView = binding.searchSourceRecyclerView;
        List<Station> resultList = new ArrayList<>();
        SearchAdapter adapter = new SearchAdapter(this, resultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding.searchViewSource.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (!newText.isEmpty()) {
                    CollectionReference collectionRef = db.collection("stations");

                    //newText로 시작하는 모든 역 검색
                    Query query = collectionRef.orderBy("cleanStationName")
                            .startAt(newText)
                            .endAt(newText + "\uf8ff");

                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                resultList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Station station = document.toObject(Station.class);
                                    CollectionReference odsayStations = document.getReference()
                                            .collection("odsay_stations");
                                    odsayStations.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> odsayTask) {
                                            if (odsayTask.isSuccessful()) {
                                                for (QueryDocumentSnapshot odsayDocument : odsayTask.getResult()) {
                                                    OdsayStation odsayStation = odsayDocument.toObject(OdsayStation.class);
                                                    System.out.println(odsayStation);
                                                    station.setOdsayStation(odsayStation);
                                                    resultList.add(station);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            } else {
                                                Log.d("MAKAR", "검색 중 오류 발생: ", odsayTask.getException());
                                            }
                                        }
                                    });

                                }

                            } else {
                                Log.d("MAKAR", "검색 중 오류 발생: ", task.getException());
                            }
                        }
                    });

                }
                return true;
            }
        });

        //recyclerView click listener
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Station station) {
                sourceStation = station;
                finish();
            }
        });

        //즐겨찾는 역 출발지로 설정
        binding.homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SetFavoriteStationActivity.homeStation != null) {
                    //TODO homeStation에 Odsay관련 정보 초기화x -> 수정필요
                    sourceStation = SetFavoriteStationActivity.homeStation;
                    finish();
                } else {
                    startActivity(new Intent(SearchSourceActivity.this, SetFavoriteStationActivity.class));
                }
            }
        });

        binding.schoolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SetFavoriteStationActivity.schoolStation != null) {
                    //TODO schoolStation에 Odsay관련 정보 초기화x -> 수정필요
                    sourceStation = SetFavoriteStationActivity.schoolStation;
                    finish();
                } else {
                    startActivity(new Intent(SearchSourceActivity.this, SetFavoriteStationActivity.class));
                }
            }
        });

        binding.detailBtn.setOnClickListener(view -> {
            startActivity(new Intent(SearchSourceActivity.this, SetFavoriteStationActivity.class));
        });
    }


    //searchView input 설정
    private void setSearchView() {
        SearchView searchView = binding.searchViewSource;
        searchView.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
    }

    //키보드 내리기
    private void setHideKeyBoard() {
        View rootView = findViewById(android.R.id.content);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 터치 이벤트가 발생시 키보드를 숨기기
                hideKeyboard();
                return false;
            }
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setToolBar() {
        binding.toolbarSearchSource.toolbarText.setText("출발역 입력");
        binding.toolbarSearchSource.toolbarImage.setVisibility(View.GONE);
        binding.toolbarSearchSource.toolbarButton.setVisibility(View.GONE);
    }

    private void setActionBar() {
        setSupportActionBar(binding.toolbarSearchSource.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}