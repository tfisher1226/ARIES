package org.aries.jms.util;



public class SampleRegisterNotificationListener {

//    public static void  main (String[]  args)
//    { 
//      try
//        { //  Create administration connection factory
//              AdminConnectionFactory  acf = new AdminConnectionFactory();
//          
//          //  Get JMX connector, supplying user name and password
//              JMXConnector  jmxc = acf.createConnection("AliBaba", "sesame");
//          
//          //  Get MBean server connection
//              MBeanServerConnection  mbsc = jmxc.getMBeanServerConnection();
//          
//          //  Create object name for service manager monitor MBean
//              ObjectName  svcMgrMonitorName
//                  = new ObjectName( MQObjectName.SERVICE_MANAGER_MONITOR_MBEAN_NAME );
//          
//          //  Create notification filter
//              NotificationFilterSupport  myFilter = new NotificationFilterSupport();
//              myFilter.enableType(ServiceNotification.SERVICE_PAUSE);
//              myFilter.enableType(ServiceNotification.SERVICE_RESUME);
//          
//          //  Create notification listener
//              ServiceNotificationListener  myListener = new ServiceNotificationListener();
//              mbsc.addNotificationListener(svcMgrMonitorName, myListener, myFilter, null);
//              
//              ...
//        }
//      
//      catch (Exception  e)
//        { System.out.println( "Exception occurred: " + e.toString() );
//          e.printStackTrace();
//        }
//      
//      finally
//        { if ( jmxc != null )
//            { try
//                { jmxc.close();
//                }
//              catch (IOException ioe)
//                { System.out.println( "I/O exception occurred: " + ioe.toString() );
//                  ioe.printStackTrace();
//                }
//            }
//        }
//    }
    
}
