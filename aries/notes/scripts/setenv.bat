rem Local workspace location
set WORKSPACE=c:\workspace\AEROSPACE

rem Maven repository location
set REPOSITORY=c:\workspace\.m2

rem Aerospace development location
set AERO_HOME=%WORKSPACE%\aero

rem Java development kit location
set JAVA_HOME=c:\software\jdk1.6.0_20

rem Ant home location
set ANT_HOME=c:\software\apache-ant-1.8.0

rem Maven home location
set MAVEN_HOME=c:\software\apache-maven-2.2.1

rem Tomcat home location
set TOMCAT_HOME=c:\software\apache-tomcat-6.0.26

rem MySQL home location
set MYSQL_HOME=c:\software\mysql-5.1.45

rem adjust command path
set PATH=%AERO_HOME%\scripts;%JAVA_HOME%\bin;%ANT_HOME%\bin;%MAVEN_HOME%\bin;%TOMCAT_HOME%\bin;%MYSQL_HOME%\bin;%PATH%
