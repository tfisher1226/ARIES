package nam.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.util.BaseUtil;

import nam.model.Interaction;


public class InteractionUtil extends BaseUtil {
	
	public static final Interaction[] VALUES_ARRAY = new Interaction[] {
		Interaction.ACCEPT,
		Interaction.CALL,
		Interaction.LISTEN,
		Interaction.INVOKE,
		Interaction.POST,
		Interaction.PUBLISH,
		Interaction.RECEIVE,
		Interaction.REPLY,
		Interaction.SEND,
		Interaction.SUBSCRIBE
	};
	
	public static final List<Interaction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static Interaction getInteraction(int ordinal) {
		if (ordinal == Interaction.ACCEPT.ordinal())
			return Interaction.ACCEPT;
		if (ordinal == Interaction.CALL.ordinal())
			return Interaction.CALL;
		if (ordinal == Interaction.LISTEN.ordinal())
			return Interaction.LISTEN;
		if (ordinal == Interaction.INVOKE.ordinal())
			return Interaction.INVOKE;
		if (ordinal == Interaction.POST.ordinal())
			return Interaction.POST;
		if (ordinal == Interaction.PUBLISH.ordinal())
			return Interaction.PUBLISH;
		if (ordinal == Interaction.RECEIVE.ordinal())
			return Interaction.RECEIVE;
		if (ordinal == Interaction.REPLY.ordinal())
			return Interaction.REPLY;
		if (ordinal == Interaction.SEND.ordinal())
			return Interaction.SEND;
		if (ordinal == Interaction.SUBSCRIBE.ordinal())
			return Interaction.SUBSCRIBE;
		return null;
	}
	
	public static String toString(Interaction interaction) {
		return interaction.name();
	}
	
	public static String toString(Collection<Interaction> interactionList) {
		StringBuffer buf = new StringBuffer();
		Iterator<Interaction> iterator = interactionList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Interaction interaction = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(interaction);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<Interaction> interactionList) {
		Collections.sort(interactionList, createInteractionComparator());
	}
	
	public static Collection<Interaction> sortRecords(Collection<Interaction> interactionCollection) {
		List<Interaction> list = new ArrayList<Interaction>(interactionCollection);
		Collections.sort(list, createInteractionComparator());
		return list;
	}
	
	public static Comparator<Interaction> createInteractionComparator() {
		return new Comparator<Interaction>() {
			public int compare(Interaction interaction1, Interaction interaction2) {
				String text1 = interaction1.value();
				String text2 = interaction2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
