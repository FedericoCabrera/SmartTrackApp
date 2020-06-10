using Google.Apis.Auth.OAuth2.Responses;
using Microsoft.Extensions.Configuration;
using Nancy.Json;
using ST.BusinessLogic.Interfaces;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Text;

namespace ST.BusinessLogic
{
    public class FirebaseMessagingRestClient : IFirebaseMessagingRestClient
    {
        public IConfiguration Configuration { get; }
        public FirebaseMessagingRestClient( IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public void SendNotification(string tokenTo, string title, string message)
        {
            var messageObj = CreateMessage(tokenTo, title, message);
            var byteArray = SerializeMessage(messageObj);
            SendRequest(byteArray);
        }

        private object CreateMessage(string tokenTo, string title, string message)
        {
            var data = new
            {
                to = tokenTo,
                notification = new
                {
                    body = message,
                    title = title
                }
            };

            return data;
        }

        private byte[] SerializeMessage(object data)
        {
            var serializer = new JavaScriptSerializer();
            var json = serializer.Serialize(data);
            byte[] byteArray = Encoding.UTF8.GetBytes(json);
            
            return byteArray;
        }

        private void SendRequest(byte[] byteArray)
        {
            string serverApiKey = Configuration["Firebase:FCMApiKey"];
            string fcmApiBaseUri = Configuration["Firebase:FCMApiBaseUri"];
            string fcmApiSendUri = Configuration["Firebase:FCMApiSendUri"];

            WebRequest request = WebRequest.Create(fcmApiBaseUri+ fcmApiSendUri);
            request.Method = "post";
            request.ContentType = "application/json";
            request.Headers.Add($"Authorization: {serverApiKey}");
            request.ContentLength = byteArray.Length;

            Stream dataStream = request.GetRequestStream();
            dataStream.Write(byteArray, 0, byteArray.Length);
            dataStream.Close();

            WebResponse response = request.GetResponse();
            dataStream = response.GetResponseStream();

            StreamReader reader = new StreamReader(dataStream);
            string responseFromServer = reader.ReadToEnd();

            reader.Close();
            dataStream.Close();
            response.Close();
            
        }
    }
}
