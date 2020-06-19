using ST.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class TrajectModel : Model<Traject, TrajectModel>
    {
        public TrajectModel()
        {
        }

        public TrajectModel(Traject entity)
        {
            SetModel(entity);
        }

        public Guid Id { get; set; }
        public LocationModel LocationInitial { get; set; }
        public LocationModel LocationFinal { get; set; }
        public double Distance { get; set; }
        public double Duration { get; set; }
        public bool IsFinished { get; set; }
        public DateTime StartDate { get; set; }

        public override Traject ToEntity() => new Traject()
        {
            LocationFinal = this.LocationFinal!=null ? new Location()
            {
                Latitude = this.LocationFinal.Latitude,
                LocationTime = this.LocationFinal.LocationTime,
                Longitude = this.LocationFinal.Longitude
            } : null,
            LocationInitial = this.LocationInitial != null ? new Location()
            {
                Latitude = this.LocationInitial.Latitude,
                LocationTime = this.LocationInitial.LocationTime,
                Longitude = this.LocationInitial.Longitude
            } : null,
            Duration = this.Duration,
            Distance = this.Distance,
            StartDate = DateTime.Now,
            IsFinished = this.IsFinished
        };

        protected override TrajectModel SetModel(Traject entity)
        {
            LocationFinal = entity.LocationFinal != null ? new LocationModel()
            {
                Latitude = entity.LocationFinal.Latitude,
                LocationTime = entity.LocationFinal.LocationTime,
                Longitude = entity.LocationFinal.Longitude
            } : null;
            LocationInitial = entity.LocationInitial != null ? new LocationModel()
            {
                Latitude = entity.LocationFinal.Latitude,
                LocationTime = entity.LocationFinal.LocationTime,
                Longitude = entity.LocationFinal.Longitude
            } : null;
            Duration = entity.Duration;
            Distance = entity.Distance;
            StartDate = entity.StartDate;
            IsFinished = entity.IsFinished;

            return this;
        }
    }
}
