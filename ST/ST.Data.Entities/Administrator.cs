using System;

namespace ST.Data.Entities
{
    public class Administrator : User
    {
        public Administrator(){
            Id = Guid.NewGuid();
        }
        public string FirebaseDeviceToken { get; set; }
        public Company Company { get; set; }
    }
}
