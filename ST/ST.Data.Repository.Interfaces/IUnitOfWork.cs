using ST.Data.Entities;
using System;

namespace ST.Data.Repository.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        IRepository<Traject> TrajectRepository { get; }
    }
}
