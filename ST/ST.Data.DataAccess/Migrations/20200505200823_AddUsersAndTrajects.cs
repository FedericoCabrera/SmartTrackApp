using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ST.Data.DataAccess.Migrations
{
    public partial class AddUsersAndTrajects : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    Name = table.Column<string>(nullable: true),
                    LastName = table.Column<string>(nullable: true),
                    UserName = table.Column<string>(nullable: true),
                    Password = table.Column<string>(nullable: true),
                    IdentityNumber = table.Column<string>(nullable: true),
                    IsAdmin = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Trajects",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    InitialLatitude = table.Column<double>(nullable: false),
                    InitialLongitude = table.Column<double>(nullable: false),
                    FinalLatitude = table.Column<double>(nullable: false),
                    FinalLongitude = table.Column<double>(nullable: false),
                    Distance = table.Column<double>(nullable: false),
                    Duration = table.Column<double>(nullable: false),
                    IsFinished = table.Column<bool>(nullable: false),
                    StartDate = table.Column<DateTime>(nullable: false),
                    UserId = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Trajects", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Trajects_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Trajects_UserId",
                table: "Trajects",
                column: "UserId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Trajects");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
