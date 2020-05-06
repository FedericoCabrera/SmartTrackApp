using System;
using System.Collections.Generic;
using System.Linq;
using ST.BusinessLogic.Interfaces;
using ST.BusinessLogic.Interfaces.Exceptions;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;


namespace ST.BusinessLogic
{
    public class UserService : IUserService
    {
        private IUnitOfWork unitOfWork;

        //PASSWORD_REGEX: The password must contain at least one upper case and one lower case character, it can contain a number
        private static string PASSWORD_REGEX = "[A-Za-z0-9]*([A-Z]+[A-Za-z0-9]*[a-z]+[A-Za-z0-9]*)|[A-Za-z0-9]*([a-z]+[A-Za-z0-9]*[A-Z]+[A-Za-z0-9]*)"; 
        private static int MINIMUN_PASSWORD_LENGTH = 4;
        public UserService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public void CreateUser(User user)
        {
            ValidateStringProperty(user.IdentityNumber);
            ValidateStringProperty(user.LastName);
            ValidateStringProperty(user.Name);
            ValidateStringProperty(user.UserName);
            ValidateUserPassword(user.Password);
            ValidateUserName(user.UserName);

            unitOfWork.UserRepository.Create(user);
            unitOfWork.UserRepository.Save();
        }

        public IEnumerable<User> GetAll() 
        {
            try
            {
                return unitOfWork.UserRepository.Get();
            }
            catch (Exception ex)
            {
                throw new Exception();
            }
        }

        public void GetUserByUserName(string userName)
        {
            throw new NotImplementedException();
        }

        public void ModifyUser(Guid userId, User newUser)
        {
            throw new NotImplementedException();
        }

        private void ValidateUserName(string userName)
        {
            var user = unitOfWork.UserRepository.Get(x => x.UserName == userName).FirstOrDefault();
            if (user!=null)
                throw new InvalidUserNameException();
        }

        private void ValidateUserPassword(string password)
        {
            if (password.Length < MINIMUN_PASSWORD_LENGTH
              || !System.Text.RegularExpressions.Regex.IsMatch(password, PASSWORD_REGEX) || password.Contains(" "))
            {
                throw new InvalidPasswordException();
            }
        }

        private void ValidateStringProperty(string property)
        {
            if (string.IsNullOrEmpty(property))
                throw new RequiredPropertyNotFoundException();
        }


    }
}
