using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;

namespace ST.BusinessLogic.Tests
{
    [TestClass]
    public class UserServiceTests
    {
        [TestMethod]
        public void CreateUserOKTest()
        {
            Mock<IUnitOfWork> unitOfWork = new Mock<IUnitOfWork>();
            unitOfWork
                .Setup(r => r.UserRepository.Get(It.IsAny<Expression<Func<User, bool>>>(), null, ""))
                .Returns(() => {
                return new List<User>();
            });
            unitOfWork.Setup(r => r.UserRepository.Create(It.IsAny<User>()));
            
            IUserService userService = new UserService(unitOfWork.Object);
            User user1 = new Employee() { 
                IdentityNumber = "1111111", 
                LastName = "Perez", 
                Name = "Jorge", 
                UserName = "JPerez", 
                Password = "Pass123456" };

            userService.CreateUser(user1);
            unitOfWork.VerifyAll();

        }

        [TestMethod]
        [ExpectedException(typeof(InvalidPasswordException))]
        public void CreateAdminInvalidPasswordTest()
        {
            Mock<IUnitOfWork> unitOfWork = new Mock<IUnitOfWork>();
            unitOfWork
                .Setup(r => r.UserRepository.Get(It.IsAny<Expression<Func<User, bool>>>(), null, ""))
                .Returns(() => {
                    return new List<User>();
                });
            unitOfWork.Setup(r => r.UserRepository.Create(It.IsAny<User>()));

            IUserService userService = new UserService(unitOfWork.Object);
            User user1 = new Administrator()
            {
                IdentityNumber = "1111111",
                LastName = "Perez",
                Name = "Jorge",
                UserName = "JPerez",
                Password = "pass123456"
            };

            userService.CreateUser(user1);
            unitOfWork.VerifyAll();

        }
    }
}
