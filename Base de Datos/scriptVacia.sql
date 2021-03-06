USE [master]
GO
/****** Object:  Database [SmartTrackDB]    Script Date: 23/06/2020 16:20:07 ******/
CREATE DATABASE [SmartTrackDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'SmartTrackDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\SmartTrackDB.mdf' , SIZE = 4288KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'SmartTrackDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\SmartTrackDB_log.ldf' , SIZE = 1072KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [SmartTrackDB] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [SmartTrackDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [SmartTrackDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [SmartTrackDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [SmartTrackDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [SmartTrackDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [SmartTrackDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [SmartTrackDB] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [SmartTrackDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [SmartTrackDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [SmartTrackDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [SmartTrackDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [SmartTrackDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [SmartTrackDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [SmartTrackDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [SmartTrackDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [SmartTrackDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [SmartTrackDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [SmartTrackDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [SmartTrackDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [SmartTrackDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [SmartTrackDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [SmartTrackDB] SET READ_COMMITTED_SNAPSHOT ON 
GO
ALTER DATABASE [SmartTrackDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [SmartTrackDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [SmartTrackDB] SET  MULTI_USER 
GO
ALTER DATABASE [SmartTrackDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [SmartTrackDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [SmartTrackDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [SmartTrackDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [SmartTrackDB] SET DELAYED_DURABILITY = DISABLED 
GO
USE [SmartTrackDB]
GO
/****** Object:  Table [dbo].[__EFMigrationsHistory]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[__EFMigrationsHistory](
	[MigrationId] [nvarchar](150) NOT NULL,
	[ProductVersion] [nvarchar](32) NOT NULL,
 CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY CLUSTERED 
(
	[MigrationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Companys]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Companys](
	[Id] [uniqueidentifier] NOT NULL,
	[Name] [nvarchar](max) NULL,
 CONSTRAINT [PK_Companys] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Incidents]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Incidents](
	[ID] [uniqueidentifier] NOT NULL,
	[LocationId] [uniqueidentifier] NULL,
	[CreationTime] [datetime2](7) NOT NULL,
	[Base64Image] [nvarchar](max) NULL,
	[Description] [nvarchar](max) NULL,
	[TrajectId] [uniqueidentifier] NULL,
 CONSTRAINT [PK_Incidents] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Locations]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Locations](
	[Id] [uniqueidentifier] NOT NULL,
	[Latitude] [float] NOT NULL,
	[Longitude] [float] NOT NULL,
	[LocationTime] [datetime2](7) NOT NULL,
	[Address] [nvarchar](max) NULL,
 CONSTRAINT [PK_Locations] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Sessions]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sessions](
	[Id] [uniqueidentifier] NOT NULL,
	[UserId] [uniqueidentifier] NOT NULL,
	[Token] [uniqueidentifier] NOT NULL,
	[LastSession] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_Sessions] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Trajects]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Trajects](
	[Id] [uniqueidentifier] NOT NULL,
	[Distance] [float] NOT NULL,
	[Duration] [float] NOT NULL,
	[IsFinished] [bit] NOT NULL,
	[StartDate] [datetime2](7) NOT NULL,
	[LocationInitialId] [uniqueidentifier] NULL,
	[EmployeeId] [uniqueidentifier] NULL,
	[LocationFinalId] [uniqueidentifier] NULL,
 CONSTRAINT [PK_Trajects] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 23/06/2020 16:20:08 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [uniqueidentifier] NOT NULL,
	[Name] [nvarchar](max) NULL,
	[LastName] [nvarchar](max) NULL,
	[UserName] [nvarchar](max) NULL,
	[Password] [nvarchar](max) NULL,
	[IdentityNumber] [nvarchar](max) NULL,
	[CompanyId] [uniqueidentifier] NULL,
	[Employee_CompanyId] [uniqueidentifier] NULL,
	[EmployeeStatus] [int] NULL,
	[LocationId] [uniqueidentifier] NULL,
	[Discriminator] [nvarchar](max) NOT NULL DEFAULT (N''),
	[FirebaseDeviceToken] [nvarchar](max) NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Index [IX_Incidents_LocationId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Incidents_LocationId] ON [dbo].[Incidents]
(
	[LocationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Incidents_TrajectId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Incidents_TrajectId] ON [dbo].[Incidents]
(
	[TrajectId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Trajects_EmployeeId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Trajects_EmployeeId] ON [dbo].[Trajects]
(
	[EmployeeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Trajects_LocationFinalId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Trajects_LocationFinalId] ON [dbo].[Trajects]
(
	[LocationFinalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Trajects_LocationInitialId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Trajects_LocationInitialId] ON [dbo].[Trajects]
(
	[LocationInitialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Users_CompanyId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Users_CompanyId] ON [dbo].[Users]
(
	[CompanyId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Users_Employee_CompanyId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Users_Employee_CompanyId] ON [dbo].[Users]
(
	[Employee_CompanyId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Users_LocationId]    Script Date: 23/06/2020 16:20:08 ******/
CREATE NONCLUSTERED INDEX [IX_Users_LocationId] ON [dbo].[Users]
(
	[LocationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Incidents]  WITH CHECK ADD  CONSTRAINT [FK_Incidents_Locations_LocationId] FOREIGN KEY([LocationId])
REFERENCES [dbo].[Locations] ([Id])
GO
ALTER TABLE [dbo].[Incidents] CHECK CONSTRAINT [FK_Incidents_Locations_LocationId]
GO
ALTER TABLE [dbo].[Incidents]  WITH CHECK ADD  CONSTRAINT [FK_Incidents_Trajects_TrajectId] FOREIGN KEY([TrajectId])
REFERENCES [dbo].[Trajects] ([Id])
GO
ALTER TABLE [dbo].[Incidents] CHECK CONSTRAINT [FK_Incidents_Trajects_TrajectId]
GO
ALTER TABLE [dbo].[Trajects]  WITH CHECK ADD  CONSTRAINT [FK_Trajects_Locations_LocationFinalId] FOREIGN KEY([LocationFinalId])
REFERENCES [dbo].[Locations] ([Id])
GO
ALTER TABLE [dbo].[Trajects] CHECK CONSTRAINT [FK_Trajects_Locations_LocationFinalId]
GO
ALTER TABLE [dbo].[Trajects]  WITH CHECK ADD  CONSTRAINT [FK_Trajects_Locations_LocationInitialId] FOREIGN KEY([LocationInitialId])
REFERENCES [dbo].[Locations] ([Id])
GO
ALTER TABLE [dbo].[Trajects] CHECK CONSTRAINT [FK_Trajects_Locations_LocationInitialId]
GO
ALTER TABLE [dbo].[Trajects]  WITH CHECK ADD  CONSTRAINT [FK_Trajects_Users_EmployeeId] FOREIGN KEY([EmployeeId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Trajects] CHECK CONSTRAINT [FK_Trajects_Users_EmployeeId]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Companys_CompanyId] FOREIGN KEY([CompanyId])
REFERENCES [dbo].[Companys] ([Id])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Companys_CompanyId]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Companys_Employee_CompanyId] FOREIGN KEY([Employee_CompanyId])
REFERENCES [dbo].[Companys] ([Id])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Companys_Employee_CompanyId]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Locations_LocationId] FOREIGN KEY([LocationId])
REFERENCES [dbo].[Locations] ([Id])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Locations_LocationId]
GO
USE [master]
GO
ALTER DATABASE [SmartTrackDB] SET  READ_WRITE 
GO
