using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ST.Web.API.Controllers
{
    public static class Utils
    {
        public static Guid GetToken(HttpRequest request)
        {
            return new Guid(request.Headers["Authorization"]);
        }
    }
}
