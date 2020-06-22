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
            ValidateStringProperty(employee.IdentityNumber);
            ValidateStringProperty(employee.LastName);
            ValidateStringProperty(employee.Name);
            ValidateStringProperty(employee.UserName);
            ValidateStringProperty(employee.Password);
            Location location = new Location();
            location.Latitude = 0;
            location.Longitude = 0;
            location.LocationTime = DateTime.Now;
            var dupEmployee = unitOfWork.EmployeeRepository.Get(x => x.UserName.Equals(employee.UserName)).FirstOrDefault();
            if (dupEmployee != null)
                throw new HandledException("Usuario ya existente.");
            Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId),null,"Employees").FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente.");
            employee.EmployeeStatus = Employee.Status.DISCONNECTED;
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

        private void ValidateEmployeeName(string employeeName)
        {
            var employee = unitOfWork.EmployeeRepository.Get(x => x.Name == employeeName).FirstOrDefault();
            if (employee == null)
                throw new HandledException("Datos de ingreso invalidos.");
        }



        private void ValidateStringProperty(string property)
        {
            if (string.IsNullOrEmpty(property))
                throw new HandledException("Datos de ingreso invalidos.");
        }

        public IEnumerable<Employee> GetEmployeesActiveWithLocation(Guid companyId)
        {

            Company company = unitOfWork.CompanyRepository.Get(x => x.Id.Equals(companyId), null, "Employees").FirstOrDefault();
            if (company == null)
                throw new HandledException("Empresa no existente");

            List<Employee> employees = new List<Employee>();
            foreach (Employee emp in company.Employees)
            {
                if(emp.EmployeeStatus.Equals(Employee.Status.CONNECTED) || emp.EmployeeStatus.Equals(Employee.Status.ON_A_TRIP))
                    employees.Add(unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(emp.Id), null, "Location").FirstOrDefault());
           
            }
            return employees;
        }
    }
}
