# User dependent .bashrc file

# Don't put duplicate lines in the history.
export HISTCONTROL=ignoredups

# Cygwin specific settings
export CYGWIN=nodosfilewarning

export ANT_HOME=/c/software/apache-ant-1.8.2
export SVN_HOME=/c/software/svn-win32-1.4.6
export JAXWS_HOME=/c/software/jaxws-ri/2.1.2
export EMACS_HOME=/c/software/emacs-21.31
export EMACS_HOME=/c/software/emacs-24.5

# Java setup
export JAVA_HOME=/c/software/jdk1.7.0_80_64
#export JAVA_HOME=/c/software/jdk1.6.0_38_x86_64
export JRE_HOME=/c/software/jre1.7.0_80_64
#export JRE_HOME=/c/software/jre1.6.0_38_x86_64

# Maven setup
export MAVEN_HOME=/c/software/apache-maven-3.3.3
#export MAVEN_HOME=/c/software/apache-maven-3.1.1
#export MAVEN_HOME=/c/software/apache-maven-3.0.4
#export MAVEN_HOME=/c/software/apache-maven-2.2.1
#export MAVEN_HOME=/c/software/apache-maven-2.0.9
export MAVEN_OPTS="-Xmx1024m -Xms512m -XX:PermSize=128m -XX:MaxPermSize=256m"
export M2_REPO="/c/Users/tfisher/.m2/repository2"
export M2_HOME=$MAVEN_HOME

# JBoss setup
export JBOSS_HOME=/c/software/jboss-eap-6.4
#export JBOSS_HOME=/c/software/jboss-as-7.1.1.Final
#export JBOSS_HOME=/c/software/jboss-6.1.0.Final
#export JBOSS_HOME=/c/software/jboss-5.1.0.GA

# JBoss datagrid setup
export JDG_HOME=/c/software/jboss-datagrid-6.5.0-quickstarts

# Tomcat setup
export TOMCAT_HOME=/c/software/apache-tomcat-6.0.26
export CATALINA_HOME=$TOMCAT_HOME

# Hadoop setup
export HADOOP_HOME=/c/software/hadoop-2.6.0

# MySQL setup
export MYSQL_HOME=/c/software/mysql-5.1.54-win32

# Python setup
export PYTHON_HOME=/c/software/Python-3.4.3

# Ruby setup
export RUBY_HOME=/c/software/ruby-2.2.2

# Set specific command path locations. 
export PATH=$CYGWIN_HOME/bin:$CYGWIN_HOME/usr/bin:$JAVA_HOME/bin:$ANT_HOME/bin:$MAVEN_HOME/bin:$SVN_HOME/bin:$JAXWS_HOME/bin:$JDG_HOME/bin:$EMACS_HOME/bin:$HADOOP_HOME/bin:$MYSQL_HOME/bin:$PYTHON_HOME/bin:$RUBY_HOME/bin:$PATH

# Set specific command prompt.
export PS1="\u@\h:\w$ "

# Source aliases if they exist.
if [ -f "${HOME}"/.bash_alias ] ; then
    source "${HOME}"/.bash_alias
fi

