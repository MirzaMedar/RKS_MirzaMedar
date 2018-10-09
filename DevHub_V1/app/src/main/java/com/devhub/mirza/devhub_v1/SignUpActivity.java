package com.devhub.mirza.devhub_v1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.UIRegister;
import com.devhub.mirza.devhub_v1.UIObjects.Users;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static Bitmap image = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_page);
        Button registerBtn = (Button) findViewById(R.id.registracija_btn2);
        Button pickProfilePhoto = (Button) findViewById(R.id.profilePhotoBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
                String lastName = ((EditText)findViewById(R.id.lastName)).getText().toString();
                String email = ((EditText)findViewById(R.id.editText8)).getText().toString();
                String position = ((EditText)findViewById(R.id.position)).getText().toString();
                String skills = ((EditText)findViewById(R.id.skills)).getText().toString();
                String username = ((EditText)findViewById(R.id.username2)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                if(image!=null)
                {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 5, stream);
                    byte[] byteArray = stream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    Integer length = byteArray.length;
                    UIRegister registerModel = new UIRegister(firstName,lastName,email,byteArray,encoded,position,skills,username,password);
                    sendNetworkRequest(registerModel);
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Sva polja su obavezna!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        pickProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       if(requestCode == PICK_IMAGE)
       {
           try{
               final Uri imageUri = data.getData();
               final  InputStream inputStream = getContentResolver().openInputStream(imageUri);
               final Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
               image = selectedImage;
           }
           catch (FileNotFoundException e) {
            e.printStackTrace();
           }
       }
    }
    private void sendNetworkRequest(UIRegister model)
    {
        if(model.getFirstName().toString().length()<=0 || model.getLastName().toString().length()<=0 || model.getEmail().toString().length()<=0 || model.getStringPhoto().toString().length()<=0 || model.getPosition().toString().length()<=0 || model.getSkills().toString().length()<=0 || model.getUsername().toString().length()<=0 || model.getPassword().toString().length()<=0)
        {
            Toast.makeText(SignUpActivity.this,"Sva polja na formi su obavezna!",Toast.LENGTH_SHORT).show();
        }
        else{
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Učitavanje");
            progress.setMessage("Registracija u toku...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_CONFIG.APIAdress)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            DevHubClient client = retrofit.create(DevHubClient.class);
            Call<Users> call = client.createAccount(model);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.code()==409)
                    {
                        Toast.makeText(SignUpActivity.this,"Korisničko ime je zauzteo!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignUpActivity.this,"Registracija uspješna!",Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                        startActivity(new Intent(SignUpActivity.this,LoginRegisterActivity.class));
                    }

                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,"Doslo je do greške!",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
