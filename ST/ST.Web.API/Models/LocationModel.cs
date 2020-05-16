using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class LocationModel : Model<Location, LocationModel>
    {
        public LocationModel() { }

        public LocationModel(Location entity)
        {
            SetModel(entity);
        }

        public Guid Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public DateTime LocationTime { get; set; }

        public override Location ToEntity() => new Location()
        {
            Id = this.Id,
            Latitude = this.Latitude,
            Longitude = this.Longitude,
            LocationTime = this.LocationTime,
        };

        protected override LocationModel SetModel(Location entity)
        {
            Id = entity.Id;
            Latitude = entity.Latitude;
            Longitude = entity.Longitude;
            LocationTime = entity.LocationTime;

            return this;
        }
    }
}
