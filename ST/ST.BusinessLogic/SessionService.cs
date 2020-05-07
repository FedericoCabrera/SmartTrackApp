using ST.BusinessLogic.Interfaces;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ST.BusinessLogic
{
    public class SessionService : ISessionService
    {
        private IUnitOfWork unitOfWork;
        private IUserService userService;
        public SessionService(IUnitOfWork unitOfWork, UserService userService)
        {
            this.unitOfWork = unitOfWork;
            this.userService = userService;
        }

        public Session Create(Guid userId)
        {


            try
            {
                Session session = new Session(userId);
                unitOfWork.SessionRepository.Create(session);
                unitOfWork.SessionRepository.Save();
                return session;
            }
            catch (Exception ex)
            {
                throw new Exception("error la crear sesion");
            }
        }

        public IEnumerable<Session> GetAll()
        {
            try
            {
                return unitOfWork.SessionRepository.Get();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public Session GetUserByToken(Guid token)
        {
            try
            {

                var session = unitOfWork.SessionRepository.Get(x => x.Token.Equals(token)).ToList();
                return session.First();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public bool IsAuthentication(Guid token)
        {
            bool isAuthentication = false;
            try
            {
                var session = GetUserByToken(token);
                return isAuthentication;
            }
            catch
            {
                throw new Exception();
            }
        }

    }
}
