using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
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


        // POST: api/Traject
        [HttpPost]
        public IActionResult CreateTraject([FromBody] TrajectModel traject)
        {
            try
            {
                ResponseModelWithData<Guid> responseModel = new ResponseModelWithData<Guid>();

                var token = Utils.GetToken(Request);
                var employee = sessionService.GetEmployeeByToken(token);

                var trajectId = trajectService.CreateTraject(employee, traject.ToEntity());
                
                responseModel.IsResponseOK = true;
                responseModel.Data = trajectId;

                return Ok(responseModel);
            }
            catch (HandledException he)
            {
                var response = new ResponseModelWithData<Guid>()
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

        // POST: api/Trajects/trajectId
        [HttpPost("Incidents/{trajectId}")]
        public IActionResult CreateIncident(Guid trajectId, [FromBody] IncidentModel incident)
        {
            try
            {
                
                ResponseModelWithData<Guid> responseModel = new ResponseModelWithData<Guid>();

                var token = Utils.GetToken(Request);
                var employee = sessionService.GetEmployeeByToken(token);

                incident.CreationTime = DateTime.Now;

                trajectService.AssignIncidentToTraject(trajectId, employee, incident.ToEntity());

                responseModel.IsResponseOK = true;
                responseModel.Data = trajectId;

                return Ok(responseModel);
            }
            catch (HandledException he)
            {
                var response = new ResponseModelWithData<Guid>()
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
