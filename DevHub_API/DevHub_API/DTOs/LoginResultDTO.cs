using DevHub_API.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DevHub_API.DTOs
{
    public class LoginResultDTO
    {
        public AuthenticationTokens token { get; set; }
        public Users user { get; set; }
    }
}