package com.devhub.mirza.devhub_v1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devhub.mirza.devhub_v1.Helpers.API_CONFIG;
import com.devhub.mirza.devhub_v1.Helpers.DevHubClient;
import com.devhub.mirza.devhub_v1.Helpers.Global;
import com.devhub.mirza.devhub_v1.UIObjects.Comments;
import com.devhub.mirza.devhub_v1.UIObjects.NewCommentDTO;
import com.devhub.mirza.devhub_v1.UIObjects.PostsDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab2CommentsActivity extends Fragment {



        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab2comments, container, false);

            final ListView commentsList = rootView.findViewById(R.id.commentsList);
            final EditText newCommentTxt = rootView.findViewById(R.id.comment);
            final Button addCommentBtn = rootView.findViewById(R.id.addCommentBtn);


            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_CONFIG.APIAdress)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            final DevHubClient client = retrofit.create(DevHubClient.class);
            int id = Global.current_post_review;
            Call<List<Comments>> call = client.getCommentsByPostId(Global.loggedUser.getToken().getToken().toString(),Integer.toString(Global.current_post_review));
            call.enqueue(new Callback<List<Comments>>() {
                @Override
                public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                    final List<Comments> fetchedComments = response.body();

                    commentsList.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return fetchedComments.size();
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
                                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                convertView = inflater.inflate(R.layout.comment_template,parent,false);
                            }
                            CircleImageView profilePhoto = convertView.findViewById(R.id.satyaProfile);
                            TextView ime = convertView.findViewById(R.id.ime);
                            TextView datum = convertView.findViewById(R.id.datum2);
                            TextView text = convertView.findViewById(R.id.textView6);

                            Glide.with(Tab2CommentsActivity.this)
                                    .load(API_CONFIG.APIAdress + fetchedComments.get(position).getUserProfilePhoto())
                                    .into(profilePhoto);
                            ime.setText(fetchedComments.get(position).getUsername());
                            datum.setText(fetchedComments.get(position).getDate());
                            text.setText(fetchedComments.get(position).getText());
                            return convertView;
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Comments>> call, Throwable t) {

                }
            });
            addCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(newCommentTxt.getText().toString().length()<=0)
                    {
                        Toast.makeText(getActivity(),"Sva polja na formi su obavezna!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        final ProgressDialog progress = new ProgressDialog(getActivity());
                        progress.setTitle("Loading");
                        progress.setMessage("Saving your post...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
                        NewCommentDTO newComment = new NewCommentDTO(Global.current_post_review,newCommentTxt.getText().toString(),Global.loggedUser.getUser().getUserID());

                        Call<NewCommentDTO> call = client.AddNewComment(Global.loggedUser.getToken().getToken().toString(),newComment);
                        call.enqueue(new Callback<NewCommentDTO>() {
                            @Override
                            public void onResponse(Call<NewCommentDTO> call, Response<NewCommentDTO> response) {
                                newCommentTxt.setText("");
                                progress.dismiss();
                                startActivity(new Intent(getActivity(),PostDetailsActivity.class));
                            }

                            @Override
                            public void onFailure(Call<NewCommentDTO> call, Throwable t) {

                            }
                        });
                    }

                }
            });
            return rootView;
        }
}
