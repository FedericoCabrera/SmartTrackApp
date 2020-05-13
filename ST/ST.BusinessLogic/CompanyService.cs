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

        public IEnumerable<Employee> GetAllEmployees(Guid companyId)
        {
            throw new NotImplementedException();
        }

        public void AddEmployee(Guid companyId, Employee employee)
        {
            throw new NotImplementedException();
        }

        public void RemoveEmployee(Guid companyId, Guid employeeId)        
        {
            throw new NotImplementedException();
        }

    }
}
