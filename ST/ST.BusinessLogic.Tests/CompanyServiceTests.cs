using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Tests
{
    [TestClass]
    public class CompanyLogicTest
    {
        /*IEnumerable<Employee> GetAllEmployees(Guid companyId);
        void AddEmployee(Guid companyId, Employee employee);
        void RemoveEmployee(Guid companyId, Guid employeeId);
        IEnumerable<Employee> GetEmployeesActiveWithLocation(Guid companyId);*/

        [TestMethod]
        public void AddCompanyValidCompanyTest()
        {
            var company = new Company();
            company.Name = "Name";
            List<Company> companys = new List<Company>();
            companys.Add(company);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.CompanyRepository.Create(It.IsAny<Company>()));
            mock.Setup(m => m.CompanyRepository.Save());
            mock.Setup(m => m.CompanyRepository.Get(null, null, null)).Returns(companys);
            var companyLogic = new ST.BusinessLogic.CompanyService(mock.Object);

            companyLogic.AddCompany(company.Name);
            var result = (Company)companyLogic.GetCompanyById(company.Id);
            mock.VerifyAll();
            Assert.AreEqual(result, company);
        }

        [TestMethod]
        public void GetAllTest()
        {
            var company = new Company();
            company.Name = "Name";
            List<Company> companys = new List<Company>();
            companys.Add(company);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.CompanyRepository.Create(It.IsAny<Company>()));
            mock.Setup(m => m.CompanyRepository.Save());
            mock.Setup(m => m.CompanyRepository.Get(null, null, null)).Returns(companys);
            var companyLogic = new ST.BusinessLogic.CompanyService(mock.Object);

            companyLogic.AddCompany(company.Name);
            var result = (List<Company>)companyLogic.GetAll();
            mock.VerifyAll();
            Assert.AreEqual(1, result.Count);
        }

        [TestMethod]
        public void GetCompanyByNameValidTest()
        {
            var company = new Company();
            company.Name = "Username";
            List<Company> companys = new List<Company>();
            companys.Add(company);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.CompanyRepository.Create(It.IsAny<Company>()));
            mock.Setup(m => m.CompanyRepository.Save());
            mock.Setup(m => m.CompanyRepository.Get(null, null, null)).Returns(companys);
            var companyLogic = new ST.BusinessLogic.CompanyService(mock.Object);

            companyLogic.AddCompany(company.Name);
            var result = (Company)companyLogic.GetCompanyByName(company.Name);
            mock.VerifyAll();
            Assert.AreEqual(company.Name, result.Name);
        }

        [TestMethod]
        [ExpectedException(typeof(HandledException))]
        public void GetCompanyByNameInvalidTest()
        {
            var company = new Company();
            company.Name = "Username";
            List<Company> companys = new List<Company>();
            companys.Add(company);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.CompanyRepository.Create(It.IsAny<Company>()));
            mock.Setup(m => m.CompanyRepository.Save());
            mock.Setup(m => m.CompanyRepository.Get(null, null, null)).Returns(companys);
            var companyLogic = new ST.BusinessLogic.CompanyService(mock.Object);

            companyLogic.AddCompany(company.Name);
            var result = (Company)companyLogic.GetCompanyByName("OtherUsername");
            mock.VerifyAll();
            Assert.AreEqual(company.Name, result.Name);
        }

    }
}