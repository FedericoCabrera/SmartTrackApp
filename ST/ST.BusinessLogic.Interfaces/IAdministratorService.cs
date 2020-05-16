using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface IAdministratorService
    {

        Administrator GetAdminById(Guid id);
        void CreateAdministrator(Administrator administrator);

        Administrator GetAdministratorByUsername(string userName);
    } 
}
