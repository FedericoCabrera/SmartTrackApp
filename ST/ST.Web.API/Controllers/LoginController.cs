using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic.Interfaces;
using ST.Web.API.Models;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        ISessionService sessionService;

        public LoginController(ISessionService sessionService)
        {
            this.sessionService = sessionService;
        }


        public IActionResult Post([FromBody]LoginModel login)
        {
            try
            {
                var session = sessionService.Login(login.UserName, login.Password);
                return Ok(session.Token);
            }
            catch (Exception)
            {
                return BadRequest("Incorrect User or Password.");
            }
        }


    }
}