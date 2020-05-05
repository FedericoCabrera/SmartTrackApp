using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces
{
    public class ResultCode
    {
        public enum Code
        {
            Ok,
            InvalidUserName,
            InvalidPassword,
            EntityNotFound,
            RequiredPropertyNotFound
        }
    }
}
