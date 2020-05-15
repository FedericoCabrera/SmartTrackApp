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
            try
            {
                var admin = unitOfWork.AdministratorRepository.Get().Where(x => x.Id == id).FirstOrDefault();
                return admin;
            }

            catch (Exception ex)
            {

                throw new Exception();
            }
        }
    }
}
