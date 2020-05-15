using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface ICompanyService
    {
        IEnumerable<Company> GetAll();
        IEnumerable<Employee> GetAllEmployees(Guid companyId);
        void AddEmployee(Guid companyId, Employee employee);
        void RemoveEmployee(Guid companyId, Guid employeeId);
        void AddCompany(string name);
        Company GetCompanyById(Guid id);
        Company GetCompanyByName(string companyName);
    }
}
