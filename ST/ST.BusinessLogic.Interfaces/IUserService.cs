using ST.Data.Entities;
using System;
using System.Collections.Generic;

namespace ST.BusinessLogic.Interfaces
{
    public interface IUserService
    {

        IEnumerable<User> GetAll();
        void CreateUser(User user);
        void ModifyUser(Guid userId, User newUser);
        void GetUserByUserName(string userName);
    }
}
