using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class AdministratorModel : Model<Administrator, AdministratorModel>
    {
        public AdministratorModel() { }

        public AdministratorModel(Administrator entity)
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

        public string CompanyName { get; set; }

        public override Administrator ToEntity() => new Administrator()
        {
            Name = this.Name,
            IdentityNumber = this.IdentityNumber,
            LastName = this.LastName,
            Password = this.Password,
            UserName = this.UserName,
        };

        protected override AdministratorModel SetModel(Administrator entity)
        {
            Name = entity.Name;
            IdentityNumber = entity.IdentityNumber;
            LastName = entity.LastName;
            Password = entity.Password;
            UserName = entity.UserName;

            return this;
        }
    }
}
