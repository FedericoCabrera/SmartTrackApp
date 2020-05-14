using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class CompanyModel : Model<Company, CompanyModel>
    {
        public CompanyModel() { }

        public CompanyModel(Company entity)
        {
            SetModel(entity);
        }

        public Guid Id { get; set; }
        public string Name { get; set; }


        public override Company ToEntity() => new Company()
        {
            Name = this.Name,
        };

        protected override CompanyModel SetModel(Company entity)
        {
            Name = entity.Name;
            return this;
        }
    }
}
