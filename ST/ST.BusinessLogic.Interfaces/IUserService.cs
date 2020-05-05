using ST.Data.Entities;
using System;

namespace ST.BusinessLogic.Interfaces
{
    public interface IUserService
    {
        void CreateUser(User user);
        void ModifyUser(Guid userId, User newUser);
        void GetUserByUserName(string userName);
    }
}
