USE LP_DOKUMENTE
EXEC sp_dropuser 'lpadmin'
EXEC sp_changedbowner 'lpadmin'

USE LP
--EXEC sp_dropuser 'sa'
EXEC sp_changedbowner 'sa'
