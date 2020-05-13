using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;


namespace ST.BusinessLogic
{
    public class EmployeeService : IEmployeeService
    {
        private IUnitOfWork unitOfWork;

        //PASSWORD_REGEX: The password must contain at least one upper case and one lower case character, it can contain a number
        private static string PASSWORD_REGEX = "[A-Za-z0-9]*([A-Z]+[A-Za-z0-9]*[a-z]+[A-Za-z0-9]*)|[A-Za-z0-9]*([a-z]+[A-Za-z0-9]*[A-Z]+[A-Za-z0-9]*)"; 
        private static int MINIMUN_PASSWORD_LENGTH = 4;
        public EmployeeService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public void CreateEmployee(Employee employee)
        {
            ValidateStringProperty(employee.IdentityNumber);
            ValidateStringProperty(employee.LastName);
            ValidateStringProperty(employee.Name);
            ValidateStringProperty(employee.UserName);
            //ValidateUserPassword(employee.Password);
            //ValidateUserName(employee.UserName);

            unitOfWork.UserRepository.Create(employee);
            unitOfWork.UserRepository.Save();
        }

        public IEnumerable<Employee> GetAll() 
        {
            try
            {
                return unitOfWork.EmployeeRepository.Get();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public Employee GetEmployeeByUsername(string userName)
        {
            try
            {

                return unitOfWork.EmployeeRepository.Get(x => x.UserName.Equals(userName)).FirstOrDefault();
                 
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
        }

        public void ModifyEmployee(Guid employeeId, Employee newEmployee)
        {
            throw new NotImplementedException();
        }

        private void ValidateEmployeeName(string employeeName)
        {
            var employee = unitOfWork.EmployeeRepository.Get(x => x.Name == employeeName).FirstOrDefault();
            if (employee!=null)
                throw new InvalidUserNameException();
        }

        private void ValidateEmployeePassword(string password)
        {
            if (password.Length < MINIMUN_PASSWORD_LENGTH
              || !System.Text.RegularExpressions.Regex.IsMatch(password, PASSWORD_REGEX) || password.Contains(" "))
            {
                throw new InvalidPasswordException();
            }
        }

        private void ValidateStringProperty(string property)
        {
            if (string.IsNullOrEmpty(property))
                throw new RequiredPropertyNotFoundException();
        }

        public Location GetEmployeeLocationByEmployeeName(string employeeName)
        {
            throw new NotImplementedException();
        }

    }
}
