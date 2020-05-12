using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface IEmployeeService
    {
        IEnumerable<Employee> GetAll();
        void CreateEmployee(Employee employee);
        void ModifyEmployee(Guid employeeId, Employee newEmployee);
        Employee GetEmployeeByEmployeeName(string employeeName);
        Location GetEmployeeLocationByEmployeeName(string employeeName);
    }
}
