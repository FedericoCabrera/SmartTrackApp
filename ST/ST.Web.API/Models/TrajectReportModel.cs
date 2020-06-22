using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class TrajectReportModel : Model<TrajectReport, TrajectReportModel>
    {
        public TrajectReportModel()
        {
            Lines = new List<TrajectReportLineModel>();
        }

        public List<TrajectReportLineModel> Lines { get; set; }
        public double TotalDuration { get; set; }
        public double TotalDistance { get; set; }

        public override TrajectReport ToEntity() => new TrajectReport
        {
            TotalDistance = this.TotalDistance,
            TotalDuration = this.TotalDuration,
            Lines = TrajectReportLineModel.ToEntity(Lines)
        };

        protected override TrajectReportModel SetModel(TrajectReport entity)
        {
            TotalDistance = entity.TotalDistance;
            TotalDuration = entity.TotalDuration;
            Lines = TrajectReportLineModel.ToModel(entity.Lines);

            return this;
        }

        
    }
}
