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

    }
}
