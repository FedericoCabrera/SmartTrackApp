using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class IncidentReportModel : Model<IncidentReport, IncidentReportModel>
    {
        public string Description { get; set; }
        public string Date { get; set; }
        public string Address { get; set; }
        public string UserName { get; set; }
        public string Base64Image { get; set; }

        public override IncidentReport ToEntity() => new IncidentReport()
        {
            Description = this.Description,
            Date = this.Date,
            Address = this.Address,
            UserName = this.UserName,
            Base64Image = this.Base64Image
        };

        protected override IncidentReportModel SetModel(IncidentReport entity)
        {
            Description = entity.Description;
            Date = entity.Date;
            Address = entity.Address;
            UserName = entity.UserName;
            Base64Image = entity.Base64Image;

            return this;
        }

        /*public static IEnumerable<IncidentReportModel> ToModel(IEnumerable<IncidentReport> list)
        {
            List<IncidentReportModel> incidents = new List<IncidentReportModel>();
            foreach(IncidentReport i in list)
            {
                incidents.Add(IncidentReportModel.ToModel(i));
            }
            return incidents;
        }*/
    }
}
