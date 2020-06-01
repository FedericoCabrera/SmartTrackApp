using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ST.BusinessLogic
{
    public class UserService : IUserService
    {
        public IUnitOfWork unitOfWork;

        public UserService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public void CreateUser(User user)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<User> GetAll()
        {
            throw new NotImplementedException();
        }

        public User GetUserById(Guid id)
        {
            throw new NotImplementedException();
        }

        public User GetUserByUserName(string userName)
        {
            var user = unitOfWork.UserRepository.Get(x => x.UserName == userName).FirstOrDefault();
            if (user == null)
                throw new HandledException("Datos de ingreso inválidos.");

            return user;
        }

        public List<Employee> GetUsersByCompany(Guid companyId)
        {
            throw new NotImplementedException();
        }

        public void ModifyUser(Guid userId, User newUser)
        {
            throw new NotImplementedException();
        }

        public void UpdateUserState(string usaername)
        {
            throw new NotImplementedException();
        }
    }
}
