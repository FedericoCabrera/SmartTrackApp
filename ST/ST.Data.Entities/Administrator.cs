using System;

namespace ST.Data.Entities
{
    public class Administrator : User
    {
        public Administrator(){
            Id = Guid.NewGuid();
        }

        public Company Company { get; set; }
    }
}
