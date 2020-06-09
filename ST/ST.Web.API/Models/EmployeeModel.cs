using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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

        public override Employee ToEntity() => new Employee()
        {
            Name = this.Name,
            IdentityNumber = this.IdentityNumber,
            LastName = this.LastName,
            Password = this.Password,
            UserName = this.UserName,
        };

        protected override EmployeeModel SetModel(Employee entity)
        {
            Name = entity.Name;
            IdentityNumber = entity.IdentityNumber;
            LastName = entity.LastName;
            Password = entity.Password;
            UserName = entity.UserName;

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
