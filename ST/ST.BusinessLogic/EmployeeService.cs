using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using static ST.Data.Entities.Employee;

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
            ValidateStringProperty(employee.Password);

            Location location = new Location();
            location.Latitude = 0;
            location.Longitude = 0;
            location.LocationTime = DateTime.Now;
            employee.EmployeeStatus = Employee.Status.DISCONNECTED;

            employee.Location = location;

            unitOfWork.EmployeeRepository.Create(employee);
            unitOfWork.EmployeeRepository.Save();
        }

        public IEnumerable<Employee> GetAll() 
        {

            return unitOfWork.EmployeeRepository.Get();
        }

        public void PutEmployeeStatus(Guid employeeId, Status employeeStatus) {
            var employee = GetEmployeeById(employeeId);
            if (employeeStatus.Equals(Status.CONNECTED))
                employee.EmployeeStatus = Status.CONNECTED;
            if (employeeStatus.Equals(Status.DISCONNECTED))
                employee.EmployeeStatus = Status.DISCONNECTED;
            if (employeeStatus.Equals(Status.ON_A_TRIP))
                employee.EmployeeStatus = Status.ON_A_TRIP;
            unitOfWork.EmployeeRepository.Update(employee);
            unitOfWork.EmployeeRepository.Save();


        }

        public Employee GetEmployeeByUsername(string userName)
        {
            var user = unitOfWork.EmployeeRepository.Get(x => x.UserName.Equals(userName)).FirstOrDefault();
            if (user == null)
                throw new HandledException("Usuario no existente.");

            return user;
        }

        public Employee GetEmployeeById(Guid employeeId)
        {

            return unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(employeeId),null,"Location").FirstOrDefault();

        }

        public void ModifyEmployee(Guid id, Employee newEmployee)
        {

            var employee = unitOfWork.EmployeeRepository.Get(x => x.Id.Equals(id)).FirstOrDefault();
            if (employee == null)
                throw new HandledException("No existe empleado a modificar.");

            ValidateStringProperty(newEmployee.IdentityNumber);
            ValidateStringProperty(newEmployee.LastName);
            ValidateStringProperty(newEmployee.Name);
            ValidateStringProperty(newEmployee.UserName);

            employee.Name = newEmployee.Name;
            employee.IdentityNumber = newEmployee.IdentityNumber;
            employee.LastName = newEmployee.LastName;
            employee.Password = newEmployee.Password;
            employee.UserName = newEmployee.UserName;

            unitOfWork.EmployeeRepository.Update(employee);
            unitOfWork.EmployeeRepository.Save();
        }


        public void RemoveEmployee(Guid employeeId)
        {
            var e = unitOfWork.EmployeeRepository.GetByID(employeeId);
            unitOfWork.EmployeeRepository.Delete(e);
            unitOfWork.EmployeeRepository.Save();

        }

        public void ConnectedEmployee(string username) 
        {
            var employee = GetEmployeeByUsername(username);
            employee.EmployeeStatus = Employee.Status.CONNECTED;
            unitOfWork.UserRepository.Update(employee);
            unitOfWork.UserRepository.Save();
        }

        private void ValidateEmployeeName(string employeeName)
        {
            var employee = unitOfWork.EmployeeRepository.Get(x => x.Name == employeeName).FirstOrDefault();
            if (employee==null)
                throw new HandledException("Datos de ingreso invalidos.");
        }

        private void ValidateEmployeePassword(string password)
        {
            if (password.Length < MINIMUN_PASSWORD_LENGTH
              || !System.Text.RegularExpressions.Regex.IsMatch(password, PASSWORD_REGEX) || password.Contains(" "))
            {
                throw new HandledException("Datos de ingreso invalidos.");
            }
        }

        private void ValidateStringProperty(string property)
        {
            if (string.IsNullOrEmpty(property))
                throw new HandledException("Datos de ingreso invalidos.");
        }

        public Location GetEmployeeLocationByEmployeeName(string employeeName)
        {
            throw new NotImplementedException();
        }

        public void ModifyLocation(Employee employee, Location location)
        {
            Location employeeLocation = employee.Location;
            employeeLocation.Latitude = location.Latitude;
            employeeLocation.Longitude = location.Longitude;
            employeeLocation.LocationTime = location.LocationTime;
            unitOfWork.LocationRepository.Update(employeeLocation);
            unitOfWork.LocationRepository.Save();
        }

    }
}
