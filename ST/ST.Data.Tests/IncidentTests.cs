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
    public class IncidentTests
    {
        [TestMethod]
        public void TestIncidentAddOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident = CreateIncident("image", "description");

            incidentRepo.Create(incident);
            incidentRepo.Save();
            var Incident = incidentRepo.GetByID(incident.ID);

            Assert.AreEqual(Incident.ID, incident.ID);
        }

        [TestMethod]
        public void TestIncidentAddOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident = CreateIncident("image", "description");
            
            incidentRepo.Create(incident);
            incidentRepo.Save();
            var Incidents = incidentRepo.Get().ToList();

            Assert.AreEqual(Incidents.Count, 1);
        }

        [TestMethod]
        public void TestIncidentRemoveOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            incidentRepo.Delete(incident1);
            incidentRepo.Save();

            var Incidents = incidentRepo.Get().ToList();

            Assert.AreEqual(Incidents.First().ID, incident2.ID);
        }

        [TestMethod]
        public void TestIncidentRemoveOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            incidentRepo.Delete(incident1);
            incidentRepo.Save();

            var Incidents = incidentRepo.Get().ToList();

            Assert.AreEqual(Incidents.Count, 1);
        }

        [TestMethod]
        public void TestIncidentUpdateOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            incident1.Description = "newDescription";
            incidentRepo.Update(incident1);
            incidentRepo.Save();

            var Incident = incidentRepo.GetByID(incident1.ID);

            Assert.AreEqual(Incident.Description, incident1.Description);
        }

        [TestMethod]
        public void TestIncidentUpdateOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            incident1.Base64Image = "newImage";
            incidentRepo.Update(incident1);
            incidentRepo.Save();

            var Incident = incidentRepo.GetByID(incident1.ID);

            Assert.AreEqual(Incident.Base64Image, incident1.Base64Image);
        }

        [TestMethod]
        public void TestIncidentGetOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            var Incident = incidentRepo.GetByID(incident2.ID);

            Assert.AreEqual(Incident.Description, incident2.Description);
        }

        [TestMethod]
        public void TestIncidentGetOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");

            incidentRepo.Create(incident1);
            incidentRepo.Save();

            var Incident = incidentRepo.GetByID(incident1.ID);

            Assert.AreEqual(Incident.CreationTime, incident1.CreationTime);
        }

        [TestMethod]
        public void TestIncidentGetAllOK()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            var Incidents = incidentRepo.Get().ToList();

            Assert.AreEqual(Incidents.Count, 2);
        }

        [TestMethod]
        public void TestIncidentGetAllOK2()
        {
            var context = ContextFactory.GetMemoryContext(Guid.NewGuid().ToString());
            IRepository<Incident> incidentRepo = new Repository<Incident>(context);

            var incident1 = CreateIncident("image1", "description1");
            var incident2 = CreateIncident("image2", "description2");

            incidentRepo.Create(incident1);
            incidentRepo.Create(incident2);

            incidentRepo.Save();

            incidentRepo.Delete(incident1);
            incidentRepo.Save();

            var Incidents = incidentRepo.Get().ToList();

            Assert.AreEqual(Incidents.Count, 1);
        }


        public Incident CreateIncident(String Base64Image, String description) 
        {
            var location = new Location();
            location.Latitude = 1;
            location.Longitude = 1;
            location.LocationTime = DateTime.Now;
            var traject = new Traject();
            traject.Distance = 1;
            traject.Duration = 1;
            traject.IsFinished = true;
            traject.LocationFinal = location;
            traject.LocationInitial = location;
            traject.StartDate = DateTime.Now;

            location.Address = "Montevideo";
            var incident = new Incident();
            incident.Location = location;
            incident.CreationTime = DateTime.Now;
            incident.Base64Image = Base64Image;
            incident.Description = description;
            incident.Traject = traject;

            return incident;
        }
    }
}