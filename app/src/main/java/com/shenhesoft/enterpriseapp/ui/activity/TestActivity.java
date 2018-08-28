package com.shenhesoft.enterpriseapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shenhesoft.enterpriseapp.R;
import com.shenhesoft.enterpriseapp.widget.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SearchFragment searchFragment = SearchFragment.newInstance();

        searchFragment.setOnTextWatcherListener(new SearchFragment.IOnTextWatcherListener() {
            @Override
            public void textChange(String s) {

            }
        });
        searchFragment.setOnSearchClickListener(new SearchFragment.IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String key) {
                Log.e(TAG, "OnSearchClick: " + key);
            }
        });
        List<String> list = new ArrayList<>();
        list.add("as");
        list.add("sa");
        searchFragment.addData(list);
        searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
    }
}
