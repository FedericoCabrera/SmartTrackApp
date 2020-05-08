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
        public SessionService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
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

                var session = unitOfWork.SessionRepository.Get(x => x.Token.Equals(token)).FirstOrDefault();
                return session;
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

        public Session Login(string username, string password)
        {
            try
            {
                var user = unitOfWork.UserRepository.Get(x => x.UserName.Equals(username)).FirstOrDefault();
                if (user.Password.Equals(password))
                {
                    var session = unitOfWork.SessionRepository.Get(x => x.UserId.Equals(user.Id)).FirstOrDefault();
                    if (session==null)
                    {
                        session = new Session(user.Id);
                        unitOfWork.SessionRepository.Create(session);
                    }
                    else 
                    {
                        session.Update(session);
                        unitOfWork.SessionRepository.Update(session);
                    }
                    unitOfWork.SessionRepository.Save();
                    return session;
                }
                throw new Exception();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }
    }
}
