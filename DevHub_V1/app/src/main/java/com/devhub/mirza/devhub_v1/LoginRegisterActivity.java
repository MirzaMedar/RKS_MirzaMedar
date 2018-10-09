package com.devhub.mirza.devhub_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.LoginResultDTO;
import com.devhub.mirza.devhub_v1.UIObjects.UILogin;
import com.devhub.mirza.devhub_v1.UIObjects.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_page);


        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });

        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRegisterActivity.this,SignUpActivity.class));
            }
        });

    }



    private void onLoginClick() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Učitavanje");
        progress.setMessage("Prijava u toku...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        EditText username =  (EditText)findViewById(R.id.username_input2);
        EditText password =  (EditText)findViewById(R.id.password_input);
        if(username.getText().toString().length()<=0 || password.getText().toString().length()<=0)
        {
            progress.dismiss();
            Toast.makeText(LoginRegisterActivity.this,"Molimo unesite ispravne podatke!",Toast.LENGTH_SHORT).show();
        }
        else{
            final String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            UILogin loginModel = new UILogin(username.getText().toString(),password.getText().toString(),android_id);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_CONFIG.APIAdress)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            DevHubClient client = retrofit.create(DevHubClient.class);
            Call<LoginResultDTO> call = client.login(loginModel);
            call.enqueue(new Callback<LoginResultDTO>() {
                @Override
                public void onResponse(Call<LoginResultDTO> call, Response<LoginResultDTO> response) {
                    progress.dismiss();
                    if(response.code()==200)
                    {
                        Global.loggedUser = response.body();
                        startActivity(new Intent(LoginRegisterActivity.this,MainActivity.class));
                    }
                    else if(response.code()==401)
                    {
                        Toast.makeText(LoginRegisterActivity.this,"Pogrešno korisničko ime ili lozinka!",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResultDTO> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(LoginRegisterActivity.this,"Došlo je do greške!",Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}
