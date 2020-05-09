using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ST.Data.DataAccess.Migrations
{
    public partial class AddNewEntities : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Trajects_Users_UserId",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "IsAdmin",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "FinalLatitude",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "FinalLongitude",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "InitialLatitude",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "InitialLongitude",
                table: "Trajects");

            migrationBuilder.RenameColumn(
                name: "UserId",
                table: "Trajects",
                newName: "LocationInitialId");

            migrationBuilder.RenameIndex(
                name: "IX_Trajects_UserId",
                table: "Trajects",
                newName: "IX_Trajects_LocationInitialId");

            migrationBuilder.AddColumn<Guid>(
                name: "CompanyId",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "Employee_CompanyId",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "EmployeeStatus",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "LocationId",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Discriminator",
                table: "Users",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<Guid>(
                name: "EmployeeId",
                table: "Trajects",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "LocationFinalId",
                table: "Trajects",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Companys",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Companys", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Locations",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Latitude = table.Column<double>(nullable: false),
                    Longitude = table.Column<double>(nullable: false),
                    LocationTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Locations", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Users_CompanyId",
                table: "Users",
                column: "CompanyId");

            migrationBuilder.CreateIndex(
                name: "IX_Users_Employee_CompanyId",
                table: "Users",
                column: "Employee_CompanyId");

            migrationBuilder.CreateIndex(
                name: "IX_Users_LocationId",
                table: "Users",
                column: "LocationId");

            migrationBuilder.CreateIndex(
                name: "IX_Trajects_EmployeeId",
                table: "Trajects",
                column: "EmployeeId");

            migrationBuilder.CreateIndex(
                name: "IX_Trajects_LocationFinalId",
                table: "Trajects",
                column: "LocationFinalId");

            migrationBuilder.AddForeignKey(
                name: "FK_Trajects_Users_EmployeeId",
                table: "Trajects",
                column: "EmployeeId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Trajects_Locations_LocationFinalId",
                table: "Trajects",
                column: "LocationFinalId",
                principalTable: "Locations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Trajects_Locations_LocationInitialId",
                table: "Trajects",
                column: "LocationInitialId",
                principalTable: "Locations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Companys_CompanyId",
                table: "Users",
                column: "CompanyId",
                principalTable: "Companys",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Companys_Employee_CompanyId",
                table: "Users",
                column: "Employee_CompanyId",
                principalTable: "Companys",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Locations_LocationId",
                table: "Users",
                column: "LocationId",
                principalTable: "Locations",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Trajects_Users_EmployeeId",
                table: "Trajects");

            migrationBuilder.DropForeignKey(
                name: "FK_Trajects_Locations_LocationFinalId",
                table: "Trajects");

            migrationBuilder.DropForeignKey(
                name: "FK_Trajects_Locations_LocationInitialId",
                table: "Trajects");

            migrationBuilder.DropForeignKey(
                name: "FK_Users_Companys_CompanyId",
                table: "Users");

            migrationBuilder.DropForeignKey(
                name: "FK_Users_Companys_Employee_CompanyId",
                table: "Users");

            migrationBuilder.DropForeignKey(
                name: "FK_Users_Locations_LocationId",
                table: "Users");

            migrationBuilder.DropTable(
                name: "Companys");

            migrationBuilder.DropTable(
                name: "Locations");

            migrationBuilder.DropIndex(
                name: "IX_Users_CompanyId",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Users_Employee_CompanyId",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Users_LocationId",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Trajects_EmployeeId",
                table: "Trajects");

            migrationBuilder.DropIndex(
                name: "IX_Trajects_LocationFinalId",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "CompanyId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "Employee_CompanyId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "EmployeeStatus",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "LocationId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "Discriminator",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "EmployeeId",
                table: "Trajects");

            migrationBuilder.DropColumn(
                name: "LocationFinalId",
                table: "Trajects");

            migrationBuilder.RenameColumn(
                name: "LocationInitialId",
                table: "Trajects",
                newName: "UserId");

            migrationBuilder.RenameIndex(
                name: "IX_Trajects_LocationInitialId",
                table: "Trajects",
                newName: "IX_Trajects_UserId");

            migrationBuilder.AddColumn<bool>(
                name: "IsAdmin",
                table: "Users",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<double>(
                name: "FinalLatitude",
                table: "Trajects",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddColumn<double>(
                name: "FinalLongitude",
                table: "Trajects",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddColumn<double>(
                name: "InitialLatitude",
                table: "Trajects",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddColumn<double>(
                name: "InitialLongitude",
                table: "Trajects",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddForeignKey(
                name: "FK_Trajects_Users_UserId",
                table: "Trajects",
                column: "UserId",
                principalTable: "Users",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
