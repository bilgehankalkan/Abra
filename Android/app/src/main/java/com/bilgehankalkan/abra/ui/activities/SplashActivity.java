package com.bilgehankalkan.abra.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.bilgehankalkan.abra.service.models.TokenRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MainActivity.class));
        finish();

        sendFirebaseToken();
    }

    private void sendFirebaseToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken(FirebaseInstanceId.getInstance().getToken());
        Call<ResponseBody> call = apiInterface.postFirebaseToken(getHeader(), USER_ID, tokenRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

            }
        });
    }
}
