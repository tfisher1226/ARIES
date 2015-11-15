package org.aries.jms.util;


public class SampleGetAttribute {

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
//          //  Create object name
//              ObjectName  destConfigName
//                  = MQObjectName.createDestinationConfig(DestinationType.QUEUE, "MyQueue");
//          
//          //  Get and print attribute value
//              Integer  attrValue
//                  = (Integer)mbsc.getAttribute(destConfigName, DestinationAttributes.MAX_NUM_PRODUCERS);
//              System.out.println( "Maximum number of producers: " + attrValue );
//          
//          //  Close JMX connector
//              jmxc.close();
//        }
//      
//      catch (Exception  e)
//        { System.out.println( "Exception occurred: " + e.toString() );
//          e.printStackTrace();
//        }
//    }
    
}
