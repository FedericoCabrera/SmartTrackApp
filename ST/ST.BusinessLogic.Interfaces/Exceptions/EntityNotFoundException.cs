using System;
using System.Collections.Generic;
using System.Text;

namespace ST.BusinessLogic.Interfaces.Exceptions
{
    public class EntityNotFoundException:LogicException
    {
        public EntityNotFoundException()
        {
            this.Code = ResultCode.Code.EntityNotFound;
        }
    }
}
