using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ST.Data.DataAccess.Migrations
{
    public partial class AddIncidents : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "FirebaseDeviceToken",
                table: "Users",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Incidents",
                columns: table => new
                {
                    ID = table.Column<Guid>(nullable: false),
                    LocationId = table.Column<Guid>(nullable: true),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    Base64Image = table.Column<string>(nullable: true),
                    Description = table.Column<string>(nullable: true),
                    TrajectId = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Incidents", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Incidents_Locations_LocationId",
                        column: x => x.LocationId,
                        principalTable: "Locations",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Incidents_Trajects_TrajectId",
                        column: x => x.TrajectId,
                        principalTable: "Trajects",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Incidents_LocationId",
                table: "Incidents",
                column: "LocationId");

            migrationBuilder.CreateIndex(
                name: "IX_Incidents_TrajectId",
                table: "Incidents",
                column: "TrajectId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Incidents");

            migrationBuilder.DropColumn(
                name: "FirebaseDeviceToken",
                table: "Users");
        }
    }
}
