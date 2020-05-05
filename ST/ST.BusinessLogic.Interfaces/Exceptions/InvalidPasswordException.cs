using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class InvalidPasswordException : LogicException
    {
        public InvalidPasswordException()
        {
            this.Code = ResultCode.Code.InvalidPassword;
        }
    }
}
