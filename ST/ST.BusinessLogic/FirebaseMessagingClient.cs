using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;
using ST.BusinessLogic.Interfaces;

namespace ST.BusinessLogic
{
    public class FirebaseMessagingClient
    {
        private readonly FirebaseMessaging firebaseMessaging;
        private static FirebaseMessagingClient firebaseMessagingClient;

        private FirebaseMessagingClient()
        {
            var path = Directory.GetCurrentDirectory();

            var credential = GoogleCredential.FromFile(Directory.GetCurrentDirectory() + "/serviceAccountKey.json").CreateScoped("https://www.googleapis.com/auth/firebase.messaging");
            //var credential = GoogleCredential.FromFile("serviceAccountKey.json").CreateScoped("https://www.googleapis.com/auth/firebase.messaging");
            var app = FirebaseApp.Create(new AppOptions() { Credential = credential });
            firebaseMessaging = FirebaseMessaging.GetMessaging(app);
        }

        public static FirebaseMessagingClient GetInstance()
        {
            if(firebaseMessagingClient == null)
            {
                firebaseMessagingClient = new FirebaseMessagingClient();
            }

            return firebaseMessagingClient;
        }

        private Message CreateNotification(string title, string notificationBody, string token)
        {
            return new Message()
            {
                Token = token,
                Notification = new Notification()
                {
                    Body = notificationBody,
                    Title = title
                }
                
            };
        }

        public async Task SendNotification(string token, string title, string body)
        {
            var result = await firebaseMessaging.SendAllAsync(new List<Message> { CreateNotification(title, body, token) });
            //do something with result
        }
    }
}
