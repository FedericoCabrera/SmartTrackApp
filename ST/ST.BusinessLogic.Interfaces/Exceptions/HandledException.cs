using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class HandledException : Exception
    {
        public HandledException() : base()
        {}
        public HandledException(string message) : base(message)
        {}
    }
}
