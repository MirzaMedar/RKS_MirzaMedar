package com.devhub.mirza.devhub_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.UIEditProfile;
import com.devhub.mirza.devhub_v1.UIObjects.Users;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class ProfileActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Toolbar t = findViewById(R.id.toolbar);
        CircleImageView profileImage = findViewById(R.id.profile_image);
        final TextView imePrezimeLbl = findViewById(R.id.imePrezimeLabel);
        final TextView positionLbl = findViewById(R.id.positionInput);
        final TextView skillsLbl = findViewById(R.id.skillsInput);
        final TextView emailLbl = findViewById(R.id.editText5);
        final TextView usernameLbl = findViewById(R.id.editText6);
        Button sacuvajBtn = findViewById(R.id.sacuvajPromjeneBtn);
        setSupportActionBar(t);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_meni_ikona);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView te = (TextView) headerView.findViewById(R.id.firstNameLastName);
        te.setText(Global.loggedUser.getUser().getFirstName()+ " " + Global.loggedUser.getUser().getLastName());


        TextView user_email = (TextView)headerView.findViewById(R.id.user_email);
        user_email.setText(Global.loggedUser.getUser().getEmail().toString());

        CircleImageView profile_image = headerView.findViewById(R.id.profile_image);
        Glide.with(ProfileActivity.this)
                .load(API_CONFIG.APIAdress + Global.loggedUser.getUser().getProfilePhotoPath().toString())
                .into(profile_image);

        sacuvajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileActivity.this);
                String errorMessage = "";
                if(positionLbl.getText().toString().length()<=0)
                    errorMessage+= "\n Position is required field!";
                if(skillsLbl.getText().toString().length()<=0)
                    errorMessage+= "\n Skills is required field!";
                if(emailLbl.getText().toString().length()<=0)
                    errorMessage += "\n Email is required field!";
                if(usernameLbl.getText().toString().length()<=0)
                    errorMessage+= "\n Username is required field!";
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                if(errorMessage.length()>0)
                {
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(API_CONFIG.APIAdress)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();
                    UIEditProfile model = new UIEditProfile(Global.loggedUser.getUser().getUserID(),emailLbl.getText().toString(),positionLbl.getText().toString(),skillsLbl.getText().toString(),usernameLbl.getText().toString());
                    DevHubClient client = retrofit.create(DevHubClient.class);
                    Call<UIEditProfile> call = client.editProfile(model);
                    call.enqueue(new Callback<UIEditProfile>() {
                        @Override
                        public void onResponse(Call<UIEditProfile> call, Response<UIEditProfile> response) {
                            if(response.code()==409)
                            {
                                Toast.makeText(ProfileActivity.this,"409!",Toast.LENGTH_SHORT).show();
                            }
                            if(response.code()==400){
                                Toast.makeText(ProfileActivity.this,"400!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                UIEditProfile model = response.body();
                                Global.loggedUser.getUser().setPosition(model.getNewPosition());
                                Global.loggedUser.getUser().setSkills(model.getNewSkills());
                                Global.loggedUser.getUser().setEmail(model.getNewEmail());
                                Global.loggedUser.getUser().setUsername(model.getNewUsername());
                                positionLbl.setText(model.getNewPosition());
                                skillsLbl.setText(model.getNewSkills());
                                usernameLbl.setText(model.getNewUsername());
                                emailLbl.setText(model.getNewEmail());
                                Toast.makeText(ProfileActivity.this,"Profile successfully edited!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UIEditProfile> call, Throwable t) {
                            //problem sa retrofitom,okida failure iako api vrati status code 200
                            Toast.makeText(ProfileActivity.this,"Profile successfully edited!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Glide.with(this)
                .load(API_CONFIG.APIAdress + Global.loggedUser.getUser().getProfilePhotoPath().toString())
                .into(profileImage);
        imePrezimeLbl.setText(Global.loggedUser.getUser().getFirstName() + " " + Global.loggedUser.getUser().getLastName());
        positionLbl.setText(Global.loggedUser.getUser().getPosition().toString());
        skillsLbl.setText(Global.loggedUser.getUser().getSkills().toString());
        emailLbl.setText(Global.loggedUser.getUser().getEmail().toString());
        usernameLbl.setText(Global.loggedUser.getUser().getUsername().toString());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                        break;
                    case R.id.new_post:
                        startActivity(new Intent(ProfileActivity.this,AddPostActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                        break;
                    case R.id.logout:
                        startActivity(new Intent(ProfileActivity.this,LogoutActivity.class));
                        break;
                }

                return true;
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
