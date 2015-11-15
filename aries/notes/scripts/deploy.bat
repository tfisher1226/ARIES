rem c:\workspace\AEROSPACE\aero\scripts\setenv.bat

cd %WORKSPACE%\audit-service
mvn -e -DskipTests=true tomcat:deploy

cd %WORKSPACE%\authorization-service
mvn -e -DskipTests=true tomcat:deploy

cd %WORKSPACE%\authentication-service
mvn -e -DskipTests=true tomcat:deploy

cd %WORKSPACE%\icams-view
mvn -e -DskipTests=true tomcat:deploy

cd %WORKSPACE%
