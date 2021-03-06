﻿using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface ITrajectService
    {
        Guid CreateTraject(Employee employee, Traject traject);
        void EndTraject(Employee employee, Traject traject);
        void AssignIncidentToTraject(Guid trajectId, Employee employeeId, Incident incident);
        IEnumerable<IncidentReport> GetIncidentsReport(Administrator admin, DateTime dateFrom, DateTime dateTo);
        TrajectReport GetTrajectsReport(Administrator admin, DateTime dateFrom, DateTime dateTo);
    }
}
