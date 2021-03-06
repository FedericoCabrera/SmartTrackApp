﻿using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata;
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

            Session session = new Session(userId);
            unitOfWork.SessionRepository.Create(session);
            unitOfWork.SessionRepository.Save();
            return session;

        }

        public IEnumerable<Session> GetAll()
        {
            return unitOfWork.SessionRepository.Get();
        }

        public User GetUserByToken(Guid token)
        {
            
            var session = unitOfWork.SessionRepository.Get(x => x.Token.Equals(token)).FirstOrDefault();
            if (session == null)
                throw new HandledException();

            var user = unitOfWork.UserRepository.Get(x => x.Id == session.UserId,null,"Company").FirstOrDefault();
            if (user == null)
                throw new HandledException();

            return user;

        }
        public Employee GetEmployeeByToken(Guid token)
        {
            try
            {
                var user = GetUserByToken(token);
                if (!(user is Employee))
                    throw new HandledException();

                return (Employee)user;
            }
            catch(HandledException he)
            {
                throw new HandledException("Usuario no encontrado.");
            }
            
        }

        public Administrator AuthorizeAdminByToken(Guid token)
        {
            try
            {
                var user = GetUserByToken(token);

                if (!(user is Administrator))
                    throw new HandledException("Usuario no autorizado.");

                return (Administrator)user;
            }
            catch(HandledException he)
            {
                throw new HandledException("Usuario no autorizado.");
            }
            
        }

        public bool IsAuthentication(Guid token)
        {
            bool isAuthentication = false;
            try
            {
                var session = GetUserByToken(token);
                isAuthentication = true;
            }
            catch(HandledException he)
            {
                isAuthentication = false;
            }
            
            return isAuthentication;

        }

        public Session Login(string username, string password)
        {

            var user = unitOfWork.UserRepository.Get(x => x.UserName.Equals(username)).FirstOrDefault();

            if (user == null)
                throw new HandledException("Datos de ingreso inválidos.");

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
            else
            {
                throw new HandledException("Datos de ingreso inválidos.");
            }
             
        }

        public Session Logout(string username)
        {

            var user = unitOfWork.UserRepository.Get(x => x.UserName.Equals(username)).FirstOrDefault();

            if (user == null)
                throw new HandledException("Datos de ingreso inválidos.");

            var session = unitOfWork.SessionRepository.Get(x => x.UserId.Equals(user.Id)).FirstOrDefault();
            if (session != null)
            {
                session.Token = Guid.Empty;
                session.Update(session);
                unitOfWork.SessionRepository.Update(session);
            }
            unitOfWork.SessionRepository.Save();
            return session;
        }
    }
}
