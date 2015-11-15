package org.aries.jms.util;


public class SampleGetAttributes {

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
//			//  Create object name
//			ObjectName  destConfigName
//			= MQObjectName.createDestinationConfig(DestinationType.QUEUE, "MyQueue");
//
//			//  Create array of attribute names
//			String  attrNames[] = 
//			{ DestinationAttributes.MAX_NUM_PRODUCERS,
//					DestinationAttributes.MAX_NUM_ACTIVE_CONSUMERS
//			};
//
//			//  Get attributes
//			AttributeList  attrList = mbsc.getAttributes(destConfigName, attrNames);
//
//			//  Extract and print attribute values
//
//			Object  attrValue;
//
//			attrValue = attrList.get(0).getValue();
//			System.out.println( "Maximum number of producers: " + attrValue.toString() );
//
//			attrValue = attrList.get(1).getValue();
//			System.out.println( "Maximum number of active consumers: " + attrValue.toString() );
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
