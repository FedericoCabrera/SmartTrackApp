using System;
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

        public TrajectService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public void CreateTraject(Employee employee, Traject traject)
        {
            employee.Trajects.Add(traject);
            unitOfWork.EmployeeRepository.Update(employee);
            unitOfWork.EmployeeRepository.Save();
        }

        public void AssignIncidentToTraject(Guid trajectId, Incident incident)
        {
            Traject traject = unitOfWork.TrajectRepository.Get(x => x.Id == trajectId, null, null).FirstOrDefault();

            if (traject == null)
                throw new HandledException("Trayecto no existente.");

            incident.Traject = traject;

            unitOfWork.IncidentRepository.Create(incident);
            unitOfWork.IncidentRepository.Save();
        }
    }
}
