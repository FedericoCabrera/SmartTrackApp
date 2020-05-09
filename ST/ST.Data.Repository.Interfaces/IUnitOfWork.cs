using ST.Data.Entities;
using System;

namespace ST.Data.Repository.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        IRepository<Administrator> AdministratorRepository { get; }
        IRepository<Employee> EmployeeRepository { get; }
        IRepository<Company> CompanyRepository { get; }
        IRepository<Location> LocationRepository { get; }
        IRepository<Traject> TrajectRepository { get; }
        IRepository<Session> SessionRepository { get; }
    }
}
