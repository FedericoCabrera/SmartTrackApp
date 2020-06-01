using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class UnhandledException : Exception
    {
        public UnhandledException(string message) : base(message)
        {
        }
    }
}
