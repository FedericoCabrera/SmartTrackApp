using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface ITrajectService
    {
        void CreateTraject(Employee employee, Traject traject);
        void AssignIncidentToTraject(Guid trajectId, Incident incident);

    }
}
