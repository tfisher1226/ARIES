#!/bin/sh

#/bin/sh run.sh -c default -b `hostname` 

/bin/sh run.sh -c default -b `hostname` <&- >> ../log/jboss-console.log 2>&1 & echo $! > .pid &

#/bin/sh run.sh -c default -b ec2-184-73-28-177.compute-1.amazonaws.com > ~/jbossesb.log &


#
# These settings below are recommended for eclipse startup.  Look into using these for JBoss... 
#
# -vmargs -Xloggc:c:tempe.txt -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution -verbose:gc -XX:+UseParallelGC  
#

