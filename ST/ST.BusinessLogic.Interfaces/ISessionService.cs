using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces
{
    public interface ISessionService
    {
        Session Create(Guid userId);
        Session Login (string username, string password);
        Session GetUserByToken(Guid token);
        bool IsAuthentication(Guid token);
        IEnumerable<Session> GetAll();
    }
}
