﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using ST.BusinessLogic;
using ST.BusinessLogic.Interfaces;
using ST.Data.DataAccess;
using ST.Data.Repository;
using ST.Data.Repository.Interfaces;

namespace ST.Web.API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);

            //Db Connection
            var connection = Configuration["DBConnection:Connection"];
            services.AddDbContext<STDbContext>(o => o.UseSqlServer(connection));

            //Dependency Injection
            services.AddScoped<IEmployeeService, EmployeeService>();
            services.AddScoped<IUnitOfWork, UnitOfWork>();
            services.AddScoped<ISessionService, SessionService>();
            services.AddScoped<ICompanyService, CompanyService>();
            services.AddScoped<IAdministratorService, AdministratorService>();
            services.AddScoped<IUserService, UserService>();
            services.AddScoped<ITrajectService, TrajectService>();
            services.AddScoped<IFirebaseMessagingRestClient, FirebaseMessagingRestClient>();


            //CORS
            services.AddCors(options =>
            {
                options.AddPolicy("CorsPolicy",
                    builder => builder.AllowAnyOrigin()
                        .AllowAnyMethod()
                        .AllowAnyHeader()
                        .AllowCredentials());
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseCors("CorsPolicy");
            app.UseHttpsRedirection();
            app.UseMvc();
        }
    }
}
