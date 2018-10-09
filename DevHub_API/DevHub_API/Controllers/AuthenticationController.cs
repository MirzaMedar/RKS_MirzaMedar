using DevHub_API.DTOs;
using DevHub_API.Helpers;
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
    [RoutePrefix("api/authentication")]
    public class AuthenticationController : ApiController
    {
        private DevHubEntities1 _db = new DevHubEntities1();
        [HttpPost]
        [Route("Register")]
        public async Task<HttpResponseMessage> Register([FromBody]UIRegister model)
        {
            if (_db.Users.Where(x => x.Username == model.Username).FirstOrDefault() != null)
                return Request.CreateErrorResponse(HttpStatusCode.Conflict, "User with that username already exists!");
            Users newUser = new Users
            {
                FirstName = model.FirstName,
                LastName = model.LastName,
                Email = model.Email,
                PositionID = 5,
                Skills = model.Skills,
                RegisterDate = DateTime.Now,
                IsDeleted = false,
                Position = model.Position,
                Username = model.Username,
                PasswordSalt = PasswordHelper.GenerateSalt(),
            };
            newUser.PasswordHash = PasswordHelper.GenerateHash(newUser.PasswordSalt, model.Password);
            string location = Path.Combine(System.Web.Hosting.HostingEnvironment.MapPath(ConfigurationManager.AppSettings["ProfilePhotos"].ToString()));
            try
            {
                model.photo = Convert.FromBase64String(model.stringPhoto);
                File.WriteAllBytes(location + newUser.Username + ".jpg", model.photo);
                newUser.ProfilePhotoPath = ConfigurationManager.AppSettings["ProfilePhotosForDB"].ToString() + newUser.Username + ".jpg";
            }
            catch (Exception ex)
            {

                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
            try
            {
                _db.Users.Add(newUser);
                _db.SaveChanges();
                return Request.CreateResponse(HttpStatusCode.OK,newUser);
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
        [HttpPost]
        [Route("Login")]
        public async Task<HttpResponseMessage> Login([FromBody]UILogin model)
        {
            Users foundUser = await _db.Users.Where(x => x.Username == model.Username).FirstOrDefaultAsync();
            if(foundUser==null)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Not authorized!");
            string hash = PasswordHelper.GenerateHash(foundUser.PasswordSalt, model.Password);
            if (foundUser.PasswordHash != hash)
                return Request.CreateErrorResponse(HttpStatusCode.Unauthorized, "Not authorized!");
            //deactivate previous tokens
            foreach (var item in _db.AuthenticationTokens.Where(x => x.UserID == foundUser.UserID).ToList())
            {
                item.IsDeleted = true;
            }
            _db.SaveChanges();
            AuthenticationTokens newToken = new AuthenticationTokens
            {
                DeviceID = model.DeviceID,
                IsDeleted = false,
                UserID = foundUser.UserID,
                TokenDate = DateTime.Now,
                Token = Guid.NewGuid().ToString()
            };
            _db.AuthenticationTokens.Add(newToken);
            _db.SaveChanges();
            LoginResultDTO loginResult = new LoginResultDTO
            {
                token = newToken,
                user = foundUser
            };
            return Request.CreateResponse(HttpStatusCode.OK, loginResult);
        }
        [HttpPost]
        [Route("EditProfile")]
        public HttpResponseMessage EditProfile([FromBody]UIEditProfile model)
        {
            if (String.IsNullOrEmpty(model.newEmail) || String.IsNullOrEmpty(model.newPosition) || String.IsNullOrEmpty(model.newSkills) || String.IsNullOrEmpty(model.newUsername))
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "Validation error!");
            }
            Users foundUser = _db.Users.Where(x => x.UserID== model.UserID).FirstOrDefault();
            if (foundUser != null)
            {
                if(!String.Equals(model.newUsername,foundUser.Username))
                {
                    if (_db.Users.Where(x => x.Username == model.newUsername).FirstOrDefault() != null)
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, "Username already in use!");
                }
                foundUser.Email = model.newEmail;
                foundUser.Position = model.newPosition;
                foundUser.Skills = model.newSkills;
                foundUser.Username = model.newUsername;
                _db.SaveChanges();
                return Request.CreateResponse(HttpStatusCode.OK,model);
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "User not found!");
            }
           
         
        }
        [HttpGet]
        [Route("Logout/{userId}")]
        public HttpResponseMessage Logout(int userId)
        {

            _db.AuthenticationTokens.Where(x => x.UserID == userId).FirstOrDefault().IsDeleted = true;
            _db.SaveChanges();
            return Request.CreateResponse(HttpStatusCode.OK);
        }
    }
}
