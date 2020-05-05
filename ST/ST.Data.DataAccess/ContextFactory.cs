using Microsoft.EntityFrameworkCore;

namespace ST.Data.DataAccess
{
    public class ContextFactory
    {
        public static STDbContext GetMemoryContext(string nameBd)
        {
            var builder = new DbContextOptionsBuilder<STDbContext>();
            return new STDbContext(GetMemoryConfig(builder, nameBd));
        }

        private static DbContextOptions GetMemoryConfig(DbContextOptionsBuilder builder, string nameBd)
        {
            builder.UseInMemoryDatabase(nameBd);
            return builder.Options;
        }
    }
}
