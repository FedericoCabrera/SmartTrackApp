using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Web.API.Models;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TrajectsController : ControllerBase
    {

        IAdministratorService administratorService;
        ICompanyService companyService;
        IEmployeeService employeeService;
        ISessionService sessionService;
        ITrajectService trajectService;

        public TrajectsController(IAdministratorService administratorService, ICompanyService companyService, 
            IEmployeeService employeeService, ISessionService sessionService, ITrajectService trajectService)
        {
            this.administratorService = administratorService;
            this.companyService = companyService;
            this.employeeService = employeeService;
            this.sessionService = sessionService;
            this.trajectService = trajectService;
        }


        // POST: api/Users
        [HttpPost]
        public IActionResult CreateTraject([FromBody] TrajectModel traject)
        {
            try
            {
                ResponseModelWithData<TrajectModel> responseModel = new ResponseModelWithData<TrajectModel>();

                var token = Utils.GetToken(Request);
                var employee = sessionService.GetEmployeeByToken(token);

                trajectService.CreateTraject(employee, traject.ToEntity());
                
                responseModel.IsResponseOK = true;

                return Ok(responseModel);
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
