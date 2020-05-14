using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Query.Internal;
using ST.BusinessLogic.Interfaces;
using ST.Data.Entities;
using ST.Web.API.Models;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CompaniesController : ControllerBase
    {

        ICompanyService companyService;
        IAdministratorService administratorService;

        public CompaniesController(ICompanyService companyService, IAdministratorService administratorService)
        {
            this.companyService = companyService;
            this.administratorService = administratorService;

        }

        public IActionResult Get()
        {
            try
            {
                return Ok(companyService.GetAll());
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }


        [HttpPost]
        public IActionResult CreateCompany([FromBody] CompanyModel company)
        {
            try
            {
                companyService.AddCompany(company.Name);
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

        }

        [HttpPost("administrators")]
        public IActionResult CreateAdministrator([FromBody] AdministratorModel administrator)
        {
            try
            {
                Company company = companyService.GetCompanyByName(administrator.CompanyName);
                Administrator admin = administrator.ToEntity();
                admin.Company = company;
                administratorService.CreateAdministrator(admin);
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }
    }

}
