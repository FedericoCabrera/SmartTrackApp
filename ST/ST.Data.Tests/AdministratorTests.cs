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
    public class AdministratorTests
    {
        [TestMethod]
        public void TestUserAddOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin = CreateAdmin("adminName", "adminLastName", "adminPassword", "adminUsername", "11111111", "CompanyName");

            adminRepo.Create(admin);
            adminRepo.Save();
            var administrator = adminRepo.GetByID(admin.Id);

            Assert.AreEqual(administrator.Id, admin.Id);
        }

        [TestMethod]
        public void TestUserAddOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin = CreateAdmin("adminName", "adminLastName", "adminPassword", "adminUsername", "11111111", "CompanyName");
            
            adminRepo.Create(admin);
            adminRepo.Save();
            var administrators = adminRepo.Get().ToList();

            Assert.AreEqual(administrators.Count, 1);
        }

        [TestMethod]
        public void TestUserRemoveOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            adminRepo.Delete(admin1);
            adminRepo.Save();

            var administrators = adminRepo.Get().ToList();

            Assert.AreEqual(administrators.First().Id, admin2.Id);
        }

        [TestMethod]
        public void TestUserRemoveOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            adminRepo.Delete(admin1);
            adminRepo.Save();

            var administrators = adminRepo.Get().ToList();

            Assert.AreEqual(administrators.Count, 1);
        }

        [TestMethod]
        public void TestUserUpdateOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            admin1.Name = "newName";
            adminRepo.Update(admin1);
            adminRepo.Save();

            var administrator = adminRepo.GetByID(admin1.Id);

            Assert.AreEqual(administrator.Name, admin1.Name);
        }

        [TestMethod]
        public void TestUserUpdateOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            admin1.IdentityNumber = "33333333";
            adminRepo.Update(admin1);
            adminRepo.Save();

            var administrator = adminRepo.GetByID(admin1.Id);

            Assert.AreEqual(administrator.IdentityNumber, admin1.IdentityNumber);
        }

        [TestMethod]
        public void TestUserGetOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            var administrator = adminRepo.GetByID(admin2.Id);

            Assert.AreEqual(administrator.Name, admin2.Name);
        }

        [TestMethod]
        public void TestUserGetOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");

            adminRepo.Create(admin1);
            adminRepo.Save();

            var administrator = adminRepo.GetByID(admin1.Id);

            Assert.AreEqual(administrator.LastName, admin1.LastName);
        }

        [TestMethod]
        public void TestUserGetAllOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            var administrators = adminRepo.Get().ToList();

            Assert.AreEqual(administrators.Count, 2);
        }

        [TestMethod]
        public void TestUserGetAllOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Administrator> adminRepo = new Repository<Administrator>(context);

            var admin1 = CreateAdmin("adminName1", "adminLastName1", "adminPassword1", "adminUsername1", "11111111", "CompanyName1");
            var admin2 = CreateAdmin("adminName2", "adminLastName2", "adminPassword2", "adminUsername2", "22222222", "CompanyName2");

            adminRepo.Create(admin1);
            adminRepo.Create(admin2);

            adminRepo.Save();

            adminRepo.Delete(admin1);
            adminRepo.Save();

            var administrators = adminRepo.Get().ToList();

            Assert.AreEqual(administrators.Count, 1);
        }


        public Administrator CreateAdmin(String nameAdmin, String lastnameAdmin, String password, String username, String identityNumber, String compName) 
        {
            var admin = new Administrator();
            var comp = new Company();
            comp.Name = compName;
            admin.Company = comp;
            admin.Name = nameAdmin;
            admin.LastName = lastnameAdmin;
            admin.Password = password;
            admin.UserName = username;
            admin.IdentityNumber = identityNumber;
            
            return admin;
        }
    }
}