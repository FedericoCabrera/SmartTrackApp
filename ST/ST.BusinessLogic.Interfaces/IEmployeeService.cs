﻿using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface IEmployeeService
    {
        IEnumerable<Employee> GetAll();
        void CreateEmployee(Employee employee);
        void ModifyEmployee(Employee employee);
        void RemoveEmployee(Employee employee);
        Location GetEmployeeLocationByEmployeeName(string employeeName);
        void ModifyLocation(Employee employee, Location location);
        Employee GetEmployeeByUsername(string userName);
<<<<<<< HEAD

        void ConnectedEmployee(string username);
=======
        Employee GetEmployeeById(Guid employeeId);
>>>>>>> 2bfd42915634cab45ff9328be0b155647ec4d6bb
    }
}
