using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class Traject
    {
        public Traject()
        {
            Id = Guid.NewGuid();
        }
        public Guid Id { get; set; }
        public Location LocationInitial { get; set; }
        public Location LocationFinal { get; set; }
        public double Distance { get; set; }
        public double Duration { get; set; }
        public bool IsFinished { get; set; }
        public DateTime StartDate { get; set; }
    }
}
