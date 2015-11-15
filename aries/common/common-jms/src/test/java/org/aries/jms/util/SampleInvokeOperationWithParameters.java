package org.aries.jms.util;


public class SampleInvokeOperationWithParameters {

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
//			ObjectName  destMgrConfigName 
//			= new ObjectName(MQObjectName.DESTINATION_MANAGER_CONFIG_MBEAN_NAME);
//
//			//  Create and populate attribute list
//
//			AttributeList  attrList = new AttributeList();
//			Attribute      attr;
//
//			attr = new Attribute(DestinationAttributes.MAX_NUM_PRODUCERS, 25);
//			attrList.add(attr);
//
//			attr = new Attribute(DestinationAttributes.MAX_NUM_ACTIVE_CONSUMERS, 50);
//			attrList.add(attr);
//
//			//  Create operation's parameter and signature arrays
//
//			Object  opParams[] = { DestinationType.QUEUE,
//					"MyQueue",
//					attrList
//			};
//
//			String  opSig[] = { String.class.getName(),
//					String.class.getName(),
//					attrList.getClass().getName()
//			};
//
//			//  Invoke operation
//			mbsc.invoke(destMgrConfigName, DestinationOperations.CREATE, opParams, opSig);
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
