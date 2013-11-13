cd ..\
@del /F /Q sweetsnake-client\export
@del /F /Q sweetsnake-server\export
@call mvn clean install -Dmaven.test.skip=true
@pause