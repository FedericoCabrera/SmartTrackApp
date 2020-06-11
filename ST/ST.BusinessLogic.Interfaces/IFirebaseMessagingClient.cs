using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace ST.BusinessLogic.Interfaces
{
    public interface IFirebaseMessagingRestClient
    {
        void SendNotification(string token, string title, string body);
    }
}
