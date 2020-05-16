using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class SessionModel
    {
        public Guid UserId { get; set; }
        public Guid Token { get; set; }
        public string Username { get; set; }
        public string Name { get; set; }
        public string Lastname { get; set; }

        public bool IsAdmin { get; set; }
    }
}
