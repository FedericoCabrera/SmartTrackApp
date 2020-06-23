using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;

namespace ST.BusinessLogic.Tests
{
    [TestClass]
    public class AdministratorLogicTest
    {   
        [TestMethod]
        public void CreateValidAdministratorTest()
        {
            var administrator = new Administrator();
            administrator.IdentityNumber = "11111111";
            administrator.LastName = "LastName";
            administrator.Name = "Name";
            administrator.Password = "Password";
            administrator.UserName = "Username";
            List<Administrator> administrators = new List<Administrator>();
            administrators.Add(administrator);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.AdministratorRepository.Create(It.IsAny<Administrator>()));
            mock.Setup(m => m.AdministratorRepository.Save());
            mock.Setup(m => m.AdministratorRepository.Get(null, null, null)).Returns(administrators);
            var administratorLogic = new ST.BusinessLogic.AdministratorService(mock.Object);

            administratorLogic.CreateAdministrator(administrator);
            var result = (Administrator)administratorLogic.GetAdminById(administrator.Id);
            mock.VerifyAll();
            Assert.AreEqual(result, administrator);
        }

        [TestMethod]
        public void GetAdminByIdValidTest()
        {
            var administrator = new Administrator();
            List<Administrator> administrators = new List<Administrator>();
            administrators.Add(administrator);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.AdministratorRepository.Create(It.IsAny<Administrator>()));
            mock.Setup(m => m.AdministratorRepository.Save());
            mock.Setup(m => m.AdministratorRepository.Get(null, null, null)).Returns(administrators);
            var administratorLogic = new ST.BusinessLogic.AdministratorService(mock.Object);

            administratorLogic.CreateAdministrator(administrator);
            var result = (Administrator)administratorLogic.GetAdminById(administrator.Id);
            mock.VerifyAll();
            Assert.AreEqual(administrator.Id, result.Id);
        }

        [TestMethod]
        [ExpectedException(typeof(HandledException))]
        public void GetAdminByIdInvalidTest()
        {
            var administrator = new Administrator();
            List<Administrator> administrators = new List<Administrator>();
            administrators.Add(administrator);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.AdministratorRepository.Create(It.IsAny<Administrator>()));
            mock.Setup(m => m.AdministratorRepository.Save());
            mock.Setup(m => m.AdministratorRepository.Get(null, null, null)).Returns(administrators);
            var administratorLogic = new ST.BusinessLogic.AdministratorService(mock.Object);

            administratorLogic.CreateAdministrator(administrator);
            var result = (Administrator)administratorLogic.GetAdminById(Guid.NewGuid());
            mock.VerifyAll();
            Assert.AreEqual(administrator.Id, result.Id);
        }

        [TestMethod]
        public void GetAdministratorByUsernameValidTest()
        {
            var administrator = new Administrator();
            administrator.UserName = "Username";
            List<Administrator> administrators = new List<Administrator>();
            administrators.Add(administrator);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.AdministratorRepository.Create(It.IsAny<Administrator>()));
            mock.Setup(m => m.AdministratorRepository.Save());
            mock.Setup(m => m.AdministratorRepository.Get(null, null, null)).Returns(administrators);
            var administratorLogic = new ST.BusinessLogic.AdministratorService(mock.Object);

            administratorLogic.CreateAdministrator(administrator);
            var result = (Administrator)administratorLogic.GetAdministratorByUsername(administrator.UserName);
            mock.VerifyAll();
            Assert.AreEqual(administrator.UserName, result.UserName);
        }

        [TestMethod]
        [ExpectedException(typeof(HandledException))]
        public void GetAdministratorByUsernameInvalidTest()
        {
            var administrator = new Administrator();
            administrator.UserName = "Username";
            List<Administrator> administrators = new List<Administrator>();
            administrators.Add(administrator);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.AdministratorRepository.Create(It.IsAny<Administrator>()));
            mock.Setup(m => m.AdministratorRepository.Save());
            mock.Setup(m => m.AdministratorRepository.Get(null, null, null)).Returns(administrators);
            var administratorLogic = new ST.BusinessLogic.AdministratorService(mock.Object);

            administratorLogic.CreateAdministrator(administrator);
            var result = (Administrator)administratorLogic.GetAdministratorByUsername("OtherUsername");
            mock.VerifyAll();
            Assert.AreEqual(administrator.UserName, result.UserName);
        }

    }
}