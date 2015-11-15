package nam.ui.src.main.webapp.admin.data;

import aries.generation.engine.GenerationContext;


public class ElementRecordDialogXHTMLGenerator extends AbstractDataElementXHTMLGenerator {
	
	private ElementRecordDialogXHTMLBuilder elementRecordDialogXHTMLBuilder;
	
	
	public ElementRecordDialogXHTMLGenerator(GenerationContext context) {
		super(context);
		createBuilder();
	}

	protected void createBuilder() {
		elementRecordDialogXHTMLBuilder = new ElementRecordDialogXHTMLBuilder(context);
		setBuilder(elementRecordDialogXHTMLBuilder);
	}

}
