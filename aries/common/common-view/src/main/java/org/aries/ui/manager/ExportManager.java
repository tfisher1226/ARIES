package org.aries.ui.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Interpolator;
import org.jboss.seam.core.Manager;
import org.jboss.seam.document.ByteArrayDocumentData;
import org.jboss.seam.document.DocumentData;
import org.jboss.seam.document.DocumentStore;
import org.jboss.seam.excel.ExcelFactory;
import org.jboss.seam.excel.ExcelWorkbook;
import org.jboss.seam.excel.ExcelWorkbookException;
import org.jboss.seam.excel.css.CSSNames;
import org.jboss.seam.excel.css.CSSParser;
import org.jboss.seam.excel.css.ColumnStyle;
import org.jboss.seam.excel.css.StyleMap;
import org.jboss.seam.excel.exporter.ExcelExporter;
import org.jboss.seam.excel.ui.ExcelComponent;
import org.jboss.seam.excel.ui.UICell;
import org.jboss.seam.excel.ui.UIColumn;
import org.jboss.seam.excel.ui.UIWorkbook;
import org.jboss.seam.excel.ui.UIWorksheet;
import org.jboss.seam.log.Log;
import org.jboss.seam.navigation.Pages;
import org.richfaces.component.UIDataTable;


@AutoCreate
@Name("org.sgiusa.exportManager")
@Scope(ScopeType.SESSION)
@SuppressWarnings("serial")
public class ExportManager implements Serializable {

	@Logger
	private Log log;

	@In(required = false)
	private ExcelExporter excelExporter;

	private ExcelWorkbook excelWorkbook;
	
	private Map<Integer, Integer> columnWidths = new HashMap<Integer, Integer>();
	

	public String exportToPDF() {
		return "/reports/memberListPDF.xhtml";
	}

	public String exportToXLS(String tableId) {
		//excelExporter = BeanContext.get("org.jboss.seam.excel.exporter.excelExporter");
		//excelExporter.export(tableId);
		export(tableId);
		return null;
	}

	public String exportToExcel() {
		return "/reports/memberListXLS.xhtml";
	}

	public void export(String dataTableId) {
		excelWorkbook = ExcelFactory.instance().getExcelWorkbook("");

		CSSParser parser = new CSSParser();

		// Gets the datatable
		UIDataTable dataTable = (UIDataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(dataTableId);
		if (dataTable == null) {
			throw new ExcelWorkbookException(Interpolator.instance().interpolate("Could not find data table with id #0", dataTableId));
		}

		// Inits the workbook and worksheet
		UIWorkbook uiWorkbook = new UIWorkbook();
		excelWorkbook.createWorkbook(uiWorkbook);
		UIWorksheet uiWorksheet = new UIWorksheet();
		uiWorkbook.getChildren().add(uiWorksheet);
		uiWorksheet.setStyle(CSSParser.getStyle(dataTable));
		uiWorksheet.setStyleClass(CSSParser.getStyleClass(dataTable));
		excelWorkbook.createOrSelectWorksheet(uiWorksheet);

		// Saves the datatable var
		String dataTableVar = dataTable.getVar();
		Object oldValue = FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(dataTableVar);

		// Processes the columns
		List<javax.faces.component.UIColumn> columns = ExcelComponent.getChildrenOfType(dataTable.getChildren(), javax.faces.component.UIColumn.class);
		columnWidths = parseColumnWidths(uiWorksheet);
		int col = 0;
		for (javax.faces.component.UIColumn column : columns) {
			ColumnStyle columnStyle = new ColumnStyle(parser.getCascadedStyleMap(column));
			boolean cssExport = columnStyle.export == null || columnStyle.export;
			if (column.isRendered() && cssExport) {
				uiWorksheet.getChildren().add(column);
				Iterator iterator = UIWorksheet.unwrapIterator(dataTable.getValue());
				processColumn(column, iterator, dataTableVar, col++);
				excelWorkbook.nextColumn();
			}
		}

		// Restores the data table var
		if (oldValue == null) {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(dataTableVar);
		} else {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(dataTableVar, oldValue);
		}

		// Redirects to the generated document
		redirectExport();
	}

	private Map<Integer, Integer> parseColumnWidths(UIWorksheet worksheet) {
		Map<Integer, Integer> columnWidths = new HashMap<Integer, Integer>();
		CSSParser parser = new CSSParser();

		StyleMap styleMap = parser.getCascadedStyleMap(worksheet);
		for (Map.Entry<String, Object> entry : styleMap.entrySet()) {
			String key = entry.getKey();
			if (key.startsWith(CSSNames.COLUMN_WIDTHS)) {
				String columnIndexString = key.substring(CSSNames.COLUMN_WIDTHS.length());
				int columnIndex = Integer.parseInt(columnIndexString);
				columnWidths.put(columnIndex, (Integer) entry.getValue());
			}
		}
		return columnWidths;
	}
	 
	private void processColumn(javax.faces.component.UIColumn column, Iterator iterator, String var, int columnIndex) {
		// Process header facet
		UIComponent headerFacet = column.getFacet(UIColumn.HEADER_FACET_NAME);
		if (headerFacet != null && UIOutput.class.isAssignableFrom(headerFacet.getClass())) {
			List<UIOutput> headerOutputs = new ArrayList<UIOutput>();
			headerOutputs.add((UIOutput) headerFacet);
			processOutputs(column, headerOutputs);
		}

		// Process data
		while (iterator.hasNext()) {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(var, iterator.next());
			List<UIOutput> dataOutputs = ExcelComponent.getChildrenOfType(column.getChildren(), UIOutput.class);
			processOutputs(column, dataOutputs);
		}

		Integer columnWidth = columnWidths.get(columnIndex);
		if (columnWidth != null) {
			UIColumn uiColumn = new UIColumn();
			uiColumn.setStyle(CSSNames.COLUMN_WIDTH + ":" + columnWidth);
			excelWorkbook.applyColumnSettings(uiColumn);
		}
	}

	private void processOutputs(javax.faces.component.UIColumn column, List<UIOutput> outputs) {
		for (UIOutput output : outputs) {
			if (output.isRendered()) {
				UICell cell = new UICell();
				column.getChildren().add(cell);
				cell.setId(output.getId());
				cell.setValue(output.getValue());
				cell.setStyle(CSSParser.getStyle(output));
				cell.setStyleClass(CSSParser.getStyleClass(output));
				excelWorkbook.addItem(cell);
			}
		}
	}
	   
	private void redirectExport() {
		String viewId = Pages.getViewId(FacesContext.getCurrentInstance());
		String baseName = Pages.getCurrentBaseName();
		DocumentData documentData = new ByteArrayDocumentData(baseName, excelWorkbook.getDocumentType(), excelWorkbook.getBytes());
		String id = DocumentStore.instance().newId();
		String url = DocumentStore.instance().preferredUrlForContent(baseName, excelWorkbook.getDocumentType().getExtension(), id);
		url = Manager.instance().encodeConversationId(url, viewId);
		DocumentStore.instance().saveData(id, documentData);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			throw new ExcelWorkbookException(Interpolator.instance().interpolate("Could not redirect to #0", url), e);
		}
	}

}
