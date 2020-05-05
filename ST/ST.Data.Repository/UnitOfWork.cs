using ST.Data.DataAccess;
using ST.Data.Entities;
using ST.Data.Repository.Interfaces;
using System;
using System.Collections.Generic;
using System.Text;

namespace ST.Data.Repository
{
    public class UnitOfWork : IUnitOfWork
    {
        private bool disposed;
        private readonly STDbContext dbContext;

        public UnitOfWork()
        {
            disposed = false;
        }

        public UnitOfWork(STDbContext context)
        {
            dbContext = context;
            disposed = false;
        }

        private IRepository<User> userRepository;
        public IRepository<User> UserRepository
        {
            get
            {
                if (userRepository == null)
                    userRepository = new Repository<User>(dbContext);

                return userRepository;
            }
        }

        private IRepository<Traject> trajectRepository;
        public IRepository<Traject> TrajectRepository
        {
            get
            {
                if (trajectRepository == null)
                    trajectRepository = new Repository<Traject>(dbContext);

                return trajectRepository;
            }
        }



        protected virtual void Dispose(bool disposing)
        {
            if (!disposed)
            {
                if (disposing)
                {
                    dbContext.Dispose();
                }
            }
            disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

    }
}
