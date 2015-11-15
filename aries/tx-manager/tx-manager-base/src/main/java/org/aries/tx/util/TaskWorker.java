package org.aries.tx.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class TaskWorker implements Runnable {

	private static Log log = LogFactory.getLog(TaskWorker.class);
	
    private TaskManager taskManager ;

    
    TaskWorker(TaskManager taskManager) {
        this.taskManager = taskManager ;
    }

    /**
     * Execute the tasks.
     */
    public void run() {
        while (true) {
            Task task = taskManager.getTask() ;
            if (task == null) {
                break ;
            }

            try {
                task.executeTask() ;
            } catch (Throwable e) {
                log.error("Error", e);
            }
        }
    }

}
