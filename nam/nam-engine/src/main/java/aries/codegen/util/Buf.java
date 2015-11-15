package aries.codegen.util;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class Buf {

	public static String SPACE = " ";
	
	public static String TAB = "\t";
	
	public static String SEMICOLON = ";";

	public static String NEWLINE = "\n";
	//public static String NEWLINE = System.getProperty("line.separator");
	
	
	protected StringBuffer buf = new StringBuffer();

	protected int indentLevel;
	
	
	public Buf() {
		//nothing for now
	}

	public Buf(String text) {
		if (text != null)
			put(text);
	}

	public void clear() {
		buf.delete(0, buf.length()-1);
		indentLevel = 0;
	}

	public int length() {
		return buf.length();
	}

	public boolean isEmpty() {
		return length() == 0;
	}
	
	public void setIndent(int indentLevel) {
		this.indentLevel = indentLevel;
	}

	public  String get() {
		return new String(buf.toString());
	}

	public StringBuffer put(int indent, String text) {
		if (text == null)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(text, "\n");
		for (int i=0; tokenizer.hasMoreTokens(); i++) {
			String sourceCode = tokenizer.nextToken();
			if (i > 0)
				buf.append("\n");
			buf.append(getIndent(indent)).append(sourceCode);
		}
		//buf.append(getIndent(indent)).append(text);
		return buf;
	}

	public StringBuffer put(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(0)).append(text);
	}

	public StringBuffer put1(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(1)).append(text);
	}

	public StringBuffer put2(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(2)).append(text);
	}

	public StringBuffer put3(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(3)).append(text);
	}

	public StringBuffer put4(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(4)).append(text);
	}

	public StringBuffer putLine() {
		return buf.append(NEWLINE);
	}

	public StringBuffer putLine(String text) {
		if (text == null)
			return null;
		return buf.append(text).append(NEWLINE);
	}

	public StringBuffer putLine1(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(1)).append(text).append(NEWLINE);
	}

	public StringBuffer putLine2(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(2)).append(text).append(NEWLINE);
	}

	public StringBuffer putLine3(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(3)).append(text).append(NEWLINE);
	}

	public StringBuffer putLine4(String text) {
		if (text == null)
			return null;
		return buf.append(getIndent(4)).append(text).append(NEWLINE);
	}

	public StringBuffer putLines(int indent, String text) {
		if (text == null)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(text, NEWLINE);
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken();
			buf.append(getIndent(indent)).append(line).append(NEWLINE);
		}
		return buf;
	}

	public StringBuffer putLines(int indent, List<String> text) {
		if (text == null)
			return null;
		Iterator<String> iterator = text.iterator();
		while (iterator.hasNext()) {
			String line = iterator.next();
			buf.append(getIndent(indent)).append(line).append(NEWLINE);
		}
		return buf;
	}

	public StringBuffer putLine(int indent) {
		put(indent, "").append(NEWLINE);
		return buf;
	}

	public StringBuffer putLine(int indent, String text) {
		if (text == null)
			return null;
		put(indent, text).append(NEWLINE);
		return buf;
	}


	public void insertLine(String text) {
		if (text != null)
			insertLine(0, text);
	}
	
	public void insertLine1(String text) {
		if (text != null)
			insertLine(1, text);
	}

	public void insertLine2(String text) {
		if (text != null)
			insertLine(2, text);
	}

	public void insertLine3(String text) {
		insertLine(3, text);
	}

	public void insertLine(int indent, String text) {
		insertLine(0, indent, text);
	}
	
	public void insertLine(int offset, int indent, String text) {
		buf.insert(offset, getIndent(indent) + text + NEWLINE);
	}

	public String getIndent(int level) {
		StringBuffer buffer = new StringBuffer();
		int targetIndentLevel = indentLevel + level;
		for (int i=0 ; i < targetIndentLevel; i++) 
			buffer.append(TAB);
		return buffer.toString();
	}

	public String toString() {
		return buf.toString();
	}


}
