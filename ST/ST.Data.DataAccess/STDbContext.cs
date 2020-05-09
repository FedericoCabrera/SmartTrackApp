using Microsoft.EntityFrameworkCore;
using ST.Data.Entities;
using System;

namespace ST.Data.DataAccess
{
    public class STDbContext : DbContext
    {
        public STDbContext(DbContextOptions options) : base(options)
        {

        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Employee> Employees { get; set; }
        public DbSet<Administrator> Administrators { get; set; }
        public DbSet<Company> Companys { get; set; }
        public DbSet<Traject> Trajects { get; set; }
        public DbSet<Location> Locations { get; set; }
        public DbSet<Session> Sessions { get; set; }
    }
}