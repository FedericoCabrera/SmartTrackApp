using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class TrajectReportLineModel : Model<TrajectReportLine, TrajectReportLineModel>
    {
        public double Distance { get; set; }
        public double Duration { get; set; }
        public bool IsFinished { get; set; }
        public string StartDate { get; set; }
        public string UserName { get; set; }

        public override TrajectReportLine ToEntity() => new TrajectReportLine
        {
            Distance = this.Distance,
            Duration = this.Duration,
            IsFinished = this.IsFinished,
            StartDate = this.StartDate,
            UserName = this.UserName,

        };

        protected override TrajectReportLineModel SetModel(TrajectReportLine entity)
        {
            Distance = entity.Distance;
            Duration = entity.Duration;
            IsFinished = entity.IsFinished;
            StartDate = entity.StartDate;
            UserName = entity.UserName;

            return this;
        }

        public static List<TrajectReportLineModel> ToModel(List<TrajectReportLine> lines)
        {
            List<TrajectReportLineModel> linesModel = new List<TrajectReportLineModel>();

            foreach(TrajectReportLine line in lines)
            {
                linesModel.Add(TrajectReportLineModel.ToModel(line));
            }

            return linesModel;
        }

        public static List<TrajectReportLine> ToEntity(List<TrajectReportLineModel> lines)
        {
            List<TrajectReportLine> linesModel = new List<TrajectReportLine>();

            foreach (TrajectReportLineModel line in lines)
            {
                linesModel.Add(TrajectReportLineModel.ToEntity(line));
            }

            return linesModel;
        }
    }
}
