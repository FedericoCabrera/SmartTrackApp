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
    public class CompanyTests
    {
        [TestMethod]
        public void TestCompanyAddOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company = CreateCompany("companyName");

            companyRepo.Create(company);
            companyRepo.Save();
            var Company = companyRepo.GetByID(company.Id);

            Assert.AreEqual(Company.Id, company.Id);
        }

        [TestMethod]
        public void TestCompanyAddOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company = CreateCompany("companyName");
            
            companyRepo.Create(company);
            companyRepo.Save();
            var Companys = companyRepo.Get().ToList();

            Assert.AreEqual(Companys.Count, 1);
        }

        [TestMethod]
        public void TestCompanyRemoveOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            companyRepo.Delete(company1);
            companyRepo.Save();

            var Companys = companyRepo.Get().ToList();

            Assert.AreEqual(Companys.First().Id, company2.Id);
        }

        [TestMethod]
        public void TestCompanyRemoveOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            companyRepo.Delete(company1);
            companyRepo.Save();

            var Companys = companyRepo.Get().ToList();

            Assert.AreEqual(Companys.Count, 1);
        }

        [TestMethod]
        public void TestCompanyUpdateOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            company1.Name = "newName";
            companyRepo.Update(company1);
            companyRepo.Save();

            var Company = companyRepo.GetByID(company1.Id);

            Assert.AreEqual(Company.Name, company1.Name);
        }

        [TestMethod]
        public void TestCompanyUpdateOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            company1.Name = "companyName3";
            companyRepo.Update(company1);
            companyRepo.Save();

            var Company = companyRepo.GetByID(company1.Id);

            Assert.AreEqual(Company.Name, company1.Name);
        }

        [TestMethod]
        public void TestCompanyGetOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            var Company = companyRepo.GetByID(company2.Id);

            Assert.AreEqual(Company.Name, company2.Name);
        }

        [TestMethod]
        public void TestCompanyGetOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");

            companyRepo.Create(company1);
            companyRepo.Save();

            var Company = companyRepo.GetByID(company1.Id);

            Assert.AreEqual(Company.Name, company1.Name);
        }

        [TestMethod]
        public void TestCompanyGetAllOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            var Companys = companyRepo.Get().ToList();

            Assert.AreEqual(Companys.Count, 2);
        }

        [TestMethod]
        public void TestCompanyGetAllOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Company> companyRepo = new Repository<Company>(context);

            var company1 = CreateCompany("companyName1");
            var company2 = CreateCompany("companyName2");

            companyRepo.Create(company1);
            companyRepo.Create(company2);

            companyRepo.Save();

            companyRepo.Delete(company1);
            companyRepo.Save();

            var Companys = companyRepo.Get().ToList();

            Assert.AreEqual(Companys.Count, 1);
        }


        public Company CreateCompany(String nameCompany) 
        {
            var company = new Company();
            company.Name = nameCompany;
            
            return company;
        }
    }
}