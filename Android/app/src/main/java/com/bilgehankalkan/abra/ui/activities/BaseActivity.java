package com.bilgehankalkan.abra.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bilgehankalkan.abra.service.ApiClient;
import com.bilgehankalkan.abra.service.ApiInterface;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Bilgehan on 17.02.2018.
 */

public class BaseActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;

    private static Map<String, String> mapHeader;
    public static String TOKEN = "638B736C-C73B-4BB5-AC9C-3B1C08F84078";
    public static String USER_ID = "5a888e45c550959c285e1209";

    public InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (apiInterface == null)
            apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    }

    public static Map<String, String> getHeader() {
        if (mapHeader == null) {
            mapHeader = new HashMap<>();
            mapHeader.put("Content-Type", "application/json");
            mapHeader.put("Authorization", "token:" + TOKEN);

            String localeString = Locale.getDefault().getCountry()
                    + "-" + Locale.getDefault().getLanguage();
            mapHeader.put("Accept-Language", localeString);
        }
        return mapHeader;
    }

    public void closeSoftKeyboard(View view) {
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
