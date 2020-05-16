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
        Location GetEmployeeLocationByEmployeeName(string employeeName);
        Employee GetEmployeeByUsername(string userName);

        void ConnectedEmployee(string username);
    }
}
