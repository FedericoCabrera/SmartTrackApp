using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.Data.Entities
{
    public class Employee : User
    {
        public Employee()
        {
            Trajects = new List<Traject>();
        }
        public List<Traject> Trajects { get; set; }
        public Location Location { get; set; }
        public Status EmployeeStatus { get; set; }
        public enum Status { DISCONNECTED, CONNECTED, ON_A_TRIP }
    }
}
