package org.aries.jms.util;


public class SampleUsingCompositeData {

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
//              ObjectName  consumerMgrMonitorName
//                  = new ObjectName(MQObjectName.CONSUMER_MANAGER_MONITOR_MBEAN_NAME);
//          
//          //  Invoke operation
//              Object  result
//                  = mbsc.invoke(consumerMgrMonitorName, ConsumerOperations.GET_CONSUMER_INFO, null, null);
//          
//          //  Typecast result to an array of composite data objects
//              CompositeData  cdArray[] = (CompositeData[])result;
//          
//          //  Step through array, printing information for each consumer
//              
//              if ( cdArray == null )
//                { System.out.println( "No message consumers found" );
//                }
//              else
//                { for ( int  i = 0; i < cdArray.length; ++i )
//                    { CompositeData  cd = cdArray[i];
//                      
//                      System.out.println( "Consumer ID: "
//                                               + cd.get(ConsumerInfo.CONSUMER_ID) );
//                      System.out.println( "User: "
//                                               + cd.get(ConsumerInfo.USER) );
//                      System.out.println( "Host: "
//                                               + cd.get(ConsumerInfo.HOST) );
//                      System.out.println( "Connection service: "
//                                               + cd.get(ConsumerInfo.SERVICE_NAME) );
//                      System.out.println( "Acknowledgment mode: "
//                                               + cd.get(ConsumerInfo.ACKNOWLEDGE_MODE_LABEL) );
//                      System.out.println( "Destination name: "
//                                               + cd.get(ConsumerInfo.DESTINATION_NAME) );
//                      System.out.println( "Destination type: "
//                                               + cd.get(ConsumerInfo.DESTINATION_TYPE) );
//                    }
//                }
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
