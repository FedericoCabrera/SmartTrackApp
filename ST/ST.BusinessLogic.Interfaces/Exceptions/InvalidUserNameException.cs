using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class InvalidUserNameException : LogicException
    {
        public InvalidUserNameException()
        {
            this.Code= ResultCode.Code.InvalidUserName;
        }
    }
}
