package org.aries.tx;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.util.FileUtils;
import org.aries.test.MavenResolver;
import org.aries.util.NameUtil;
import org.aries.util.ReflectionUtil;
import org.jboss.byteman.agent.submit.ScriptText;
import org.jboss.byteman.agent.submit.Submit;


public class BytemanUtil {

	private static Map<String, String> scriptFileTable = new HashMap<String, String>();
	

	public static void initialize() throws Exception {
		String userDir = System.getProperty("user.dir");
		String javaClassPath = userDir + "/target/test-classes";
		String destinationFile = javaClassPath + "/byteman.jar";
		destinationFile = NameUtil.normalizePath(destinationFile);
		System.setProperty("java.class.path", destinationFile);
		copyArtifact(destinationFile, "org.jboss.byteman", "byteman", "2.1.4.1");
	}

	public static void copyArtifact(String targetFilePath, String groupId, String artifactId, String version) throws IOException {
		File sourceFile = MavenResolver.getJavaArchiveFile(groupId, artifactId, version);
		String sourceFilePath = sourceFile.getAbsolutePath();
		FileUtils.getFileUtils().copyFile(sourceFilePath, targetFilePath, null, false);
	}


	public static void loadScripts(Class<?> classObject, String methodName) throws Exception {
		Method method = ReflectionUtil.findMethod(classObject, methodName);
		if (method == null)
			throw new IllegalStateException("Method not found: "+methodName);
		BytemanRule annotation = method.getAnnotation(BytemanRule.class);
		if (annotation != null) {
			String script = constructScriptText(new BytemanRule[] {annotation});
			loadScriptText(classObject, methodName, script);
			return;
		}
		//List<BytemanRule> bytemanRuleAnnotations = new ArrayList<BytemanRule>(); 
		Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
		for (Annotation declaredAnnotation : declaredAnnotations) {
			if (declaredAnnotation.getClass().equals(BytemanRule.class)) {
				BytemanRule bytemanRuleAnnotation = (BytemanRule) declaredAnnotation;
				String script = constructScriptText(new BytemanRule[] {bytemanRuleAnnotation});
				loadScriptText(classObject, methodName, script);
				//bytemanRuleAnnotations.add(bytemanRuleAnnotation);
			}
		}
	}

	public static void unloadScripts(Class<?> classObject, String methodName) throws Exception {
		unloadScriptText(classObject, methodName);
	}

	/**
	 * Loads a script supplied as a text String rather than via a file on disk.
	 * @param clazz the test class.
	 * @param testname the test name.
	 * @param scriptText the text of the rule or rules contained in the script.
	 */
	public static void loadScriptText(Class<?> clazz, String testname, String scriptText) throws Exception {
		String className = clazz.getName();
		String key = className + "+" + testname;
		Submit submit = new Submit("localhost", 8888);
		System.out.println("BytemanUtil: loading text script = " + key);
		List<ScriptText> scripts = new ArrayList<ScriptText>();
		ScriptText script = new ScriptText(key, scriptText);
		scripts.add(script);
		submit.addScripts(scripts);
		scriptFileTable.put(key, scriptText);
	}

	/**
	 * Unloads a script previously supplied as a text String.
	 * @param clazz the test class.
	 * @param testName the test name.
	 */
	public static void unloadScriptText(Class<?> clazz, String testName) throws Exception {
		String className = clazz.getName();
		String key = className + "+" + testName;
		String scriptText = scriptFileTable.remove(key);
		if (scriptText != null) {
			Submit submit = new Submit("localhost", 8888);
			System.out.println("BytemanUtil: unloading text script = " + key);
			List<ScriptText> scripts = new ArrayList<ScriptText>();
			ScriptText script = new ScriptText(key, scriptText);
			scripts.add(script);
			submit.deleteScripts(scripts);
		}
	}


	/**
	 * construct the text of a rule script from a set of BMRule annotations
	 *
	 * @param bmRules
	 * @return
	 */
	public static String constructScriptText(BytemanRule[] bmRules) {
		StringBuilder builder = new StringBuilder();
		builder.append("# Byteman autogenerated script ");
		for (BytemanRule bmRule : bmRules) {
			String scriptText = constructScriptText(bmRule);
			builder.append(scriptText);
		}
		return builder.toString();
	}

	public static String constructScriptText(Collection<BytemanRule> bmRules) {
		StringBuilder builder = new StringBuilder();
		builder.append("# Byteman autogenerated script ");
		Iterator<BytemanRule> iterator = bmRules.iterator();
		while (iterator.hasNext()) {
			BytemanRule bmRule = iterator.next();
			String scriptText = constructScriptText(bmRule);
			builder.append(scriptText);
		}
		return builder.toString();
	}
	
	private static String constructScriptText(BytemanRule bmRule) {
		StringBuilder builder = new StringBuilder();
		builder.append("\nRULE ");
		builder.append(bmRule.name());
		if (bmRule.isInterface()) {
			builder.append("\nINTERFACE ");
		} else {
			builder.append("\nCLASS ");
		}
		if (bmRule.isOverriding()) {
			builder.append("^");
		}
		builder.append(bmRule.targetClass());
		builder.append("\nMETHOD ");
		builder.append(bmRule.targetMethod());
		String location = bmRule.targetLocation();
		if (location != null && location.length() > 0) {
			builder.append('\n');
			// if we have AT XXX or AFTER XXX then use it
			// otherwise prepend AT
			if (!location.startsWith("AFTER ") && !location.startsWith("AT ") ) {
				builder.append("AT ");
			}
			builder.append(location);
		}
		String helper = bmRule.helper();
		if (helper != null && helper.length() > 0) {
			builder.append("\nHELPER ");
			builder.append(helper);
		}
		String binding = bmRule.binding();
		if (binding != null && binding.length() > 0) {
			builder.append("\nBIND " + binding);
		}
		builder.append("\nIF ");
		builder.append(bmRule.condition());
		builder.append("\nDO ");
		builder.append(bmRule.action());
		builder.append("\nENDRULE\n");
		return builder.toString();
	}

}