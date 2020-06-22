using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class TrajectReportLine
    {
        public double Distance { get; set; }
        public double Duration { get; set; }
        public bool IsFinished { get; set; }
        public string StartDate { get; set; }
        public string UserName { get; set; }
    }
}
