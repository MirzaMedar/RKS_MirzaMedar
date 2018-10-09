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
    [RoutePrefix("api/authtokens")]
    public class AuthenticationTokensController : ApiController
    {
        private DevHubEntities1 _db = new DevHubEntities1();
    }
}
