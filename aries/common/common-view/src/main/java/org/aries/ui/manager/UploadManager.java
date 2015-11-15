package org.aries.ui.manager;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.ui.Display;
import org.aries.ui.util.SeamConversationHelper;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;



@ConversationScoped
@Named("uploadManager")
@SuppressWarnings("serial")
public class UploadManager implements Serializable {

	protected Log log = LogFactory.getLog(UploadManager.class);

	@Inject
	private Display display;

	private int fileCount = 1;

	private int uploadCount;

	private String fileName;
	
	private byte[] fileData;

	private String fileDataAsText;

	private ArrayList<UploadedImage> files = new ArrayList<UploadedImage>();
	
	
	@Inject
	private SeamConversationHelper seamConversationHelper;


	public UploadManager() {
	}
	
	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public int getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(int uploadCount) {
		this.uploadCount = uploadCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	public String getFileDataAsText() {
		return fileDataAsText;
	}

	public void setFileDataAsText(String fileDataAsText) {
		this.fileDataAsText = fileDataAsText;
	}

	public void uploadListener(FileUploadEvent event) throws Exception {
		seamConversationHelper.logStatus();
		display.setModule("newApplication");

		UploadedFile item = event.getUploadedFile();
		String msg = "File: '" + item.getName() + "' was uploaded";
		display.info(msg);
		System.out.println(msg);

		try {
			UploadedImage file = new UploadedImage();
	        file.setLength(item.getData().length);
	        file.setName(item.getName());
	        file.setData(item.getData());
	        files.add(file);

			this.fileName = item.getName();
			byte[] fileData = file.getData();
			this.fileDataAsText = new String(fileData);
			this.fileData = fileData;
			this.uploadCount++;

//			File file = new File(item.getName());
//			this.fileName = file.getAbsolutePath();
//			this.fileName = item.getName();
//			//String name = FilenameUtils.getName(item.getFileName());
//			byte[] fileData = FileUtils.readFileToByteArray(file);
//			this.fileDataAsText = new String(fileData);
//			this.fileData = fileData;
//			//contentType = item.getContentType();
//			this.uploadCount++;

		} catch (Exception e) {
			display.error(e);
			//log.error(e);
		}
	}
	
}
