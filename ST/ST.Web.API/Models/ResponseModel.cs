using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class ResponseModel
    {
        public bool IsResponseOK { get; set; }
        public string ErrorMessage { get; set; }
    }
}
