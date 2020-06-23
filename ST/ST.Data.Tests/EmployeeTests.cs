using Microsoft.VisualStudio.TestTools.UnitTesting;
using ST.Data.DataAccess;
using ST.Data.Entities;
using ST.Data.Repository;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ST.Data.Tests
{
    [TestClass]
    public class EmployeeTests
    {
        [TestMethod]
        public void TestEmployeeAddOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee = CreateEmployee("employeeName", "employeeLastName", "employeePassword", "employeeUsername", "11111111");

            employeeRepo.Create(employee);
            employeeRepo.Save();
            var Employee = employeeRepo.GetByID(employee.Id);

            Assert.AreEqual(Employee.Id, employee.Id);
        }

        [TestMethod]
        public void TestEmployeeAddOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee = CreateEmployee("employeeName", "employeeLastName", "employeePassword", "employeeUsername", "11111111");
            
            employeeRepo.Create(employee);
            employeeRepo.Save();
            var Employees = employeeRepo.Get().ToList();

            Assert.AreEqual(Employees.Count, 1);
        }

        [TestMethod]
        public void TestEmployeeRemoveOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            employeeRepo.Delete(employee1);
            employeeRepo.Save();

            var Employees = employeeRepo.Get().ToList();

            Assert.AreEqual(Employees.First().Id, employee2.Id);
        }

        [TestMethod]
        public void TestEmployeeRemoveOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            employeeRepo.Delete(employee1);
            employeeRepo.Save();

            var Employees = employeeRepo.Get().ToList();

            Assert.AreEqual(Employees.Count, 1);
        }

        [TestMethod]
        public void TestEmployeeUpdateOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            employee1.Name = "newName";
            employeeRepo.Update(employee1);
            employeeRepo.Save();

            var Employee = employeeRepo.GetByID(employee1.Id);

            Assert.AreEqual(Employee.Name, employee1.Name);
        }

        [TestMethod]
        public void TestEmployeeUpdateOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            employee1.IdentityNumber = "33333333";
            employeeRepo.Update(employee1);
            employeeRepo.Save();

            var Employee = employeeRepo.GetByID(employee1.Id);

            Assert.AreEqual(Employee.IdentityNumber, employee1.IdentityNumber);
        }

        [TestMethod]
        public void TestEmployeeGetOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            var Employee = employeeRepo.GetByID(employee2.Id);

            Assert.AreEqual(Employee.Name, employee2.Name);
        }

        [TestMethod]
        public void TestEmployeeGetOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");

            employeeRepo.Create(employee1);
            employeeRepo.Save();

            var Employee = employeeRepo.GetByID(employee1.Id);

            Assert.AreEqual(Employee.LastName, employee1.LastName);
        }

        [TestMethod]
        public void TestEmployeeGetAllOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            var Employees = employeeRepo.Get().ToList();

            Assert.AreEqual(Employees.Count, 2);
        }

        [TestMethod]
        public void TestEmployeeGetAllOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Employee> employeeRepo = new Repository<Employee>(context);

            var employee1 = CreateEmployee("employeeName1", "employeeLastName1", "employeePassword1", "employeeUsername1", "11111111");
            var employee2 = CreateEmployee("employeeName2", "employeeLastName2", "employeePassword2", "employeeUsername2", "22222222");

            employeeRepo.Create(employee1);
            employeeRepo.Create(employee2);

            employeeRepo.Save();

            employeeRepo.Delete(employee1);
            employeeRepo.Save();

            var Employees = employeeRepo.Get().ToList();

            Assert.AreEqual(Employees.Count, 1);
        }


        public Employee CreateEmployee(String nameEmployee, String lastnameEmployee, String password, String username, String identityNumber) 
        {
            var employee = new Employee();
            employee.Name = nameEmployee;
            employee.LastName = lastnameEmployee;
            employee.Password = password;
            employee.UserName = username;
            employee.IdentityNumber = identityNumber;
                        
            return employee;
        }
    }
}