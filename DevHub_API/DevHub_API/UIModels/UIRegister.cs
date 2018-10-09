using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.UIModels
{
    public class UIRegister
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public byte[] photo { get; set; }
        public string stringPhoto { get; set; }
        public string Position { get; set; }
        public string Skills { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
    }
}