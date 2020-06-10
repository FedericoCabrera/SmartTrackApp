﻿using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;


namespace ST.BusinessLogic
{
    public class TrajectService : ITrajectService
    {
        private IUnitOfWork unitOfWork;
        private IFirebaseMessagingRestClient firebaseMessagingRestClient;

        public TrajectService(IUnitOfWork unitOfWork, IFirebaseMessagingRestClient firebaseMessagingRestClient)
        {
            this.unitOfWork = unitOfWork;
            this.firebaseMessagingRestClient = firebaseMessagingRestClient;
        }

        public Guid CreateTraject(Employee employee, Traject traject)
        {
            /*TODO: Delete this: */
            /*FirebaseMessagingClient.GetInstance().SendNotification("fYMVkLYUr3w:APA91bG48aa4bxstNOHt6gMgIIWFC8j9IXR71gyiTtd6hmUmmwDPllgpcDZAPo7iJeJ-Ko5Tagy7L_dJae1doDO_YrJbnP0-UJxGw1o7_-dygDbOkPkhDOjS2EdynnkrhFqjCgC3wNTP"
                , "Titulo prueba", "Prueba desde backend" + DateTime.Now.ToString());*/


            /****************************/

            employee.Trajects.Add(traject);
            unitOfWork.EmployeeRepository.Update(employee);
            unitOfWork.EmployeeRepository.Save();

            return traject.Id;
        }

        public void AssignIncidentToTraject(Guid trajectId, Employee employee, Incident incident)
        {

            Traject traject = unitOfWork.TrajectRepository.Get(x => x.Id == trajectId, null, "").FirstOrDefault();

            if (traject == null)
                throw new HandledException("Trayecto no existente.");

            incident.Traject = traject;

            unitOfWork.IncidentRepository.Create(incident);
            unitOfWork.IncidentRepository.Save();

            var company = unitOfWork.CompanyRepository.Get(x => x.Employees.Contains(employee), null, "Employees").FirstOrDefault();
            
            if(company != null)
            {
                var administrators = unitOfWork.AdministratorRepository.Get(x => x.Company.Id == company.Id, null, "").ToList();

                foreach (var admin in administrators)
                {
                    if(admin.FirebaseDeviceToken != null)
                        firebaseMessagingRestClient.SendNotification(admin.FirebaseDeviceToken, "Incidente reportado.", $"{employee.UserName} ha reportado un incidente a la hora {DateTime.Now.ToString()}");
                }
            }
        }

    }
}
