# base-files version 3.1-4

# ~/.bash_profile: executed by bash for login shells.

# Assign specific home directory
#export HOME=/home/Administrator

if [ -e /etc/bash.bashrc ] ; then
  source /etc/bash.bashrc
fi

if [ -e ${HOME}/.bashrc ] ; then
  source ${HOME}/.bashrc
fi

# Set PATH so it includes user's private bin if it exists
# if [ -d ${HOME}/bin ] ; then
#   PATH=${HOME}/bin:${PATH}
# fi

# Set MANPATH so it includes users' private man if it exists
# if [ -d ${HOME}/man ]; then
#   MANPATH=${HOME}/man:${MANPATH}
# fi

# Set INFOPATH so it includes users' private info if it exists
# if [ -d ${HOME}/info ]; then
#   INFOPATH=${HOME}/info:${INFOPATH}
# fi


