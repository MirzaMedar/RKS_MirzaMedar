using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.UIModels
{
    public class UIEditProfile
    {
        public int UserID { get; set; }
        public string newPosition { get; set; }
        public string newSkills { get; set; }
        public string newEmail { get; set; }
        public string newUsername { get; set; }

    }
}