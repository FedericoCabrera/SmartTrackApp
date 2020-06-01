using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Models
{
    public class ResponseModelWithData<T> : ResponseModel
    {
        public T Data { get; set; }
    }
}
