using ST.Data.Entities;
using System;
using System.Collections.Generic;
using static ST.Data.Entities.Employee;

namespace ST.BusinessLogic.Interfaces
{
    public interface IEmployeeService
    {
        IEnumerable<Employee> GetAll();
        void CreateEmployee(Employee employee);
        void ModifyEmployee(Guid employeeId, Employee employee);
        void RemoveEmployee(Guid employeeId);
        Location GetEmployeeLocationByEmployeeName(string employeeName);
        void ModifyLocation(Employee employee, Location location);
        Employee GetEmployeeByUsername(string userName);
        void ConnectedEmployee(string username);
        Employee GetEmployeeById(Guid employeeId);
        void PutEmployeeStatus(Guid employeeId, Status employeeStatus);

    }
}