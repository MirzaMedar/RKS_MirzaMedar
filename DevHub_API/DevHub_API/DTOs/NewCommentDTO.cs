using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.DTOs
{
    public class NewCommentDTO
    {
        public int PostID { get; set; }
        public string Comment { get; set; }
        public int UserID { get; set; }
    }
}