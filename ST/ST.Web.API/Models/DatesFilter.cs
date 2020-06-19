using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class DatesFilter
    {
        public int YearFrom { get; set; }
        public int MonthFrom { get; set; }
        public int DayFrom { get; set; }

        public int YearTo { get; set; }
        public int MonthTo { get; set; }
        public int DayTo { get; set; }
    }
}
