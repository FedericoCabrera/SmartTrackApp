using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
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

            traject.LocationInitial.LocationTime = DateTime.Now;
            employee.Trajects.Add(traject);
            unitOfWork.EmployeeRepository.Update(employee);
            unitOfWork.EmployeeRepository.Save();

            return traject.Id;
        }

        public void EndTraject(Employee employee, Traject traject)  {
            var t = unitOfWork.TrajectRepository.Get(x => x.Id == traject.Id,null,"LocationInitial").FirstOrDefault();
            t.IsFinished = true;
            t.LocationFinal = traject.LocationFinal;
            t.LocationFinal.LocationTime = DateTime.Now;
            t.Distance = traject.Distance;
            var time = (t.LocationFinal.LocationTime - t.LocationInitial.LocationTime);
            t.Duration = time.TotalSeconds;
            unitOfWork.TrajectRepository.Update(t);
            unitOfWork.TrajectRepository.Save();

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
                        firebaseMessagingRestClient.SendNotification(admin.FirebaseDeviceToken, "Incidente reportado.", $"{employee.UserName} ha reportado un incidente a la hora {incident.CreationTime}");
                }
            }
        }

        public IEnumerable<IncidentReport> GetIncidentsReport(Administrator admin, DateTime dateFrom, DateTime dateTo)
        {
            var company = unitOfWork.CompanyRepository.Get(x => x.Id == admin.Company.Id, null, "Employees").FirstOrDefault();
            var incidentsList = new List<IncidentReport>();

            if(company != null)
            {
                foreach(Employee e in company.Employees)
                {
                    var employee = unitOfWork.EmployeeRepository.Get(x => x.Id == e.Id, null, "Trajects").FirstOrDefault();
                    if(employee != null)
                    {
                        var trajectsIds = employee.Trajects.Select(x => x.Id).ToList();

                        var incidents = unitOfWork.IncidentRepository.Get(x => trajectsIds.Contains(x.Traject.Id) && x.CreationTime >= dateFrom && x.CreationTime <= dateTo, null, "Location").ToList();

                        foreach(var incident in incidents)
                        {
                            var incidentReport = new IncidentReport()
                            {
                                Address = incident.Location!=null? incident.Location.Address:"",
                                Base64Image = incident.Base64Image,
                                Date = incident.CreationTime.ToString(),
                                Description = incident.Description,
                                UserName = e.UserName
                            };

                            incidentsList.Add(incidentReport);
                        }
                    }                    
                }
            }

            return incidentsList;
        }

        public TrajectReport GetTrajectsReport(Administrator admin, DateTime dateFrom, DateTime dateTo)
        {
            var company = unitOfWork.CompanyRepository.Get(x => x.Id == admin.Company.Id, null, "Employees").FirstOrDefault();
            var trajectReport = new TrajectReport();
            var trajectsList = new List<TrajectReportLine>();

            double totalDistance = 0;
            double totalDuration = 0;

            if (company != null)
            {
                foreach (Employee e in company.Employees)
                {
                    var employee = unitOfWork.EmployeeRepository.Get(x => x.Id == e.Id, null, "Trajects").FirstOrDefault();
                    if (employee != null)
                    {

                        List<Traject> trajects = employee.Trajects.Where(x => x.IsFinished && x.StartDate >= dateFrom && x.StartDate < dateTo.AddDays(1)).ToList();
                        foreach (Traject t in trajects)
                        {
                            TrajectReportLine line = new TrajectReportLine()
                            {
                                Distance = t.Distance,
                                Duration = t.Duration,
                                StartDate = t.StartDate.ToString(),
                                UserName = e.UserName
                            };

                            trajectsList.Add(line);
                            totalDistance += t.Distance;
                            totalDuration += t.Duration;

                        }
                        
                    }
                }
            }

            trajectReport.Lines = trajectsList.OrderBy(x => x.StartDate).ToList();
            trajectReport.TotalDistance = totalDistance;
            trajectReport.TotalDuration = totalDuration;

            return trajectReport;
        }
    }
}
