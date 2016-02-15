package pt.webdetails.cda.dataaccess.hci;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pt.webdetails.cda.connections.hci.HciFacetResultModel;
import pt.webdetails.cda.connections.hci.HciFacetTermCount;

public class HciFacetTableModel extends AbstractTableModel {
	
	private static final Log logger = LogFactory.getLog( HciFacetTableModel.class );
	
	private static int columnCount;
	private static final long serialVersionUID = 3748256425284462447L;

	private HciFacetResultModel searchFacetData = new HciFacetResultModel();

	private static LinkedHashMap<Integer, Method> columnMap = new LinkedHashMap<Integer, Method>();
	private static List<String> columnNames = new ArrayList<String>();
	
	public HciFacetTableModel() {}
	
	public HciFacetTableModel(HciFacetResultModel searchFacetData) {
		this.searchFacetData = searchFacetData;
		if (columnMap.isEmpty()) {
			int count = 0;
			try {
				for (PropertyDescriptor pd : Introspector.getBeanInfo(HciFacetTermCount.class).getPropertyDescriptors()) {
					if (pd.getReadMethod() != null && !"HciFacetTermCount".equals(pd.getName()) && !"class".equals(pd.getName())) {
							columnNames.add(pd.getName().toUpperCase());
							columnMap.put(count++, pd.getReadMethod());
					}
				}
				columnCount = count;
			} catch (Exception e) {
				logger.debug("Error in parsing search data results: " + e.getLocalizedMessage());
			}
		}
	}

	@Override
	public int getRowCount() {
		return searchFacetData.getTermCounts().size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}
	
	@Override
	public String getColumnName(int index) {
	    return columnNames.get(index);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HciFacetTermCount item = searchFacetData.getTermCounts().get(rowIndex);
		Object value =  null;
		try {
			value = columnMap.get(columnIndex).invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
