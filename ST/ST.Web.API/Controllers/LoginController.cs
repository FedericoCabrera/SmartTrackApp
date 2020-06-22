using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Web.API.Models;
using static ST.Data.Entities.Employee;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        ISessionService sessionService;
        IEmployeeService employeeService;
        IAdministratorService administratorService;
        IUserService userService;

        public LoginController(ISessionService sessionService, IEmployeeService employeeService, IAdministratorService administratorService, IUserService userService)
        {
            this.sessionService = sessionService;
            this.employeeService = employeeService;
            this.administratorService = administratorService;
            this.userService = userService;
        }

        [HttpPost]
        public IActionResult Post([FromBody]LoginModel login)
        {
            try
            {
                var session = sessionService.Login(login.UserName, login.Password);
                var ret = new SessionModel();
                var user = userService.GetUserByUserName(login.UserName);

                ret.Name = user.Name;
                ret.Lastname = user.LastName;
                ret.UserId = user.Id;
                ret.Username = user.UserName;
                ret.Token = session.Token;

                if (user is Administrator)
                    ret.IsAdmin = true;
                else {
                    ret.IsAdmin = false;
                    employeeService.PutEmployeeStatus(ret.UserId, Status.CONNECTED);
                }
                   
                var response = new ResponseModelWithData<SessionModel>()
                {
                    Data = ret,
                    IsResponseOK = true
                };

                return Ok(response);
            }
            catch(HandledException he)
            {
                var response = new ResponseModelWithData<SessionModel>()
                {
                    IsResponseOK = false,
                    ErrorMessage = he.Message
                };

                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }


        [HttpPut("logout")]
        public IActionResult Logout()
        {
            try
            {
                ResponseModel responseModel = new ResponseModel();
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                sessionService.Logout(user.UserName);
                if (user is Employee) 
                { 
                    employeeService.GetEmployeeById(user.Id);
                    employeeService.PutEmployeeStatus(user.Id , Status.DISCONNECTED);
                }
                var response = new ResponseModel()
                {
                    ErrorMessage = "",
                    IsResponseOK = true
                };

                return Ok(response);
            }
            catch (HandledException he)
            {
                var response = new ResponseModel()
                {
                    IsResponseOK = false,
                    ErrorMessage = he.Message
                };

                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }


    }
}