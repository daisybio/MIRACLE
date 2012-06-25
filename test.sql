-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: changelog-0.3.groovy
-- Ran at: 22-06-12 16:39
-- Against: rppa@jdbc:sqlserver://10.149.64.14:1433;xopenStates=false;sendTimeAsDatetime=true;trustServerCertificate=false;sendStringParametersAsUnicode=false;selectMethod=direct;responseBuffering=adaptive;packetSize=8000;loginTimeout=15;lockTimeout=-1;lastUpdateCount=true;encrypt=false;disableStatementPooling=true;databaseName=RPPAScanner_Copy;applicationName=Microsoft SQL Server JDBC Driver;
-- Liquibase version: 2.0.5
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE [dbo].[DATABASECHANGELOGLOCK] ([ID] INT NOT NULL, [LOCKED] BIT NOT NULL, [LOCKGRANTED] DATETIME, [LOCKEDBY] VARCHAR(255), CONSTRAINT [PK_DATABASECHANGELOGLOCK] PRIMARY KEY ([ID]))
GO

INSERT INTO [dbo].[DATABASECHANGELOGLOCK] ([ID], [LOCKED]) VALUES (1, 0)
GO

-- Lock Database
-- Create Database Change Log Table
CREATE TABLE [dbo].[DATABASECHANGELOG] ([ID] VARCHAR(63) NOT NULL, [AUTHOR] VARCHAR(63) NOT NULL, [FILENAME] VARCHAR(200) NOT NULL, [DATEEXECUTED] DATETIME NOT NULL, [ORDEREXECUTED] INT NOT NULL, [EXECTYPE] VARCHAR(10) NOT NULL, [MD5SUM] VARCHAR(35), [DESCRIPTION] VARCHAR(255), [COMMENTS] VARCHAR(255), [TAG] VARCHAR(255), [LIQUIBASE] VARCHAR(20), CONSTRAINT [PK_DATABASECHANGELOG] PRIMARY KEY ([ID], [AUTHOR], [FILENAME]))
GO

-- Changeset changelog-0.3.groovy::1340373645634-1::mlist (generated)::(Checksum: 3:3632d9b908b514707b324c694d6e74c8)
CREATE TABLE [dbo].[person] ([id] BIGINT IDENTITY NOT NULL, [version] BIGINT NOT NULL, [account_expired] bit NOT NULL, [account_locked] bit NOT NULL, [enabled] bit NOT NULL, [password] VARCHAR(255) NOT NULL, [password_expired] bit NOT NULL, [username] VARCHAR(255) NOT NULL, CONSTRAINT [personPK] PRIMARY KEY ([id]))
GO

insert into person values (0, 0, 0, 1, 'password', 0, 'mlpedersen')
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table, Custom SQL', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-1', '2.0.5', '3:3632d9b908b514707b324c694d6e74c8', 1)
GO

-- Changeset changelog-0.3.groovy::1340373645634-7::mlist (generated)::(Checksum: 3:98d74b59f64ca9c3a4deabfb071426d5)
CREATE TABLE [dbo].[role] ([id] BIGINT IDENTITY NOT NULL, [version] BIGINT NOT NULL, [authority] VARCHAR(255) NOT NULL, CONSTRAINT [rolePK] PRIMARY KEY ([id]))
GO

insert into role values(0, 'ROLE_ADMIN')
GO

insert into role values(0, 'ROLE_USER')
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table, Custom SQL (x2)', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-7', '2.0.5', '3:98d74b59f64ca9c3a4deabfb071426d5', 2)
GO

-- Changeset changelog-0.3.groovy::1340373645634-2::mlist (generated)::(Checksum: 3:524eb7dea0f04a6532a7bf4d0e9d6c5f)
CREATE TABLE [dbo].[person_role] ([role_id] BIGINT NOT NULL, [person_id] BIGINT NOT NULL)
GO

insert into person_role values(1, 1)
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table, Custom SQL', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-2', '2.0.5', '3:524eb7dea0f04a6532a7bf4d0e9d6c5f', 3)
GO

-- Changeset changelog-0.3.groovy::1340373645634-3::mlist (generated)::(Checksum: 3:afae2e7d1536cb528dd74c7fa47b5f32)
CREATE TABLE [dbo].[project] ([id] BIGINT IDENTITY NOT NULL, [version] BIGINT NOT NULL, [created_by_id] BIGINT NOT NULL, [date_created] datetime2 NOT NULL, [last_updated] datetime2 NOT NULL, [last_updated_by_id] BIGINT NOT NULL, [project_description] VARCHAR(255) NOT NULL, [project_title] VARCHAR(255) NOT NULL, CONSTRAINT [projectPK] PRIMARY KEY ([id]))
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-3', '2.0.5', '3:afae2e7d1536cb528dd74c7fa47b5f32', 4)
GO

-- Changeset changelog-0.3.groovy::1340373645634-4::mlist (generated)::(Checksum: 3:ac3d1382c2739b53737f796725e8aee7)
CREATE TABLE [dbo].[project_slide] ([project_slides_id] BIGINT, [slide_id] BIGINT)
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-4', '2.0.5', '3:ac3d1382c2739b53737f796725e8aee7', 5)
GO

-- Changeset changelog-0.3.groovy::1340373645634-5::mlist (generated)::(Checksum: 3:0c51f764b4ab38902f5abdb1d4211f89)
CREATE TABLE [dbo].[project_slide_layout] ([project_layouts_id] BIGINT, [slide_layout_id] BIGINT)
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-5', '2.0.5', '3:0c51f764b4ab38902f5abdb1d4211f89', 6)
GO

-- Changeset changelog-0.3.groovy::1340373645634-6::mlist (generated)::(Checksum: 3:97e7857fd9f0a98915ac836e5aab9845)
CREATE TABLE [dbo].[registration_code] ([id] BIGINT IDENTITY NOT NULL, [date_created] datetime2 NOT NULL, [token] VARCHAR(255) NOT NULL, [username] VARCHAR(255) NOT NULL, CONSTRAINT [registration_PK] PRIMARY KEY ([id]))
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Table', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-6', '2.0.5', '3:97e7857fd9f0a98915ac836e5aab9845', 7)
GO

-- Changeset changelog-0.3.groovy::1340373645634-8::mlist (generated)::(Checksum: 3:bf495671497b9ecc72f161ecedebe4e3)
ALTER TABLE [dbo].[antibody] ADD [comments] VARCHAR(255)
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-8', '2.0.5', '3:bf495671497b9ecc72f161ecedebe4e3', 8)
GO

-- Changeset changelog-0.3.groovy::1340373645634-9::mlist (generated)::(Checksum: 3:d9d13d771a68ecf8a9ce987fe8611c70)
ALTER TABLE [dbo].[slide] ADD [comments] VARCHAR(255)
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-9', '2.0.5', '3:d9d13d771a68ecf8a9ce987fe8611c70', 9)
GO

-- Changeset changelog-0.3.groovy::1340373645634-10::mlist (generated)::(Checksum: 3:a7c83a83ce0c1dc8969023abee31a82f)
ALTER TABLE [dbo].[slide] ADD [created_by_id] BIGINT
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-10', '2.0.5', '3:a7c83a83ce0c1dc8969023abee31a82f', 10)
GO

-- Changeset changelog-0.3.groovy::1340373645634-11::mlist (generated)::(Checksum: 3:e9ac44163b6fade055467593952228ac)
ALTER TABLE [dbo].[slide] ADD [date_created] datetime2 NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-11', '2.0.5', '3:e9ac44163b6fade055467593952228ac', 11)
GO

-- Changeset changelog-0.3.groovy::1340373645634-12::mlist (generated)::(Checksum: 3:8f677d595859512e9d31a62ffdf4fe86)
ALTER TABLE [dbo].[slide] ADD [last_updated] datetime2 NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-12', '2.0.5', '3:8f677d595859512e9d31a62ffdf4fe86', 12)
GO

-- Changeset changelog-0.3.groovy::1340373645634-13::mlist (generated)::(Checksum: 3:4cf738e237273e4621acaf52bfbe0bda)
ALTER TABLE [dbo].[slide] ADD [last_updated_by_id] BIGINT
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-13', '2.0.5', '3:4cf738e237273e4621acaf52bfbe0bda', 13)
GO

-- Changeset changelog-0.3.groovy::1340373645634-14::mlist (generated)::(Checksum: 3:baa70baa61260c1f95af8122066ea8c5)
ALTER TABLE [dbo].[slide_layout] ADD [created_by_id] BIGINT
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-14', '2.0.5', '3:baa70baa61260c1f95af8122066ea8c5', 14)
GO

-- Changeset changelog-0.3.groovy::1340373645634-15::mlist (generated)::(Checksum: 3:c997819aa0b98accfad4bd5568b280bd)
ALTER TABLE [dbo].[slide_layout] ADD [date_created] datetime2 NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-15', '2.0.5', '3:c997819aa0b98accfad4bd5568b280bd', 15)
GO

-- Changeset changelog-0.3.groovy::1340373645634-16::mlist (generated)::(Checksum: 3:4b60fbd179069be126c69a3687d2169e)
ALTER TABLE [dbo].[slide_layout] ADD [last_updated] datetime2 NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-16', '2.0.5', '3:4b60fbd179069be126c69a3687d2169e', 16)
GO

-- Changeset changelog-0.3.groovy::1340373645634-17::mlist (generated)::(Checksum: 3:d5bb597d771279abe5d272cf3c398bca)
ALTER TABLE [dbo].[slide_layout] ADD [last_updated_by_id] BIGINT
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Column', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-17', '2.0.5', '3:d5bb597d771279abe5d272cf3c398bca', 17)
GO

-- Changeset changelog-0.3.groovy::1340373645634-18::mlist (generated)::(Checksum: 3:030145bed2d75eeeccfbebf530f31f45)
ALTER TABLE [dbo].[antibody] ALTER COLUMN [concentration] double precision(19) NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Drop Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-18', '2.0.5', '3:030145bed2d75eeeccfbebf530f31f45', 18)
GO

-- Changeset changelog-0.3.groovy::1340373645634-19::mlist (generated)::(Checksum: 3:0f7ae2deb7d83f49df6d77351c490a32)
ALTER TABLE [dbo].[antibody] ALTER COLUMN [concentration_unit] VARCHAR(2) NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Drop Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-19', '2.0.5', '3:0f7ae2deb7d83f49df6d77351c490a32', 19)
GO

-- Changeset changelog-0.3.groovy::1340373645634-20::mlist (generated)::(Checksum: 3:b1890fa86247f01bacf417bbdca6b58b)
ALTER TABLE [dbo].[spot] ALTER COLUMN [layout_spot_id] BIGINT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Drop Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-20', '2.0.5', '3:b1890fa86247f01bacf417bbdca6b58b', 20)
GO

-- Changeset changelog-0.3.groovy::1340373645634-21::mlist (generated)::(Checksum: 3:f7f26e986e63b5d7473eb5a8f00eec06)
ALTER TABLE [dbo].[person_role] ADD CONSTRAINT [person_rolePK] PRIMARY KEY ([role_id], [person_id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Primary Key', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-21', '2.0.5', '3:f7f26e986e63b5d7473eb5a8f00eec06', 21)
GO

-- Changeset changelog-0.3.groovy::1340373645634-22::mlist (generated)::(Checksum: 3:99e8ea0c4eae0684f8a63184241cb942)
ALTER TABLE [dbo].[slide] DROP CONSTRAINT [FK6873DB1F058D40D]
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Drop Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-22', '2.0.5', '3:99e8ea0c4eae0684f8a63184241cb942', 22)
GO

-- Changeset changelog-0.3.groovy::1340373645634-23::mlist (generated)::(Checksum: 3:78055a6b43fb91dfa17937642482d189)
ALTER TABLE [dbo].[person_role] ADD CONSTRAINT [FKE6A16B207D7505C5] FOREIGN KEY ([person_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-23', '2.0.5', '3:78055a6b43fb91dfa17937642482d189', 23)
GO

-- Changeset changelog-0.3.groovy::1340373645634-24::mlist (generated)::(Checksum: 3:c56dddc8f23e70f5321256c7b2f083c9)
ALTER TABLE [dbo].[person_role] ADD CONSTRAINT [FKE6A16B20C939F4E5] FOREIGN KEY ([role_id]) REFERENCES [dbo].[role] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-24', '2.0.5', '3:c56dddc8f23e70f5321256c7b2f083c9', 24)
GO

-- Changeset changelog-0.3.groovy::1340373645634-25::mlist (generated)::(Checksum: 3:915b0ba15ffcf88a9e4f5745625f565c)
ALTER TABLE [dbo].[project] ADD CONSTRAINT [FKED904B19BEF1F16C] FOREIGN KEY ([created_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-25', '2.0.5', '3:915b0ba15ffcf88a9e4f5745625f565c', 25)
GO

-- Changeset changelog-0.3.groovy::1340373645634-26::mlist (generated)::(Checksum: 3:f8be5d6674d826d33c6a8d01e963c704)
ALTER TABLE [dbo].[project] ADD CONSTRAINT [FKED904B193B098F16] FOREIGN KEY ([last_updated_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-26', '2.0.5', '3:f8be5d6674d826d33c6a8d01e963c704', 26)
GO

-- Changeset changelog-0.3.groovy::1340373645634-27::mlist (generated)::(Checksum: 3:5553dad94ce19ff0634afd9edbcb72f9)
ALTER TABLE [dbo].[project_slide] ADD CONSTRAINT [FKC80B750B53BB22B3] FOREIGN KEY ([project_slides_id]) REFERENCES [dbo].[project] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-27', '2.0.5', '3:5553dad94ce19ff0634afd9edbcb72f9', 27)
GO

-- Changeset changelog-0.3.groovy::1340373645634-28::mlist (generated)::(Checksum: 3:f788fdcc46e9150d0aaf5d410df16d73)
ALTER TABLE [dbo].[project_slide] ADD CONSTRAINT [FKC80B750B6BD5E4E7] FOREIGN KEY ([slide_id]) REFERENCES [dbo].[slide] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-28', '2.0.5', '3:f788fdcc46e9150d0aaf5d410df16d73', 28)
GO

-- Changeset changelog-0.3.groovy::1340373645634-29::mlist (generated)::(Checksum: 3:48a756517a4b3065a882b67cbf4e8798)
ALTER TABLE [dbo].[project_slide_layout] ADD CONSTRAINT [FKE50ED0DE3126D238] FOREIGN KEY ([project_layouts_id]) REFERENCES [dbo].[project] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-29', '2.0.5', '3:48a756517a4b3065a882b67cbf4e8798', 29)
GO

-- Changeset changelog-0.3.groovy::1340373645634-30::mlist (generated)::(Checksum: 3:51bcb6e933898efc4536650f12abbc28)
ALTER TABLE [dbo].[project_slide_layout] ADD CONSTRAINT [FKE50ED0DED6E665DC] FOREIGN KEY ([slide_layout_id]) REFERENCES [dbo].[slide_layout] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-30', '2.0.5', '3:51bcb6e933898efc4536650f12abbc28', 30)
GO

-- Changeset changelog-0.3.groovy::1340373645634-31::mlist (generated)::(Checksum: 3:34414d9c258e0b47024002f1588f5292)
ALTER TABLE [dbo].[slide] ADD CONSTRAINT [FK6873DB1BEF1F16C] FOREIGN KEY ([created_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-31', '2.0.5', '3:34414d9c258e0b47024002f1588f5292', 31)
GO

-- Changeset changelog-0.3.groovy::1340373645634-32::mlist (generated)::(Checksum: 3:51838d42b52f105d6fdab1df0d881c58)
ALTER TABLE [dbo].[slide] ADD CONSTRAINT [FK6873DB124200F90] FOREIGN KEY ([experimenter_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-32', '2.0.5', '3:51838d42b52f105d6fdab1df0d881c58', 32)
GO

-- Changeset changelog-0.3.groovy::1340373645634-33::mlist (generated)::(Checksum: 3:b28fcead5b2ccbfc795d01e6feae3e54)
ALTER TABLE [dbo].[slide] ADD CONSTRAINT [FK6873DB13B098F16] FOREIGN KEY ([last_updated_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-33', '2.0.5', '3:b28fcead5b2ccbfc795d01e6feae3e54', 33)
GO

-- Changeset changelog-0.3.groovy::1340373645634-34::mlist (generated)::(Checksum: 3:fbd7db6cb772a6a6ba968bfb902dc998)
ALTER TABLE [dbo].[slide_layout] ADD CONSTRAINT [FKBD452178BEF1F16C] FOREIGN KEY ([created_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-34', '2.0.5', '3:fbd7db6cb772a6a6ba968bfb902dc998', 34)
GO

-- Changeset changelog-0.3.groovy::1340373645634-35::mlist (generated)::(Checksum: 3:5ff943412c480135a53e13a3f22ffd01)
ALTER TABLE [dbo].[slide_layout] ADD CONSTRAINT [FKBD4521783B098F16] FOREIGN KEY ([last_updated_by_id]) REFERENCES [dbo].[person] ([id])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Foreign Key Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-35', '2.0.5', '3:5ff943412c480135a53e13a3f22ffd01', 35)
GO

-- Changeset changelog-0.3.groovy::1340373645634-36::mlist (generated)::(Checksum: 3:eddae7807d50a7b3007810d4e766b470)
CREATE UNIQUE INDEX [username_unique_1340373639887] ON [dbo].[person]([username])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Index', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-36', '2.0.5', '3:eddae7807d50a7b3007810d4e766b470', 36)
GO

-- Changeset changelog-0.3.groovy::1340373645634-37::mlist (generated)::(Checksum: 3:c98ae8bc11406add44032092be639c2c)
CREATE UNIQUE INDEX [project_title_unique_1340373639891] ON [dbo].[project]([project_title])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Index', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-37', '2.0.5', '3:c98ae8bc11406add44032092be639c2c', 37)
GO

-- Changeset changelog-0.3.groovy::1340373645634-38::mlist (generated)::(Checksum: 3:ccb89ec84eb9266833b92810bb5b957d)
CREATE UNIQUE INDEX [authority_unique_1340373639904] ON [dbo].[role]([authority])
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Create Index', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-38', '2.0.5', '3:ccb89ec84eb9266833b92810bb5b957d', 38)
GO

-- Changeset changelog-0.3.groovy::1340373645634-39::mlist (generated)::(Checksum: 3:2b265a3b32f2a9627cd16dc05ae74960)
DROP TABLE [dbo].[experimenter]
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Drop Table', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-39', '2.0.5', '3:2b265a3b32f2a9627cd16dc05ae74960', 39)
GO

-- Changeset changelog-0.3.groovy::1340373645634-40::mlist (generated)::(Checksum: 3:0b2a7509be63e1bf16cb1c464ab3ecbc)
ALTER TABLE [dbo].[slide] ALTER COLUMN [created_by_id] BIGINT NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-40', '2.0.5', '3:0b2a7509be63e1bf16cb1c464ab3ecbc', 40)
GO

-- Changeset changelog-0.3.groovy::1340373645634-41::mlist (generated)::(Checksum: 3:b905a3948cc91ca37a94b77863fa1c21)
ALTER TABLE [dbo].[slide] ALTER COLUMN [last_updated_by_id] BIGINT NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-41', '2.0.5', '3:b905a3948cc91ca37a94b77863fa1c21', 41)
GO

-- Changeset changelog-0.3.groovy::1340373645634-42::mlist (generated)::(Checksum: 3:ff56e9abfb46fa6acfe4ac583ac82983)
ALTER TABLE [dbo].[slide_layout] ALTER COLUMN [created_by_id] BIGINT NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-42', '2.0.5', '3:ff56e9abfb46fa6acfe4ac583ac82983', 42)
GO

-- Changeset changelog-0.3.groovy::1340373645634-43::mlist (generated)::(Checksum: 3:8ffa76e159a001b9fdc6006c658257e6)
ALTER TABLE [dbo].[slide_layout] ALTER COLUMN [last_updated_by_id] BIGINT NOT NULL
GO

INSERT INTO [dbo].[DATABASECHANGELOG] ([AUTHOR], [COMMENTS], [DATEEXECUTED], [DESCRIPTION], [EXECTYPE], [FILENAME], [ID], [LIQUIBASE], [MD5SUM], [ORDEREXECUTED]) VALUES ('mlist (generated)', '', GETDATE(), 'Add Not-Null Constraint', 'EXECUTED', 'changelog-0.3.groovy', '1340373645634-43', '2.0.5', '3:8ffa76e159a001b9fdc6006c658257e6', 43)
GO

