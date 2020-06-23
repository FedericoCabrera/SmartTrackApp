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
    public class SessionLogicTest
    {
        [TestMethod]
        public void CreateValidSessionTest()
        {
            var userId = Guid.NewGuid();
            var session = new Session(userId);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Create(It.IsAny<Session>()));
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.Get(null, null, null)).Returns(sessions);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            sessionLogic.Create(userId);
            var result = ((List<Session>)sessionLogic.GetAll()).First();
            mock.VerifyAll();
            Assert.AreEqual(result.UserId, userId);
        }

        [TestMethod]
        public void LoginSessionTest()
        {
            var user = new Employee();
            var session = new Session(user.Id);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.GetByID(user.Id)).Returns(session);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            var result = sessionLogic.Login("userName", "password");
            mock.VerifyAll();
        }

        [TestMethod]
        [ExpectedException(typeof(HandledException))]
        public void LoginIncorrectSessionTest()
        {
            var user = new Employee();
            var session = new Session(user.Id);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            var result = sessionLogic.Login("userName", "fail");
            mock.VerifyAll();
        }

        [TestMethod]
        public void GetTokenSessionTest()
        {
            var userId = Guid.NewGuid();
            var session = new Session(userId);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Create(It.IsAny<Session>()));
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.Get(null, null, null)).Returns(sessions);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            sessionLogic.Create(userId);
            var result = ((List<Session>)sessionLogic.GetAll()).First();
            mock.VerifyAll();
            Assert.AreEqual(session.Token, result.Token);
        }

        [TestMethod]
        public void IsAuthenticationSessionTest()
        {
            var userId = Guid.NewGuid();
            var session = new Session(userId);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Create(It.IsAny<Session>()));
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.Get(null, null, null)).Returns(sessions);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            sessionLogic.Create(userId);
            sessionLogic.IsAuthentication(session.Token);

            mock.VerifyAll();
        }

        [TestMethod]
        [ExpectedException(typeof(HandledException))]
        public void NotAuthenticationSessionTest()
        {
            var userId = Guid.NewGuid();
            var session = new Session(userId);
            List<Session> sessions = new List<Session>();
            sessions.Add(session);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Create(It.IsAny<Session>()));
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.Get(null, null, null)).Returns(sessions);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            sessionLogic.Create(userId);
            sessionLogic.IsAuthentication(Guid.NewGuid());

            mock.VerifyAll();
        }

        [TestMethod]
        public void GetAllSessionTest()
        {
            var userId = Guid.NewGuid();
            var session_1 = new Session(userId);
            var session_2 = new Session(userId);
            List<Session> sessions = new List<Session>();
            sessions.Add(session_1);
            sessions.Add(session_2);

            var mock = new Mock<IUnitOfWork>(MockBehavior.Strict);
            mock.Setup(m => m.SessionRepository.Create(It.IsAny<Session>()));
            mock.Setup(m => m.SessionRepository.Save());
            mock.Setup(m => m.SessionRepository.Get(null, null, null)).Returns(sessions);
            var sessionLogic = new ST.BusinessLogic.SessionService(mock.Object);

            sessionLogic.Create(userId);
            var result = (List<Session>)sessionLogic.GetAll();
            mock.VerifyAll();
            Assert.AreEqual(result.Count, 2);
        }
    }
}