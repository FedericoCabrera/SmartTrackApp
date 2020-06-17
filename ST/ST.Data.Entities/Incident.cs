using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class Incident
    {
        public Incident()
        {
            ID = new Guid();
        }
        public Guid ID { get; set; }
        public Location Location { get; set; }
        public DateTime CreationTime { get; set; }
        public string Base64Image { get; set; }
        public string Description { get; set; }
        public string Address { get; set; }
        public Traject Traject { get; set; }
    }
}
