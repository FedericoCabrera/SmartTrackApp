using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class TrajectReport
    {
        public TrajectReport()
        {
            Lines = new List<TrajectReportLine>();
        }

        public List<TrajectReportLine> Lines { get; set; }
        public double TotalDuration { get; set; }
        public double TotalDistance { get; set; }
    }
}
