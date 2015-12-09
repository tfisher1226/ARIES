package nam.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TreePrinter implements PhaseListener {

	private static Log log = LogFactory.getLog(TreePrinter.class);

	private long startTime;

	private long endTime;

	public int count;

	public int indent;

	public static final int INDENTSIZE = 2;

	
	public void beforePhase(PhaseEvent PhaseEvent) {
	}

	public void afterPhase(PhaseEvent PhaseEvent) {
		count = 0;
		//System.out.println("");
		//System.out.println("Component tree:");
		//printComponentTree(FacesContext.getCurrentInstance().getViewRoot());
		//System.out.println("Component tree size: "+count);
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;    
	}

	public void printComponentTree(UIComponent comp){
		printComponentInfo(comp);
		Collection<UIComponent> complist = comp.getChildren();
		if (complist.size() > 0)
			indent++;
		Iterator<UIComponent> iterator = complist.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			UIComponent uiComponent = (UIComponent) iterator.next();
			printComponentTree(uiComponent);
			if (i+1 == complist.size())
				indent--;
		}

	}

	public void printComponentInfo(UIComponent comp){
		if (comp.getId() == null) {
			System.out.println("UIViewRoot" + " " + "(" + comp.getClass().getName() + ")");
		} else {
			//printIndent();
			//System.out.println("|");
			printIndent();
			System.out.println(comp.getId() + " " + "(" + comp.getClass().getName() + ")");
			count++;
		}  
	}

	public void printIndent() {
		for (int i=0; i < indent; i++ )  
			for (int j=0; j < INDENTSIZE; j++)  
				System.out.print(" ");          
	}

}
