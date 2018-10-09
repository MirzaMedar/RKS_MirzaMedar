using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.DTOs
{
    public class PostsDTO
    {
        public int PostID { get; set; }
        public string Title { get; set; }
        public string Text { get; set; }
        public string PhotoPath { get; set; }
        public string Date { get; set; }

        public string User { get; set; }
        public string UserPhotoPath { get; set; }
    }
}