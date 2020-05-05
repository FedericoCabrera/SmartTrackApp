using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace ST.Data.Repository.Interfaces
{
    public interface IRepository<TEntity> where TEntity : class
    {
        void Create(TEntity entity);
        void CreateRange(IEnumerable<TEntity> entities);
        void Delete(TEntity entity);
        void Update(TEntity entity);
        void Attach(TEntity entity);
        TEntity GetByID(object id);
        IEnumerable<TEntity> Get(
        Expression<Func<TEntity, bool>> filter = null,
        Func<IQueryable<TEntity>, IOrderedQueryable<TEntity>> orderBy = null,
        string includeProperties = "");
        void Save();
    }
}
