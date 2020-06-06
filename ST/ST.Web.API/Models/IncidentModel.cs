using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class IncidentModel : Model<Incident, IncidentModel>
    {

        public Guid ID { get; set; }
        public LocationModel Location { get; set; }
        public DateTime CreationTime { get; set; }
        public string Base64Image { get; set; }
        public string Description { get; set; }
        //public TrajectModel Traject { get; set; }

        public override Incident ToEntity() => new Incident()
        {
            Location = new Location()
            {
                Latitude = this.Location.Latitude,
                LocationTime = this.Location.LocationTime,
                Longitude = this.Location.Longitude
            },
            Description = this.Description,
            Base64Image = this.Base64Image,
            CreationTime = this.CreationTime            
        };

        protected override IncidentModel SetModel(Incident entity)
        {
            Location = new LocationModel()
            {
                Latitude = entity.Location.Latitude,
                LocationTime = entity.Location.LocationTime,
                Longitude = entity.Location.Longitude
            };
            CreationTime = entity.CreationTime;
            Base64Image = entity.Base64Image;
            Description = entity.Description;

            return this;
        }
    }
}
