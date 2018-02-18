package com.bilgehankalkan.abra.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bilgehankalkan.abra.service.models.TokenRequest;
import com.bilgehankalkan.abra.utils.messaging.FirebaseMessagingService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sendFirebaseToken();

        startActivity(new Intent(this, MainActivity.class));
        finish();
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
