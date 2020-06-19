using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ViewFeatures.Internal;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Web.API.Models;
using static ST.Data.Entities.Employee;

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
                employeeService.PutEmployeeStatus(employee.Id, Status.ON_A_TRIP);
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

        [HttpPut]
        public IActionResult EndTraject([FromBody] TrajectModel traject)
        {
            try
            {
                ResponseModelWithData<Traject> responseModel = new ResponseModelWithData<Traject>();

                var token = Utils.GetToken(Request);
                var employee = sessionService.GetEmployeeByToken(token);
                employeeService.PutEmployeeStatus(employee.Id, Status.CONNECTED);
                trajectService.EndTraject(employee, traject.ToEntity());

                responseModel.IsResponseOK = true;
                responseModel.Data = traject.ToEntity();

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

        [HttpPut("Incidents")]
        public IActionResult GetIncidentsReport([FromBody]DatesFilter datesFilter)
        {
            ResponseModelWithData<IEnumerable<IncidentReportModel>> responseModel = new ResponseModelWithData<IEnumerable<IncidentReportModel>>();

            try
            {
                var token = Utils.GetToken(Request);

                var dateFrom = new DateTime(datesFilter.YearFrom, datesFilter.MonthFrom, datesFilter.DayFrom);
                var dateTo = new DateTime(datesFilter.YearTo, datesFilter.MonthTo, datesFilter.DayTo);

                var admin = sessionService.AuthorizeAdminByToken(token);

                var list = trajectService.GetIncidentsReport(admin, dateFrom, dateTo);

                responseModel.Data = IncidentReportModel.ToModel(list);
                responseModel.IsResponseOK = true;

                return Ok(responseModel);
            }
            catch (HandledException he)
            {

                responseModel.IsResponseOK = false;
                responseModel.ErrorMessage = he.Message;

                return Ok(responseModel);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }

    }
}
