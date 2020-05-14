using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
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

        public UsersController(IAdministratorService administratorService, ICompanyService companyService, IUserService userService)
        {
            this.administratorService = administratorService;
            this.companyService = companyService;
            this.userService = userService;
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
               Administrator admin = administratorService.GetAdminById(token);
               userService.CreateUser(employee.ToEntity());
               companyService.AddEmployee(admin.Company.Id , employee.ToEntity());          
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }
    }
}