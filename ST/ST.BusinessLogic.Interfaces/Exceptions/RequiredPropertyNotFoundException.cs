using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class RequiredPropertyNotFoundException : LogicException
    {
        public RequiredPropertyNotFoundException()
        {
            this.Code = ResultCode.Code.RequiredPropertyNotFound;
        }
    }
}
