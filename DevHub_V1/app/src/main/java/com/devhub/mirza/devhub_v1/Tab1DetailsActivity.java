package com.devhub.mirza.devhub_v1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.PostsDTO;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab1DetailsActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1details, container, false);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_CONFIG.APIAdress)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        DevHubClient client = retrofit.create(DevHubClient.class);
        int id = Global.current_post_review;
        Call<PostsDTO> call = client.getPostById(Global.loggedUser.getToken().getToken().toString(),Integer.toString(Global.current_post_review));
        call.enqueue(new Callback<PostsDTO>() {
            @Override
            public void onResponse(Call<PostsDTO> call, Response<PostsDTO> response) {
                Global.current_post_review_data = response.body();
                TextView textView = (TextView) rootView.findViewById(R.id.post_text);
                textView.setMovementMethod(new ScrollingMovementMethod());
                TextView title = rootView.findViewById(R.id.post_title);
                title.setText(Global.current_post_review_data.getTitle().toString());
                ImageView photo = rootView.findViewById(R.id.post_photo);
                Glide.with(Tab1DetailsActivity.this)
                        .load(API_CONFIG.APIAdress + Global.current_post_review_data.getPhotoPath().toString())
                        .into(photo);
                TextView text = rootView.findViewById(R.id.post_text);
                text.setText(Global.current_post_review_data.getText().toString());

            }

            @Override
            public void onFailure(Call<PostsDTO> call, Throwable t) {
                Toast.makeText(getActivity(),"Doslo je do greške",Toast.LENGTH_SHORT).show();
            }
        });





        return rootView;
    }


}
