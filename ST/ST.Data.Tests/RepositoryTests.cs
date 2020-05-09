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
    public class RepositoryTests
    {
        [TestMethod]
        public void TestAdministratorAddOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<User> userRepository = new Repository<User>(context);

            User user = new Administrator() {IdentityNumber="1111111", LastName = "Perez", Name = "Jorge", UserName = "JPerez", Password = "pass123456" };

            var idUsed = user.Id;

            userRepository.Create(user);
            userRepository.Save();

            var employees = userRepository.Get(x => x.Id == idUsed).ToList();

            Assert.AreEqual(employees[0].Id, idUsed);
        }

        [TestMethod]
        public void TestTrajectAddOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Traject> trajectRepository = new Repository<Traject>(context);

            Traject traject = new Traject()
            {
                Distance = 1,
                Duration = 1,
                StartDate = DateTime.Now,
                IsFinished = false,
                LocationInitial = new Location() { Latitude = 1000, Longitude = 1000, LocationTime = DateTime.Now },
                LocationFinal = new Location() { Latitude = 1000, Longitude = 1000, LocationTime = DateTime.Now }
            };

            var trajectIdUsed = traject.Id;

            trajectRepository.Create(traject);
            trajectRepository.Save();

            var trajects = trajectRepository.Get(x => x.Id == traject.Id,null,"User").ToList();

            Assert.AreEqual(trajects[0].Id, trajectIdUsed);
        }

        [TestMethod]
        public void TestAddRangeOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<User> userRepository = new Repository<User>(context);

            User user1 = new Employee() { IdentityNumber = "1111111", LastName = "Perez", Name = "Jorge", UserName = "JPerez", Password = "pass123456" };
            User user2 = new Employee() { IdentityNumber = "1111112", LastName = "Perez", Name = "Pedro", UserName = "PePerez", Password = "pass123456" };
            User user3 = new Employee() { IdentityNumber = "1111113", LastName = "Perez", Name = "Pepe", UserName = "PPerez", Password = "pass123456" };

            List<User> users = new List<User>() { user1, user2, user3 };

            userRepository.CreateRange(users);
            userRepository.Save();

            var u1 = userRepository.Get(x => x.IdentityNumber == "1111111").ToList();
            var u2 = userRepository.Get(x => x.IdentityNumber == "1111112").ToList();
            var u3 = userRepository.Get(x => x.IdentityNumber == "1111113").ToList();

            Assert.AreEqual(u1[0].IdentityNumber, "1111111");
            Assert.AreEqual(u2[0].IdentityNumber, "1111112");
            Assert.AreEqual(u3[0].IdentityNumber, "1111113");
        }

        [TestMethod]
        public void TestDeleteOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<User> userRepository = new Repository<User>(context);

            User user1 = new Employee() { IdentityNumber = "1111111", LastName = "Perez", Name = "Jorge", UserName = "JPerez", Password = "pass123456" };
            User user2 = new Employee() { IdentityNumber = "1111112", LastName = "Perez", Name = "Pedro", UserName = "PePerez", Password = "pass123456" };
            User user3 = new Employee() { IdentityNumber = "1111113", LastName = "Perez", Name = "Pepe", UserName = "PPerez", Password = "pass123456" };

            List<User> users = new List<User>() { user1, user2, user3 };

            userRepository.CreateRange(users);
            userRepository.Save();

            userRepository.Delete(user1);
            userRepository.Save();

            var u1 = userRepository.Get(x => x.Id == user1.Id).FirstOrDefault();

            Assert.AreEqual(u1, null);
        }

        [TestMethod]
        public void TestAttachOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<User> userRepository = new Repository<User>(context);

            User user1 = new Employee() { IdentityNumber = "1111111", LastName = "Perez", Name = "Jorge", UserName = "JPerez", Password = "pass123456" };

            userRepository.Create(user1);
            userRepository.Save();

            var u1 = userRepository.GetByID(user1.Id);

            u1.LastName = "Gonzalez";

            userRepository.Attach(user1);
            userRepository.Save();

            var u2 = userRepository.GetByID(user1.Id);

            Assert.AreEqual(u2.LastName, "Gonzalez");
        }

        [TestMethod]
        public void TestUpdateOk()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<User> userRepository = new Repository<User>(context);

            User user1 = new Employee() { IdentityNumber = "1111111", LastName = "Perez", Name = "Jorge", UserName = "JPerez", Password = "pass123456" };

            userRepository.Create(user1);
            userRepository.Save();

            var u1 = userRepository.GetByID(user1.Id);

            u1.LastName = "Gonzalez";

            userRepository.Update(user1);
            userRepository.Save();

            var u2 = userRepository.GetByID(user1.Id);

            Assert.AreEqual(u2.LastName, "Gonzalez");
        }
    }
}
