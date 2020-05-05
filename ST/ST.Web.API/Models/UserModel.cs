using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class UserModel : Model<User, UserModel>
    {
        public UserModel() { }

        public UserModel(User entity)
        {
            SetModel(entity);
        }

        public Guid Id { get; set; }
        public string Name { get; set; }
        public string LastName { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
        public string IdentityNumber { get; set; }
        public bool IsAdmin { get; set; }

        public override User ToEntity() => new User()
        {
            Name = this.Name,
            IdentityNumber = this.IdentityNumber,
            IsAdmin = this.IsAdmin,
            LastName = this.LastName,
            Password = this.Password,
            UserName = this.UserName
        };

        protected override UserModel SetModel(User entity)
        {
            Name = entity.Name;
            IdentityNumber = entity.IdentityNumber;
            IsAdmin = entity.IsAdmin;
            LastName = entity.LastName;
            Password = entity.Password;
            UserName = entity.UserName;

            return this;
        }
    }
}
