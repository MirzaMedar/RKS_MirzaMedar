package com.devhub.mirza.devhub_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.AuthenticationTokens;
import com.devhub.mirza.devhub_v1.UIObjects.PostsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_CONFIG.APIAdress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        DevHubClient client = retrofit.create(DevHubClient.class);
        Call<Void> call = client.logout(Global.loggedUser.getToken().getToken().toString(),Integer.toString(Global.loggedUser.getUser().getUserID()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(LogoutActivity.this,LoginRegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
