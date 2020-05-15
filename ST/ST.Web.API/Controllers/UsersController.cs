using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.Data.Entities;
using ST.Web.API.Models;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : Controller
    {
        IAdministratorService administratorService;
        ICompanyService companyService;
        IUserService userService;
        ISessionService sessionService;

        public UsersController(IAdministratorService administratorService, ICompanyService companyService, IUserService userService, ISessionService sessionService)
        {
            this.administratorService = administratorService;
            this.companyService = companyService;
            this.userService = userService;
            this.sessionService = sessionService;
        }

        [HttpGet]
        public IActionResult Get()
        {
            try
            {
                return Ok(userService.GetAll());
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }

       // POST: api/Users
        [HttpPost]
        public IActionResult CreateEmployee([FromBody] EmployeeModel employee)
        {
            try
            {
               var token = new Guid(Request.Headers["Authorization"]);
               var user = sessionService.GetUserByToken(token);
               Administrator admin = administratorService.GetAdminById(user.Id);
               var company = admin.Company;
               var companyId = company.Id;
               companyService.AddEmployee(companyId, employee.ToEntity());          
               return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }
    }
}