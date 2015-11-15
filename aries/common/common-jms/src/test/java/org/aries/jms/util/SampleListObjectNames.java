package org.aries.jms.util;


public class SampleListObjectNames {

//	public static void  main (String[]  args)
//	{ 
//		try
//		{ //  Create administration connection factory
//			AdminConnectionFactory  acf = new AdminConnectionFactory();
//
//			//  Get JMX connector, supplying user name and password
//			JMXConnector  jmxc = acf.createConnection("AliBaba", "sesame");
//
//			//  Get MBean server connection
//			MBeanServerConnection  mbsc = jmxc.getMBeanServerConnection();
//
//			//  Create object name for destination manager monitor MBean
//			ObjectName  destMgrMonitorName
//			= new ObjectName(MQObjectName.DESTINATION_MANAGER_MONITOR_MBEAN_NAME);
//
//			//  Get destination object names
//			ObjectName  destNames[] = mbsc.invoke(destMgrMonitorName,
//					DestinationOperations.GET_DESTINATIONS,
//					null,
//					null);
//
//			//  Step through array of object names, printing information for each destination
//
//			System.out.println( "Listing destinations: " );
//
//			ObjectName  eachDestName;
//			Object      attrValue;
//
//			for ( int i = 0; i < destNames.length; ++i )
//			{ eachDestName = destNames[i];
//
//			attrValue = mbsc.getAttribute(eachDestName, DestinationAttributes.NAME);
//			System.out.println( "\tName: " + attrValue );
//
//			attrValue = mbsc.getAttribute(eachDestName, DestinationAttributes.TYPE);
//			System.out.println( "\tTypeYPE: " + attrValue );
//
//			attrValue = mbsc.getAttribute(eachDestName, DestinationAttributes.STATE_LABEL);
//			System.out.println( "\tState: " + attrValue );
//
//			System.out.println( "" );
//			}
//
//			//  Close JMX connector
//			jmxc.close();
//		}
//
//		catch (Exception  e)
//		{ System.out.println( "Exception occurred: " + e.toString() );
//		e.printStackTrace();
//		}
//	}

}
