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
        IEmployeeService employeeService;
        IAdministratorService administratorService;

        public LoginController(ISessionService sessionService, IEmployeeService employeeService, IAdministratorService administratorService)
        {
            this.sessionService = sessionService;
            this.employeeService = employeeService;
            this.administratorService = administratorService;
        }


        public IActionResult Post([FromBody]LoginModel login)
        {
            try
            {
                var session = sessionService.Login(login.UserName, login.Password);
                var ret = new SessionModel();
                var employee = employeeService.GetEmployeeByUsername(login.UserName);
                if (employee == null)
                {
                    var admin = administratorService.GetAdministratorByUsername(login.UserName);
                    ret.Name = admin.Name;
                    ret.Lastname = admin.LastName;
                    ret.UserId = admin.Id;
                    ret.Username = admin.LastName;
                    ret.IsAdmin = true;
                    ret.Token = session.Token;

                }
                else
                {
                    ret.Name = employee.Name;
                    ret.Lastname = employee.LastName;
                    ret.UserId = employee.Id;
                    ret.Username = employee.LastName;
                    ret.IsAdmin = false;
                    ret.Token = session.Token;
                }
                return Ok(ret);
            }
            catch (Exception)
            {
                return BadRequest("Incorrect User or Password.");
            }
        }



        void UpdateEmployeeStatus(string username) 
        {
            var employee = employeeService.GetEmployeeByUsername(username);

        }
    }
}