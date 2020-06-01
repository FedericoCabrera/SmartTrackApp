using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;


namespace ST.BusinessLogic
{
    public class CompanyService : ICompanyService
    {
        private IUnitOfWork unitOfWork;

        public CompanyService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public IEnumerable<Company> GetAll() 
        {
            return unitOfWork.CompanyRepository.Get();
        }

        public Company GetCompanyByName(string companyName)
        {
            var company = unitOfWork.CompanyRepository.Get(x => x.Name.Equals(companyName)).FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");

            return company;
        }

        public Company GetCompanyById(Guid id)
        {
            var company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(id)).FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");

            return company;
        }

        public IEnumerable<Employee> GetAllEmployees(Guid companyId)
        {
            var company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");

            return company.Employees;
        }

        public void AddEmployee(Guid companyId, Employee employee)
        {

            Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId),null,"Employees").FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");

            company.Employees.Add(employee);
            unitOfWork.CompanyRepository.Update(company);
            unitOfWork.CompanyRepository.Save();

        }

        public void AddCompany(string name)
        {
            Company company = new Company();
            company.Name = name;
            unitOfWork.CompanyRepository.Create(company);
            unitOfWork.CompanyRepository.Save();
        }

        public void RemoveEmployee(Guid companyId, Guid employeeId)        
        {

            Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");

            Employee employee = unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(employeeId)).FirstOrDefault();
            if (employee == null)
                throw new HandledException("Empleado no existente.");

            company.Employees.Remove(employee);
            unitOfWork.CompanyRepository.Update(company);
            unitOfWork.CompanyRepository.Save();

        }

        public IEnumerable<Employee> GetEmployeesActiveWithLocation(Guid companyId)
        {
            try
            {
                Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
                List<Employee> employees = new List<Employee>();
                foreach (Employee emp in company.Employees)
                {
                    employees.Add(unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(emp.Id) && x.EmployeeStatus.Equals(Employee.Status.CONNECTED), null, "Location").FirstOrDefault());
                }
                return employees;
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }
    }
}
