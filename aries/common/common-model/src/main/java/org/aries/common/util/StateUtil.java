package org.aries.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.aries.common.State;
import org.aries.util.BaseUtil;


public class StateUtil extends BaseUtil {
	
	public static final State[] VALUES_ARRAY = new State[] {
		State.AL,
		State.AK,
		State.AZ,
		State.AR,
		State.CA,
		State.CO,
		State.CT,
		State.DE,
		State.FL,
		State.GA,
		State.HI,
		State.ID,
		State.IL,
		State.IN,
		State.IA,
		State.KS,
		State.KY,
		State.LA,
		State.ME,
		State.MD,
		State.MA,
		State.MI,
		State.MN,
		State.MS,
		State.MO,
		State.MT,
		State.NE,
		State.NV,
		State.NH,
		State.NJ,
		State.NM,
		State.NY,
		State.NC,
		State.ND,
		State.OH,
		State.OK,
		State.OR,
		State.PA,
		State.RI,
		State.SC,
		State.SD,
		State.TN,
		State.TX,
		State.UT,
		State.VT,
		State.VA,
		State.WA,
		State.WV,
		State.WI,
		State.WY
	};
	
	public static final List<State> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
	
	
	public static State getState(int ordinal) {
		if (ordinal == State.AL.ordinal())
			return State.AL;
		if (ordinal == State.AK.ordinal())
			return State.AK;
		if (ordinal == State.AZ.ordinal())
			return State.AZ;
		if (ordinal == State.AR.ordinal())
			return State.AR;
		if (ordinal == State.CA.ordinal())
			return State.CA;
		if (ordinal == State.CO.ordinal())
			return State.CO;
		if (ordinal == State.CT.ordinal())
			return State.CT;
		if (ordinal == State.DE.ordinal())
			return State.DE;
		if (ordinal == State.FL.ordinal())
			return State.FL;
		if (ordinal == State.GA.ordinal())
			return State.GA;
		if (ordinal == State.HI.ordinal())
			return State.HI;
		if (ordinal == State.ID.ordinal())
			return State.ID;
		if (ordinal == State.IL.ordinal())
			return State.IL;
		if (ordinal == State.IN.ordinal())
			return State.IN;
		if (ordinal == State.IA.ordinal())
			return State.IA;
		if (ordinal == State.KS.ordinal())
			return State.KS;
		if (ordinal == State.KY.ordinal())
			return State.KY;
		if (ordinal == State.LA.ordinal())
			return State.LA;
		if (ordinal == State.ME.ordinal())
			return State.ME;
		if (ordinal == State.MD.ordinal())
			return State.MD;
		if (ordinal == State.MA.ordinal())
			return State.MA;
		if (ordinal == State.MI.ordinal())
			return State.MI;
		if (ordinal == State.MN.ordinal())
			return State.MN;
		if (ordinal == State.MS.ordinal())
			return State.MS;
		if (ordinal == State.MO.ordinal())
			return State.MO;
		if (ordinal == State.MT.ordinal())
			return State.MT;
		if (ordinal == State.NE.ordinal())
			return State.NE;
		if (ordinal == State.NV.ordinal())
			return State.NV;
		if (ordinal == State.NH.ordinal())
			return State.NH;
		if (ordinal == State.NJ.ordinal())
			return State.NJ;
		if (ordinal == State.NM.ordinal())
			return State.NM;
		if (ordinal == State.NY.ordinal())
			return State.NY;
		if (ordinal == State.NC.ordinal())
			return State.NC;
		if (ordinal == State.ND.ordinal())
			return State.ND;
		if (ordinal == State.OH.ordinal())
			return State.OH;
		if (ordinal == State.OK.ordinal())
			return State.OK;
		if (ordinal == State.OR.ordinal())
			return State.OR;
		if (ordinal == State.PA.ordinal())
			return State.PA;
		if (ordinal == State.RI.ordinal())
			return State.RI;
		if (ordinal == State.SC.ordinal())
			return State.SC;
		if (ordinal == State.SD.ordinal())
			return State.SD;
		if (ordinal == State.TN.ordinal())
			return State.TN;
		if (ordinal == State.TX.ordinal())
			return State.TX;
		if (ordinal == State.UT.ordinal())
			return State.UT;
		if (ordinal == State.VT.ordinal())
			return State.VT;
		if (ordinal == State.VA.ordinal())
			return State.VA;
		if (ordinal == State.WA.ordinal())
			return State.WA;
		if (ordinal == State.WV.ordinal())
			return State.WV;
		if (ordinal == State.WI.ordinal())
			return State.WI;
		if (ordinal == State.WY.ordinal())
			return State.WY;
		return null;
	}
	
	public static String toString(State state) {
		return state.name();
	}
	
	public static String toString(Collection<State> stateList) {
		StringBuffer buf = new StringBuffer();
		Iterator<State> iterator = stateList.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			State state = iterator.next();
			if (i > 0)
				buf.append(", ");
			String text = toString(state);
			buf.append(text);
		}
		String text = StringEscapeUtils.escapeJavaScript(buf.toString());
		return text;
	}
	
	public static void sortRecords(List<State> stateList) {
		Collections.sort(stateList, createStateComparator());
	}
	
	public static Collection<State> sortRecords(Collection<State> stateCollection) {
		List<State> list = new ArrayList<State>(stateCollection);
		Collections.sort(list, createStateComparator());
		return list;
	}
	
	public static Comparator<State> createStateComparator() {
		return new Comparator<State>() {
			public int compare(State state1, State state2) {
				String text1 = state1.value();
				String text2 = state2.value();
				int status = text1.compareTo(text2);
				return status;
			}
		};
	}
	
}
