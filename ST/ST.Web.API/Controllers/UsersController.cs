using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.DataAccess.Migrations;
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

        public UsersController(IAdministratorService administratorService, ICompanyService companyService, IEmployeeService employeeService, ISessionService sessionService)
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

                var employees = companyService.GetAllEmployees(companyId);

                var response = new ResponseModelWithData<IEnumerable<Employee>>()
                {
                    Data = employees,
                    IsResponseOK = true
                };

                return Ok(response);     

            }
            catch (HandledException he)
            {
                var response = new ResponseModelWithData<IEnumerable<Employee>>()
                {
                    IsResponseOK = true,
                    ErrorMessage = he.Message
                };

                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }

        [HttpGet("location", Name = "Get Location")]
        public IActionResult GetLocation()
        {
            try
            {
                var companyId = GetCompanyID();
                return Ok(companyService.GetEmployeesActiveWithLocation(companyId));

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
                ResponseModelWithData<EmployeeModel> responseModel = new ResponseModelWithData<EmployeeModel>();
                var companyId = GetCompanyID();
                companyService.AddEmployee(companyId, employee.ToEntity());
                responseModel.IsResponseOK = true;
                responseModel.Data = EmployeeModel.ToModel(employeeService.GetEmployeeByUsername(employee.UserName));
                return Ok(responseModel);
            }
            catch (HandledException he)
            {
                var response = new ResponseModel()
                {
                    IsResponseOK = true,
                    ErrorMessage = he.Message
                };

                return Ok(response);
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
                ResponseModel responseModel = new ResponseModel();
                var token = new Guid(Request.Headers["Authorization"]);
                var admin = sessionService.AuthorizeAdminByToken(token);
                //Administrator admin = administratorService.GetAdminById(user.Id);

                var company = admin.Company;
                var companyId = company.Id;
                companyService.RemoveEmployee(companyId, employee.Id);
                employeeService.RemoveEmployee(employee.ToEntity());
                responseModel.IsResponseOK = true;

                return Ok(responseModel);
            }
            catch (HandledException he)
            {
                var response = new ResponseModel()
                {
                    IsResponseOK = true,
                    ErrorMessage = he.Message
                };

                return Ok(response);
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
                ResponseModelWithData<EmployeeModel> responseModel = new ResponseModelWithData<EmployeeModel>();
                var token = new Guid(Request.Headers["Authorization"]);
                var user = sessionService.GetUserByToken(token);
                Administrator admin = administratorService.GetAdminById(user.Id);
                employeeService.ModifyEmployee(employee.ToEntity());
                responseModel.IsResponseOK = true;
                responseModel.Data = EmployeeModel.ToModel(employeeService.GetEmployeeByUsername(employee.UserName));
                return Ok(responseModel);
            }
            catch (HandledException he)
            {
                var response = new ResponseModel()
                {
                    IsResponseOK = true,
                    ErrorMessage = he.Message
                };

                return Ok(response);
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
            catch (HandledException he)
            {
                var response = new ResponseModel()
                {
                    IsResponseOK = true,
                    ErrorMessage = he.Message
                };

                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }

        private Guid GetCompanyID() 
        {
            var token = Utils.GetToken(Request);
            var user = sessionService.GetUserByToken(token);
            Administrator admin = administratorService.GetAdminById(user.Id);
            var company = admin.Company;

            return company.Id;
           
        }
    }
}