package com.devhub.mirza.devhub_v1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.UIAddPost;
import com.devhub.mirza.devhub_v1.UIObjects.Users;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPostActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static Bitmap image=null;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_page);
        final EditText postTile = findViewById(R.id.postTitle);
        final EditText postText = findViewById(R.id.postText);
        Button uploadSSBtn = findViewById(R.id.upload_ss_btn);
        Button saveBtn = findViewById(R.id.button2);
        uploadSSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image!=null)
                {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 5, stream);
                    byte[] byteArray = stream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    UIAddPost model = new UIAddPost(Global.loggedUser.getUser().getUserID(),postTile.getText().toString(),postText.getText().toString(), encoded);
                    SendNetworkRequest(model);
                }
               else{
                    Toast.makeText(AddPostActivity.this,"Sva polja na formi su obavezna!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Toolbar t = findViewById(R.id.toolbar);
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
        Glide.with(AddPostActivity.this)
                .load(API_CONFIG.APIAdress + Global.loggedUser.getUser().getProfilePhotoPath().toString())
                .into(profile_image);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(AddPostActivity.this,MainActivity.class));
                        break;
                    case R.id.new_post:
                        startActivity(new Intent(AddPostActivity.this,AddPostActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(AddPostActivity.this,ProfileActivity.class));
                        break;
                    case R.id.logout:
                        startActivity(new Intent(AddPostActivity.this,LogoutActivity.class));
                        break;
                }

                return true;
            }
        });

    }

    private void SendNetworkRequest(UIAddPost model) {
        if(model.getTitle().toString().length()<=0 || model.getPost().toString().length()<=0 || model.getPhotoBase64().toString().length()<=0)
        {
            Toast.makeText(AddPostActivity.this,"Sva polja na formi su obavezna!",Toast.LENGTH_SHORT).show();
        }
        else{
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Loading");
            progress.setMessage("Saving your post...");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_CONFIG.APIAdress)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            DevHubClient client = retrofit.create(DevHubClient.class);
            Call<UIAddPost> call = client.addPost(Global.loggedUser.getToken().getToken(),model);
            call.enqueue(new Callback<UIAddPost>() {
                @Override
                public void onResponse(Call<UIAddPost> call, Response<UIAddPost> response) {
                    Toast.makeText(AddPostActivity.this,"Successfully added new post!",Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                    startActivity(new Intent(AddPostActivity.this,MainActivity.class));
                }

                @Override
                public void onFailure(Call<UIAddPost> call, Throwable t) {
                    // retrofit error,returning invoking onFailure even if API returned status code 200
                    progress.dismiss();
                    startActivity(new Intent(AddPostActivity.this,MainActivity.class));
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PICK_IMAGE)
        {
            try{
                final Uri imageUri = data.getData();
                final InputStream inputStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                image = selectedImage;
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
