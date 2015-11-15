# User dependent .bashrc file

# Don't put duplicate lines in the history.
export HISTCONTROL=ignoredups

export ANT_HOME=/c/software/apache-ant-1.8.2
export SVN_HOME=/c/software/svn-win32-1.4.6
export JAXWS_HOME=/c/software/jaxws-ri/2.1.2
export EMACS_HOME=/c/software/emacs-21.3

# Java setup
export JAVA_HOME=/c/software/jdk1.6.0_23
export JRE_HOME=/c/software/jre1.6.0_23

# Maven setup
export MAVEN_HOME=/c/software/apache-maven-3.0.1
#export MAVEN_HOME=/c/software/apache-maven-2.2.1
#export MAVEN_HOME=/c/software/apache-maven-2.0.9
export MAVEN_OPTS="-Xmx1024m -Xms512m -XX:PermSize=128m -XX:MaxPermSize=256m"
export M2_REPO="/c/workspace/.m2/repository"
export M2_HOME=$MAVEN_HOME

# JBoss setup
#export JBOSS_HOME=/c/software/jboss-6.0.0.Final
export JBOSS_HOME=/c/software/jboss-5.1.0.GA

# Tomcat setup
export TOMCAT_HOME=/c/software/apache-tomcat-6.0.26
export CATALINA_HOME=$TOMCAT_HOME

# MySQL setup
export MYSQL_HOME=/c/software/mysql-5.1.54-win32

# Set specific command path locations.
export PATH=$PATH:$CYGWIN_HOME/bin:$CYGWIN_HOME/usr/bin:$JAVA_HOME/bin:$ANT_HOME/bin:$MAVEN_HOME/bin:$SVN_HOME/bin:$JAXWS_HOME/bin:$EMACS_HOME/bin:$MYSQL_HOME/bin

# Set specific command prompt.
export PS1="\u@\h:\w$ "

# Source aliases if they exist.
if [ -f "${HOME}"/.bash_alias ] ; then
    source "${HOME}"/.bash_alias
fi

cd /c
