package com.devhub.mirza.devhub_v1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.AuthenticationTokens;
import com.devhub.mirza.devhub_v1.UIObjects.Posts;
import com.devhub.mirza.devhub_v1.UIObjects.PostsDTO;
import com.devhub.mirza.devhub_v1.UIObjects.UIEditProfile;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<PostsDTO> fetchedPosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // BOTTOM BAVIGATION VIEW
        setContentView(R.layout.home_page);

        //BottomNavigationView b = findViewById(R.id.bottom_navigation);

        /*Toolbar t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        final ListView postsList = findViewById(R.id.postsList);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView te = (TextView) headerView.findViewById(R.id.firstNameLastName);
        te.setText(Global.loggedUser.getUser().getFirstName()+ " " + Global.loggedUser.getUser().getLastName());


        TextView user_email = (TextView)headerView.findViewById(R.id.user_email);
        user_email.setText(Global.loggedUser.getUser().getEmail().toString());

        CircleImageView profile_image = headerView.findViewById(R.id.profile_image);
        Glide.with(MainActivity.this)
                .load(API_CONFIG.APIAdress + Global.loggedUser.getUser().getProfilePhotoPath().toString())
                .into(profile_image);
        String logirani = Global.loggedUser.getUser().getFirstName() + " " + Global.loggedUser.getUser().getLastName();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId())
                {
                    case R.id.home_page:
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case R.id.new_post:
                        startActivity(new Intent(MainActivity.this,AddPostActivity.class));
                        break;
                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        break;
                    case R.id.logout:
                        startActivity(new Intent(MainActivity.this,LogoutActivity.class));
                        break;
                }

                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddPostActivity.class));
            }
        });

       /* b.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_info:
                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_comments:
                        Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });*/



        // KRAJ UCITAVANJA FRAGMENATA



        // TOOLBAR
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       Toolbar t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_meni_ikona);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        // KRAJ TOOLBARA */

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_CONFIG.APIAdress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        DevHubClient client = retrofit.create(DevHubClient.class);
        Call<List<PostsDTO>> call = client.getAllPosts(Global.loggedUser.getToken().getToken().toString());
        call.enqueue(new Callback<List<PostsDTO>>() {
            @Override
            public void onResponse(Call<List<PostsDTO>> call, Response<List<PostsDTO>> response) {
                if(response.code()==401)
                {
                    Toast.makeText(MainActivity.this,"Not authorized!",Toast.LENGTH_SHORT).show();
                }
                if(response.isSuccessful())
                {
                    fetchedPosts = response.body();
                    Integer size = fetchedPosts.size();

                    postsList.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return fetchedPosts.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return null;
                        }

                        @Override
                        public long getItemId(int position) {
                            return 0;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            if(convertView==null)
                            {
                                final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                convertView = inflater.inflate(R.layout.post_template,parent,false);
                            }
                            CircleImageView userProfileImage = convertView.findViewById(R.id.user_profile_image);
                            TextView userUsername = convertView.findViewById(R.id.user_username);
                            ImageView postPhoto = convertView.findViewById(R.id.post_photo);
                            TextView postTitle = convertView.findViewById(R.id.post_title);
                            TextView postText = convertView.findViewById(R.id.post_text);
                            TextView postIdText = convertView.findViewById(R.id.post_id);
                            Glide.with(MainActivity.this)
                                    .load(API_CONFIG.APIAdress + fetchedPosts.get(position).getUserPhotoPath())
                                    .into(userProfileImage);
                            userUsername.setText(fetchedPosts.get(position).getUser());
                            Glide.with(MainActivity.this)
                                    .load(API_CONFIG.APIAdress + fetchedPosts.get(position).getPhotoPath())
                                    .into(postPhoto);
                            postTitle.setText(fetchedPosts.get(position).getTitle());
                            postText.setText(fetchedPosts.get(position).getText());
                            postIdText.setText(fetchedPosts.get(position).getPostID().toString());
                            return convertView;
                        }
                    });
                    postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TextView post_id = (TextView)view.findViewById(R.id.post_id);
                            Toast.makeText(MainActivity.this,post_id.getText().toString(),Toast.LENGTH_SHORT).show();
                            Global.current_post_review = Integer.parseInt(post_id.getText().toString());
                            startActivity(new Intent(MainActivity.this,PostDetailsActivity.class));
                        }
                    });
                }

            }
            @Override
            public void onFailure(Call<List<PostsDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    // TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //KRAJ TOOLBARA


    // BACK BUTTON
   /* @Override
    public boolean onSupportNavigateUp(){
        Toast.makeText(MainActivity.this,"Back",Toast.LENGTH_SHORT).show();

        return true;
    }*/
    //BACK BUTTON
}
