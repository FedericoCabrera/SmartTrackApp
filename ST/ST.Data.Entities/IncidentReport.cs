using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class IncidentReport
    {
        public string Description { get; set; }
        public string Date { get; set; }
        public string Address { get; set; }
        public string UserName { get; set; }
        public string Base64Image { get; set; }
    }
}
