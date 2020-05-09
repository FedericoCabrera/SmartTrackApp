using System;

namespace ST.Data.Entities
{
    public class Location
    {
        public Location()
        {

            Id = Guid.NewGuid();
        }

        public Guid Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public DateTime LocationTime { get; set; }
    }
}
