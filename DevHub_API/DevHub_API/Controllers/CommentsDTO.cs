using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.Controllers
{
    public class CommentsDTO
    {
        public string Username { get; set; }
        public string UserProfilePhoto { get; set; }
        public string Date { get; set; }
        public string Text { get; set; }

    }
}