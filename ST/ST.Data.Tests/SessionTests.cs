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
    public class SessionTests
    {
        [TestMethod]
        public void TestSessionAddOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session = CreateSession(new Employee());

            sessionRepo.Create(session);
            sessionRepo.Save();
            var Session = sessionRepo.GetByID(session.Id);

            Assert.AreEqual(Session.Id, session.Id);
        }

        [TestMethod]
        public void TestSessionAddOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session = CreateSession(new Employee());
            
            sessionRepo.Create(session);
            sessionRepo.Save();
            var Sessions = sessionRepo.Get().ToList();

            Assert.AreEqual(Sessions.Count, 1);
        }

        [TestMethod]
        public void TestSessionRemoveOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            sessionRepo.Delete(session1);
            sessionRepo.Save();

            var Sessions = sessionRepo.Get().ToList();

            Assert.AreEqual(Sessions.First().Id, session2.Id);
        }

        [TestMethod]
        public void TestSessionRemoveOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            sessionRepo.Delete(session1);
            sessionRepo.Save();

            var Sessions = sessionRepo.Get().ToList();

            Assert.AreEqual(Sessions.Count, 1);
        }

        [TestMethod]
        public void TestSessionUpdateOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            session1.LastSession = DateTime.Now;
            sessionRepo.Update(session1);
            sessionRepo.Save();

            var Session = sessionRepo.GetByID(session1.Id);

            Assert.AreEqual(Session.LastSession, session1.LastSession);
        }

        [TestMethod]
        public void TestSessionUpdateOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            session1.Token = Guid.NewGuid();
            sessionRepo.Update(session1);
            sessionRepo.Save();

            var Session = sessionRepo.GetByID(session1.Id);

            Assert.AreEqual(Session.Token, session1.Token);
        }

        [TestMethod]
        public void TestSessionGetOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            var Session = sessionRepo.GetByID(session2.Id);

            Assert.AreEqual(Session.LastSession, session2.LastSession);
        }

        [TestMethod]
        public void TestSessionGetOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());

            sessionRepo.Create(session1);
            sessionRepo.Save();

            var Session = sessionRepo.GetByID(session1.Id);

            Assert.AreEqual(Session.Token, session1.Token);
        }

        [TestMethod]
        public void TestSessionGetAllOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            var Sessions = sessionRepo.Get().ToList();

            Assert.AreEqual(Sessions.Count, 2);
        }

        [TestMethod]
        public void TestSessionGetAllOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Session> sessionRepo = new Repository<Session>(context);

            var session1 = CreateSession(new Employee());
            var session2 = CreateSession(new Administrator());

            sessionRepo.Create(session1);
            sessionRepo.Create(session2);

            sessionRepo.Save();

            sessionRepo.Delete(session1);
            sessionRepo.Save();

            var Sessions = sessionRepo.Get().ToList();

            Assert.AreEqual(Sessions.Count, 1);
        }


        public Session CreateSession(User user) 
        {
            var session = new Session(user.Id);
                        
            return session;
        }
    }
}