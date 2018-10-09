package com.devhub.mirza.devhub_v1.Helpers;

import android.net.http.HttpResponseCache;

import com.devhub.mirza.devhub_v1.AddPostActivity;
import com.devhub.mirza.devhub_v1.UIObjects.AuthenticationTokens;
import com.devhub.mirza.devhub_v1.UIObjects.Comments;
import com.devhub.mirza.devhub_v1.UIObjects.LoginResultDTO;
import com.devhub.mirza.devhub_v1.UIObjects.NewCommentDTO;
import com.devhub.mirza.devhub_v1.UIObjects.Posts;
import com.devhub.mirza.devhub_v1.UIObjects.PostsDTO;
import com.devhub.mirza.devhub_v1.UIObjects.UIAddPost;
import com.devhub.mirza.devhub_v1.UIObjects.UIEditProfile;
import com.devhub.mirza.devhub_v1.UIObjects.UILogin;
import com.devhub.mirza.devhub_v1.UIObjects.UIRegister;
import com.devhub.mirza.devhub_v1.UIObjects.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DevHubClient {

    @POST("api/authentication/Register")
    Call<Users> createAccount(@Body UIRegister registerModel);

    @POST("api/authentication/Login")
    Call<LoginResultDTO> login(@Body UILogin loginModel);
    @POST("api/authentication/EditProfile")
    Call<UIEditProfile> editProfile(@Body UIEditProfile editModel);

    @POST("api/posts/AddPost")
    Call<UIAddPost> addPost(@Header("auth_token") String token, @Body UIAddPost model);

    @GET("api/posts/GetAllPosts")
    Call<List<PostsDTO>> getAllPosts(@Header("auth_token") String token);

    @GET("api/posts/GetPostById/{postId}")
    Call<PostsDTO> getPostById(@Header("auth_token")String token, @Path("postId") String postId);

    @GET("api/posts/GetCommentsByPostId/{postId}")
    Call<List<Comments>> getCommentsByPostId(@Header("auth_token")String token, @Path("postId") String postId);


    @POST("api/posts/AddNewComment")
    Call<NewCommentDTO> AddNewComment(@Header("auth_token") String token, @Body NewCommentDTO model);


    @GET("api/authentication/Logout/{userId}")
    Call<Void> logout(@Header("auth_token") String token,@Path("userId") String userId);
}
