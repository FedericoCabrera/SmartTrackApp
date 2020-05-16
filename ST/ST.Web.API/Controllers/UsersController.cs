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
        IEmployeeService employeeService;
        ISessionService sessionService;

        public UsersController(IAdministratorService administratorService, ICompanyService companyService, IUserService userService, ISessionService sessionService)
        {
            this.administratorService = administratorService;
            this.companyService = companyService;
            this.employeeService = employeeService;
            this.sessionService = sessionService;
        }



        [HttpGet]
        public IActionResult Get()
        {
            try
            {
                var companyId = GetCompanyID();
                return Ok(companyService.GetAllEmployees(companyId));     

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
               var companyId = GetCompanyID();
               companyService.AddEmployee(companyId, employee.ToEntity());          
               return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }

        [HttpDelete]
        public IActionResult RemoveEmployee([FromBody] EmployeeModel employee)
        {
            try
            {
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                Administrator admin = administratorService.GetAdminById(user.Id);
                var company = admin.Company;
                var companyId = company.Id;
                companyService.RemoveEmployee(companyId, employee.Id);
                employeeService.RemoveEmployee(employee.ToEntity());
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }

        [HttpPut]
        public IActionResult UpdateEmployee([FromBody] EmployeeModel employee)
        {
            try
            {
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                Administrator admin = administratorService.GetAdminById(user.Id);
                employeeService.ModifyEmployee(employee.ToEntity());
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }


        [HttpPut]
        public IActionResult UpdateLocation([FromBody] LocationModel location)
        {
            try
            {
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                Employee employee = employeeService.GetEmployeeById(user.Id);
                employeeService.ModifyLocation(employee, location.ToEntity());
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }

        private Guid GetCompanyID() 
        {
            try
            {
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                Administrator admin = administratorService.GetAdminById(user.Id);
                var company = admin.Company;
                return company.Id;
            }
            catch (Exception ex)
            {

                throw new Exception();
            }
           
        }
    }
}