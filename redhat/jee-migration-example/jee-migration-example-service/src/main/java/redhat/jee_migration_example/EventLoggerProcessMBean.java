package redhat.jee_migration_example;

import javax.management.MXBean;


@MXBean
public interface EventLoggerProcessMBean {
	
	public static final String MBEAN_NAME = "redhat.jee_migration_example.eventLogger:name=EventLoggerProcessMBean";
	
}
