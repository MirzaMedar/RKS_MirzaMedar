using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.UIModels
{
    public class UIAddPost
    {
        public int UserID { get; set; }
        public string Title { get; set; }
        public string Post { get; set; }
        public byte[] Photo { get; set; }
        public string photoBase64 { get; set; }
    }
}