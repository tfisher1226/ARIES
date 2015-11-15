package nam.ui.workspace;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import nam.model.Import;
import nam.model.util.ImportUtil;
import nam.ui.design.WorkspaceManager;
import nam.ui.tree.ModelTreeNode;
import nam.ui.tree.ModelTreeObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.ui.AbstractViewManager;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.intercept.PostConstruct;


@SessionScoped
@Named("modelFileInfoManager")
@SuppressWarnings("serial")
public class ModelFileInfoManager extends AbstractViewManager implements Serializable {

	private Import modelFile;

	private Import selectedModelFile;

	//@In(required = true, value = "org.sgiusa.modelFileService")
	//private ModelFileService modelFileService;

	@Inject
	private ModelFileTreeManager modelFileTreeManager;


	public ModelFileInfoManager() {
	}
	
	public WorkspaceManager getWorkspaceManager() {
		return BeanContext.getFromSession("workspaceManager");
	}
	
	@PostConstruct
	public void initialize() {
		display = BeanContext.getFromSession("display");
	}

	public void initialize(Import modelFile) throws IOException {
		this.modelFile = modelFile;
	}

	public void reloadContent() throws IOException {
		reloadContent(modelFile);
	}
	
	public void reloadContent(Import modelFile) throws IOException {
		populateContent(modelFile);
	}

	public void populateContent(Import document) throws IOException {
		//document.setData(content);
	}

	
	public Import getModelFile() {
		return modelFile;
	}

//	public ModelFile getModelFile(Long modelFileId) {
//		return modelFileTreeManager.getModelFile(modelFileId);
//	}

	@Observer(value="modelFileSelected")
	public void handleModelFileSelected() {
		modelFile = null;
		ModelTreeNode selectedNode = modelFileTreeManager.getSelectedNode();
		ModelTreeObject<?> data = selectedNode.getData();
		Object object = data.getObject();
		Import modelFile = (Import) data.getObject();
		
	}

	public void viewModelFile() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = modelFileTreeManager.getSelectedModelFile();
		String modelFileLabel = modelFileTreeManager.getSelectedNode().getLabel();
		setTitle("ModelFile Information");
		setHeader(modelFileLabel);
		display.info("Information for "+modelFileLabel);
	}

	public void newModelFile() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = new Import();
		setTitle("ModelFile Information");
		setHeader("New ModelFile for User: "+getWorkspaceManager().getUser().getUserName());
		display.info("Enter information for new ModelFile");
	}

	public void editModelFile() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = modelFileTreeManager.getSelectedModelFile();
		String modelFileLabel = modelFileTreeManager.getSelectedNode().getLabel();
		setTitle("ModelFile Information");
		setHeader(modelFileLabel);
		display.info("Specify information for "+modelFileLabel);
	}

//	public void saveModelFile() {
//		if (validateModelFile(modelFile)) {
//			if (modelFile.getId() == null) {
//				addModelFile(modelFile);
//			} else {
//				saveModelFile(modelFile);
//			}
//		}
//	}
	
	public void addModelFile(Import modelFile) {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		try {
			//TODO modelFileService.addModelFile(parentId, modelFile);
			//TODO events.raiseEvent("org.sgiusa.modelFileTreeChanged");
			//TODO modelFile = modelFileTreeManager.getSelectedModelFile();
		} catch (Exception e) {
			Throwable cause = ExceptionUtils.getRootCause(e);
			if (cause == null)
				cause = e;
			//cause.printStackTrace();
			log.error(cause.getMessage(), cause);
			display.error("Error: "+cause.getMessage());;
		}
	}

	public void saveModelFile(Import modelFile) {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		try {
			//TODO modelFileService.saveModelFile(modelFile);
			//TODO events.raiseEvent("org.sgiusa.modelFileTreeChanged");
			//TODO modelFile = modelFileTreeManager.getSelectedModelFile();
		} catch (Exception e) {
			Throwable cause = ExceptionUtils.getRootCause(e);
			if (cause == null)
				cause = e;
			//cause.printStackTrace();
			log.error(cause.getMessage(), cause);
			display.error("Error: "+cause.getMessage());;
		}
	}

	public boolean validateModelFile(Import modelFile) {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		try {
			Assert.notNull(modelFile, "ModelFile record not specified");
			if (StringUtils.isEmpty(ImportUtil.getImportedFileName(modelFile)))
				display.error("ModelFile name must be specified");
			return !display.messagesExist();
		} catch (Exception e) {
			display.warn(e.getMessage());
			return false;
		}
	}
	
	public void promptDeleteModelFile() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = modelFileTreeManager.getSelectedModelFile();
		if (modelFile == null) {
			display.error("An ModelFile must be selected.");
		}
	}
	
//	//TODO example of getting extended information on-demand
//	protected List<Member> getMembers(ModelFile modelFile) {
//		display.setModule("modelFileInfo");
//		try {
//			Assert.notNull(modelFile, "ModelFile should be specified");
//			List<Member> list = memberService.getMemberListByModelFileId(modelFile.getId());
//			return list;
//		} catch (Throwable e) {
//			log.error(e.getMessage(), e);
//			display.error("Error "+e);
//			return null;
//		}
//	}
	
	@Observer("deleteModelFile")
	public String deleteModelFile() {
		display.setModule("modelFileInfo");
		try {
			//TODO modelFileService.deleteModelFile(modelFile);
			//TODO events.raiseEvent("org.sgiusa.modelFileTreeChanged");
			//TODO modelFile = modelFileTreeManager.getSelectedModelFile();
			modelFileTreeManager.clearSelection();
			modelFile = null;
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			display.error("Error "+e);
			return null;
		}
	}

	
	public void buildModelFromFile() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = modelFileTreeManager.getSelectedModelFile();
		String modelFileLabel = modelFileTreeManager.getSelectedNode().getLabel();
		setTitle("ModelFile Information");
		setHeader(modelFileLabel);
		display.info("Specify information for "+modelFileLabel);

		modelFile = new Import();
		setTitle("ModelFile Information");
		setHeader("New ModelFile for User: "+getWorkspaceManager().getUser().getUserName());
		display.info("Enter information for new ModelFile");
	}

	public void generateSources() {
		display = BeanContext.getFromSession("display");
		display.setModule("modelFileInfo");
		modelFile = modelFileTreeManager.getSelectedModelFile();
		String modelFileLabel = modelFileTreeManager.getSelectedNode().getLabel();
		setTitle("ModelFile Information");
		setHeader(modelFileLabel);
		display.info("Specify information for "+modelFileLabel);

		modelFile = new Import();
		setTitle("ModelFile Information");
		setHeader("New ModelFile for User: "+getWorkspaceManager().getUser().getUserName());
		display.info("Enter information for new ModelFile");
	}

}

