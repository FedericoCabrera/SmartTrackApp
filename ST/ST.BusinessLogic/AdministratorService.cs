using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;


namespace ST.BusinessLogic
{
    public class AdministratorService : IAdministratorService
    {
        private IUnitOfWork unitOfWork;

        public AdministratorService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public void CreateAdministrator(Administrator administrator)
        {
            unitOfWork.AdministratorRepository.Create(administrator);
            unitOfWork.AdministratorRepository.Save();
        }

        public Administrator GetAdminById(Guid id)
        {

            var admin = unitOfWork.AdministratorRepository.Get(x => x.Id == id,null,"Company").FirstOrDefault();
            if (admin == null)
                throw new HandledException("Administrador no existente.");

            return admin;

        }

        public Administrator GetAdministratorByUsername(string userName)
        {
            return unitOfWork.AdministratorRepository.Get(x => x.UserName.Equals(userName)).FirstOrDefault();
        }
    }
}
