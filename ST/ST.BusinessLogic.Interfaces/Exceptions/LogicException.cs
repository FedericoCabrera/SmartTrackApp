using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class LogicException : Exception
    {
        public ResultCode.Code Code { get; set; }
    }
}
