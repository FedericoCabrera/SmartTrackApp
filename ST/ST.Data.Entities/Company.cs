using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.Data.Entities
{
    public class Company
    {
        public Company()
        {
            Id = Guid.NewGuid();
            Employees = new List<Employee>();
        }
        public Guid Id { get; set; }
        public string Name { get; set; }
        public List<Employee> Employees{ get; set; }
    }
}
