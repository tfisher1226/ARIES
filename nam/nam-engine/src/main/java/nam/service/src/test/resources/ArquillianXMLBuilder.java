package nam.service.src.test.resources;

import aries.codegen.AbstractDataLayerFileBuilder;
import aries.codegen.util.Buf;
import aries.generation.engine.GenerationContext;
import aries.generation.model.ModelFile;


public class ArquillianXMLBuilder extends AbstractDataLayerFileBuilder {

	public ArquillianXMLBuilder(GenerationContext context) {
		super(context);
	}

	public ModelFile buildFile() throws Exception {
		return buildFile(false);
	}
	
	public ModelFile buildFile(boolean isTest) throws Exception {
		ModelFile modelFile = createTestResourcesFile("arquillian.xml");
		modelFile.setTextContent(getFileContent(isTest));
		return modelFile;
	}
	
	public String getFileContent(boolean isTest) throws Exception {
		Buf buf = new Buf();
		buf.put(generateXmlOpen(isTest));
		buf.put(generateXMLContent(isTest));
		buf.put(generateXmlClose());
		return buf.get();
	}

	protected String generateXmlOpen(boolean isTest) {
		Buf buf = new Buf();
		buf.putLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.putLine("");
		buf.putLine("<arquillian");
		buf.putLine("	xmlns=\"http://jboss.org/schema/arquillian\"");
		buf.putLine("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		buf.putLine("	xsi:schemaLocation=\"http://jboss.org/schema/arquillian http://jboss.org/schema/arquillian/arquillian_1_0.xsd\">");
		return buf.get();
	}

	protected String generateXMLContent(boolean isTest) {
		Buf buf = new Buf();
		buf.putLine("	");
		buf.putLine("	<defaultProtocol type=\"Servlet 3.0\" />");
		buf.putLine("	");
		buf.putLine("	<engine>");
		buf.putLine("		<!-- Uncomment the following to have test archives exported to the file system for inspection. -->");
		buf.putLine("		<property name=\"deploymentExportPath\">target/deployments</property>");
		buf.putLine("	</engine>"); 
		buf.putLine("	");		
		buf.putLine("	<container qualifier=\"jboss\" default=\"true\">");
		buf.putLine("		<configuration>");
		buf.putLine("			<property name=\"jbossHome\">${NODE_05}</property>");
		buf.putLine("		</configuration>");
		buf.putLine("	</container>");
		//buf.putLine("	");
		//buf.putLine("	<!-- If JBOSS_HOME environment variable exists, we can remove jbossHome variable  -->");
		buf.putLine("	");
		buf.putLine("	<group qualifier=\"serverGroup01\">");
		buf.putLine("		<container qualifier=\"hornetQ01_local\" mode=\"manual\">");
		//buf.putLine("			<!--");  
		//buf.putLine("			<protocol type=\"jmx-as7\">");
		//buf.putLine("				<property name=\"executionType\">REMOTE</property>");
		//buf.putLine("			</protocol>");
		//buf.putLine("			-->");
		buf.putLine("			");
		buf.putLine("			<configuration>");
		buf.putLine("				<property name=\"jbossHome\">${NODE_05}</property>");
		buf.putLine("				<property name=\"serverConfig\">standalone-hornetq-dedicated1.xml</property>");
		buf.putLine("				<property name=\"managementAddress\">${BIND_SERVER01}</property>");
		buf.putLine("				<property name=\"managementPort\">9999</property>");
		buf.putLine("				<property name=\"startupTimeoutInSeconds\">60</property>");
		buf.putLine("				");		
		buf.putLine("				<property name=\"javaVmArguments\">");
		buf.putLine("					-Xms256m");
		buf.putLine("					-Xmx2g"); 
		buf.putLine("					-XX:PermSize=256m");
		buf.putLine("					-XX:MaxPermSize=512m");
		buf.putLine("					-XX:NewRatio=4"); 
		buf.putLine("					-XX:SurvivorRatio=8"); 
		buf.putLine("					-Xbootclasspath/a:/software/jdk1.6.0_38_x86_64/lib/tools.jar");
		buf.putLine("					-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n");
		buf.putLine("					");
		buf.putLine("					-Djboss.bind.address=${BIND_SERVER01}"); 
		buf.putLine("					-Djboss.bind.address.management=${BIND_SERVER01}"); 
		buf.putLine("					-Djboss.partition.name=jbossas7clusterColocated"); 
		buf.putLine("					-Djboss.default.multicast.address=230.0.0.4"); 
		buf.putLine("					-Djboss.modules.system.pkgs=org.jboss.byteman");
		buf.putLine("					-Dcom.arjuna.ats.jta.supportSubtransactions=true");
		buf.putLine("					");						
		buf.putLine("					-Dorg.jboss.byteman.verbose=true");
		buf.putLine("					-Dorg.jboss.byteman.transform.all");
		buf.putLine("					-javaagent:${BYTEMAN_HOME}/lib/byteman.jar=boot:${BYTEMAN_HOME}/lib/byteman.jar,boot:${BYTEMAN_HOME}/lib/byteman-submit.jar,${BYTEMAN_HOME}/lib/byteman-bmunit.jar,listener:true,port:8888,prop:org.jboss.byteman.verbose=true");
		buf.putLine("					");
		buf.putLine("					-Dadmin.application_runtime_home=c:/workspace/ARIES/admin/runtime");
		buf.putLine("					-Dtx-manager.application_runtime_home=c:/workspace/ARIES/tx-manager/runtime");
		buf.putLine("					-Dbookshop2.buyer.application_runtime_home=c:/workspace/ARIES/bookshop2/bookshop2-buyer/runtime");
		buf.putLine("					-Dbookshop2.seller.application_runtime_home=c:/workspace/ARIES/bookshop2/bookshop2-seller/runtime");
		buf.putLine("					-Dbookshop2.shipper.application_runtime_home=c:/workspace/ARIES/bookshop2/bookshop2-shipper/runtime");
		buf.putLine("					-Dbookshop2.supplier.application_runtime_home=c:/workspace/ARIES/bookshop2/bookshop2-supplier/runtime");
		buf.putLine("				</property>");
		buf.putLine("			</configuration>");
		buf.putLine("		</container>");
		buf.putLine("	</group>");
		//buf.putLine("	");
		//buf.putLine("	<!--"); 
		//buf.putLine("	-javaagent:target/test-classes/byteman.jar=boot:target/test-classes/byteman.jar,listener:true,port:8888,prop:org.jboss.byteman.verbose=true");
		//buf.putLine("	-->");
		buf.putLine("	");
		buf.putLine("	<extension qualifier=\"byteman\">");
		buf.putLine("		<property name=\"autoInstallClientAgent\">false</property>");
		buf.putLine("		<property name=\"autoInstallContainerAgent\">false</property>");
		buf.putLine("		<property name=\"clientAgentPort\">9999</property>");
		buf.putLine("		<property name=\"containerAgentPort\">8888</property>");
		buf.putLine("	</extension>");
		return buf.get();
	}

	protected String generateXmlClose() {
		Buf buf = new Buf();
		buf.putLine("</arquillian>");
		return buf.get();
	}

	
	/*
	<!--  
	<group qualifier="serverGroup01">
		<container qualifier="hornetQ01_local" mode="manual">
			<configuration>
				<property name="jbossHome">${NODE_01}</property>
				<property name="serverConfig">standalone-hornetq-dedicated1.xml</property>
				<property name="managementAddress">${BIND_SERVER01}</property>
				<property name="managementPort">9999</property>
				<property name="startupTimeoutInSeconds">60</property>
	
				<property name="javaVmArguments">
					-Xms800m
					-Xmx2g 
					-XX:PermSize=256M
					-XX:MaxPermSize=512M
					-XX:NewRatio=4 
					-XX:SurvivorRatio=8 
					-XX:+UseFastAccessorMethods
					-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y
	
					-Djboss.bind.address=${BIND_SERVER01} 
					-Djboss.bind.address.management=${BIND_SERVER01} 
					-Djboss.partition.name=jbossas7clusterColocated 
					-Djboss.default.multicast.address=230.0.0.4 
					
					-Dbookshop2.buyer.application_runtime_home=c:/workspace/ARIES/bookshop2/bookshop2-buyer/runtime
				</property>
			</configuration>
		</container>
	
		<container qualifier="hornetQ01_remote" mode="manual">
			<configuration>
				<property name="jbossHome">${NODE_01}</property>
				<property name="serverConfig">standalone-hornetq-dedicated1.xml</property>
				<property name="managementAddress">${BIND_SERVER01}</property>
				<property name="managementPort">9999</property>
				<property name="javaVmArguments">-Djboss.bind.address=${BIND_SERVER01} -Djboss.bind.address.management=${BIND_SERVER01} -Djboss.partition.name=jbossas7clusterColocated -Djboss.default.multicast.address=230.0.0.4 -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n</property>
			</configuration>
		</container>
	</group>
	-->

	<!--  		
	<group qualifier="hornetQ01XXX">
		<container qualifier="target" mode="manual">
			<configuration>
				<property name="jbossHome">${NODE_01}</property>
				<property name="serverConfig">standalone-hornetq-dedicated1.xml</property>
				<property name="managementAddress">${BIND_SERVER01}</property>
				<property name="managementPort">9999</property>
				<property name="javaVmArguments">-Djboss.bind.address=${BIND_SERVER01} -Djboss.bind.address.management=${BIND_SERVER01} -Djboss.partition.name=jbossas7clusterColocated -Djboss.default.multicast.address=230.0.0.4 -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n</property>
			</configuration>
		</container>
		<container qualifier="backup" mode="manual">
			<configuration>
				<property name="jbossHome">${NODE_02}</property>
				<property name="serverConfig">standalone-hornetq-dedicated2.xml</property>
				<property name="managementAddress">${BIND_SERVER02}</property>
				<property name="managementPort">9999</property>
				<property name="javaVmArguments">-Djboss.bind.address=${BIND_SERVER02} -Djboss.bind.address.management=${BIND_SERVER02} -Djboss.partition.name=jbossas7clusterColocated -Djboss.default.multicast.address=230.0.0.4</property>
			</configuration>
		</container>
	</group>
	-->
	
		<!--  
		-XX:+UseCompressedOops
		-XX:+UseConcMarkSweepGC 
		-XX:+UseParNewGC 
		-XX:+DisableExplicitGC
		-XX:+UseCMSInitiatingOccupancyOnly 
		-XX:+CMSClassUnloadingEnabled
		-XX:+CMSScavengeBeforeRemark 
		-XX:CMSInitiatingOccupancyFraction=68
		-XX:+UseFastAccessorMethods
		-->
	
	
		<!-- 
		<property name="outputToConsole">true</property>
		<property name="adminUrl">t3://localhost:7001</property>
           <property name="adminUserName">weblogic</property>
           <property name="adminPassword">weblogic123</property>
           <property name="target">AdminServer</property>
		 -->
	 */

	
}
