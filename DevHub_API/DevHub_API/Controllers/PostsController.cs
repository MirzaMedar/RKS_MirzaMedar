using DevHub_API.DTOs;
using DevHub_API.Models;
using DevHub_API.UIModels;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;

namespace DevHub_API.Controllers
{
    [RoutePrefix("api/posts")]
    public class PostsController : ApiController
    {
        DevHubEntities1 _db = new DevHubEntities1();
        [HttpGet]
        [Route("GetAllPosts")]
        public async Task<HttpResponseMessage> GetAllPosts()
        {
            
            // START TOKEN VALIDATION
            if(!Request.Headers.Contains("auth_token"))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            string token = Request.Headers.GetValues("auth_token").FirstOrDefault().ToString();
            if (String.IsNullOrEmpty(token))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            if (_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().IsDeleted == true)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            TimeSpan difference = DateTime.Now - _db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().TokenDate;
            if (difference.TotalHours > 3)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            // END TOKEN VALIDATION

            List<Posts> list = _db.Posts.Include(x => x.Users).ToList();
            
            List<PostsDTO> filtered = list.Select(x=> new PostsDTO {
                Date = x.PostDate.ToShortDateString(),
                PhotoPath = x.PhotoPath,
                PostID = x.PostID,
                Text = x.PostText,
                Title = x.Title,
                User = x.Users.Username,
                UserPhotoPath = x.Users.ProfilePhotoPath
            }).ToList();
            return Request.CreateResponse(HttpStatusCode.OK,filtered);
        }
        [HttpPost]
        [Route("AddPost")]
        public async Task<HttpResponseMessage> AddPost([FromBody]UIAddPost model)
        {
            // START TOKEN VALIDATION
            string token = Request.Headers.GetValues("auth_token").FirstOrDefault().ToString();
            if (String.IsNullOrEmpty(token))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            if (_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().IsDeleted == true)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            TimeSpan difference = DateTime.Now -_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().TokenDate;
            if(difference.TotalHours > 3)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            // END TOKEN VALIDATION
            Posts newPost = new Posts
            {
                IsDeleted = false,
                UserID = model.UserID,
                PostDate = DateTime.Now,
                Title = model.Title,
                PostText = model.Post
            };
            _db.Posts.Add(newPost);
            model.Photo = Convert.FromBase64String(model.photoBase64);
            await _db.SaveChangesAsync();
            if(model.Photo.Length>0)
            {
                string location = Path.Combine(System.Web.Hosting.HostingEnvironment.MapPath(ConfigurationManager.AppSettings["PostPhotos"].ToString()));
                File.WriteAllBytes(location + newPost.PostID.ToString() + ".jpg", model.Photo);
                _db.Posts.Where(x=>x.PostID==newPost.PostID).FirstOrDefault().PhotoPath = ConfigurationManager.AppSettings["PostPhotosForDB"].ToString() + newPost.PostID.ToString() + ".jpg";
                _db.SaveChanges();
            }
            return Request.CreateResponse(HttpStatusCode.OK);
        }
        [HttpGet]
        [Route("GetPostById/{postId}")]
        public async Task<HttpResponseMessage> GetPostById(int postId)
        {
            // START TOKEN VALIDATION
            string token = Request.Headers.GetValues("auth_token").FirstOrDefault().ToString();
            if (String.IsNullOrEmpty(token))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            if (_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().IsDeleted == true)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            TimeSpan difference = DateTime.Now - _db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().TokenDate;
            if (difference.TotalHours > 3)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            // END TOKEN VALIDATION



            Posts foundPost = await _db.Posts.Where(x => x.PostID == postId).Include(x => x.Users).FirstOrDefaultAsync();
            if (foundPost == null)
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Post not found!");
            PostsDTO forReturn = new PostsDTO
            {
                Date = foundPost.PostDate.ToShortDateString(),
                PhotoPath = foundPost.PhotoPath,
                PostID = foundPost.PostID,
                Text = foundPost.PostText,
                Title = foundPost.Title,
                User = foundPost.Users.Username,
                UserPhotoPath = foundPost.Users.ProfilePhotoPath
            };
            return Request.CreateResponse(HttpStatusCode.OK, forReturn);
        }
        [HttpGet]
        [Route("GetCommentsByPostId/{postId}")]
        public async Task<HttpResponseMessage> GetCommentsByPostId(int postId)
        {
            // START TOKEN VALIDATION
            string token = Request.Headers.GetValues("auth_token").FirstOrDefault().ToString();
            if (String.IsNullOrEmpty(token))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            if (_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().IsDeleted == true)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            TimeSpan difference = DateTime.Now - _db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().TokenDate;
            if (difference.TotalHours > 3)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            // END TOKEN VALIDATION


            List<PostComments> commentsList = await _db.PostComments.Include(x => x.Comments).Include(x=>x.Comments.Users).Where(x => x.PostID == postId).ToListAsync();
            List<CommentsDTO> forReturn = commentsList.Select(x => new CommentsDTO
            {
                Date = x.CommentDate.ToShortDateString(),
                Text = x.Comments.Comment,
                Username = x.Comments.Users.Username,
                UserProfilePhoto = x.Comments.Users.ProfilePhotoPath
            }).ToList();
            return Request.CreateResponse(HttpStatusCode.OK, forReturn);
        }
        [HttpPost]
        [Route("AddNewComment")]
        public HttpResponseMessage AddNewComment([FromBody]NewCommentDTO model)
        {
            // START TOKEN VALIDATION
            string token = Request.Headers.GetValues("auth_token").FirstOrDefault().ToString();
            if (String.IsNullOrEmpty(token))
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            if (_db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().IsDeleted == true)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            TimeSpan difference = DateTime.Now - _db.AuthenticationTokens.Where(x => x.Token == token).FirstOrDefault().TokenDate;
            if (difference.TotalHours > 3)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Auth token is not valid!");
            // END TOKEN VALIDATION

            Comments newComment = new Comments
            {
                IsDeleted = false,
                UserID = model.UserID,
                Comment = model.Comment
            };
            _db.Comments.Add(newComment);
            PostComments newPostComment = new PostComments
            {
                CommentDate = DateTime.Now,
                CommentID = newComment.CommentID,
                PostID = model.PostID
            };

            _db.PostComments.Add(newPostComment);
            _db.SaveChanges();
            return Request.CreateResponse(HttpStatusCode.OK,model);
        }
    }
}
