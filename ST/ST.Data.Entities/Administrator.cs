using System;

namespace ST.Data.Entities
{
    public class Administrator : User
    {
        public Administrator(){}

        public Company Company { get; set; }
    }
}
