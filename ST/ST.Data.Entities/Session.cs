using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Entities
{
    public class Session
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public Guid Token { get; set; }
        public DateTime LastSession { get; set; }
        
        public Session() { }

        public Session(Guid userId)
        {
            this.Id = Guid.NewGuid();
            this.UserId = userId;
            this.Token = Guid.NewGuid();
            this.LastSession = DateTime.Now;
        }

        public Session Update(Session session)
        {
            LastSession = DateTime.Now;
            Token = Guid.NewGuid();

            return this;
        }

        public override bool Equals(object obj)
        {
            return Id.Equals(((Session)obj).Id) && Token.Equals(((Session)obj).Token);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
