using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace ST.Web.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ValuesController : ControllerBase
    {
        public class Value
        {
            public string V1 { get; set; }
            public string V2 { get; set; }
        }
        // GET api/values
        [HttpGet]
        public ActionResult<IEnumerable<Value>> Get()
        {
            var v1 = new Value{ V1 = "1", V2 = "2" };
            var v2 = new Value { V1 = "4", V2 = "6" };
            List<Value> vals = new List<Value>() { v1, v2 };

            return  Ok(vals);
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<string> Get(int id)
        {
            return "value";
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
