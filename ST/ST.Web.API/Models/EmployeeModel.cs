using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using static ST.Data.Entities.Employee;

namespace ST.Web.API.Models
{
    public class EmployeeModel : Model<Employee, EmployeeModel>
    {
        public EmployeeModel() { }

        public EmployeeModel(Employee entity)
        {
            SetModel(entity);
        }

        public Guid Id { get; set; }
        public string Name { get; set; }
        public string LastName { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public string IdentityNumber { get; set; }
        public bool IsAdmin { get; set; }
        public string EmployeeStatus { get; set; }
        public Location Location { get; set; }

        public override Employee ToEntity() => new Employee()
        {
            Id = this.Id,
            Name = this.Name,
            IdentityNumber = this.IdentityNumber,
            LastName = this.LastName,
            Password = this.Password,
            UserName = this.UserName,
        };

        protected override EmployeeModel SetModel(Employee entity)
        {
            Id = entity.Id;
            Name = entity.Name;
            IdentityNumber = entity.IdentityNumber;
            LastName = entity.LastName;
            Password = entity.Password;
            UserName = entity.UserName;
            EmployeeStatus = entity.EmployeeStatus.ToString();
            Location = entity.Location;
            return this;
        }

        public static List<EmployeeModel> ToEntity(List<Employee> employess) 
        {
            List<EmployeeModel> employeesModel = new List<EmployeeModel>();
            foreach (Employee e in employess)
            {
                employeesModel.Add(EmployeeModel.ToModel(e));
            }
            return employeesModel;
        }
    }
}
