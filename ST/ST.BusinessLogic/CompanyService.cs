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

        //PASSWORD_REGEX: The password must contain at least one upper case and one lower case character, it can contain a number
        public CompanyService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public IEnumerable<Company> GetAll() 
        {
            try
            {
                return unitOfWork.CompanyRepository.Get();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public Company GetCompanyByName(string companyName)
        {
            try
            {
                var Company = unitOfWork.CompanyRepository.Get(x => x.Name.Equals(companyName)).FirstOrDefault();
                return Company;
            }
            catch (Exception ex)
            {

                throw new Exception();
            }
        }

        public Company GetCompanyById(Guid id)
        {
            try
            {
                var Company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(id)).FirstOrDefault();
                return Company;
            }
            catch (Exception ex)
            {

                throw new Exception();
            }
        }

        public IEnumerable<Employee> GetAllEmployees(Guid companyId)
        {
            try
            {
                var company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
                return company.Employees;
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public void AddEmployee(Guid companyId, Employee employee)
        {
            try
            {
                Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId),null,"Employees").FirstOrDefault();
                company.Employees.Add(employee);
                unitOfWork.CompanyRepository.Update(company);
                unitOfWork.CompanyRepository.Save();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public void AddCompany(string name)
        {
            try
            {
                Company company = new Company();
                company.Name = name;
                unitOfWork.CompanyRepository.Create(company);
                unitOfWork.CompanyRepository.Save();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public void RemoveEmployee(Guid companyId, Guid employeeId)        
        {
            try
            {
                Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
                Employee employee = unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(employeeId)).FirstOrDefault();
                company.Employees.Remove(employee);
                unitOfWork.CompanyRepository.Update(company);
                unitOfWork.CompanyRepository.Save();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
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
