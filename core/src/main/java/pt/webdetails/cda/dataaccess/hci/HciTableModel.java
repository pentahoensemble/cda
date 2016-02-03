package pt.webdetails.cda.dataaccess.hci;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import pt.webdetails.cda.connections.hci.HciResultsItemModel;

public class HciTableModel extends AbstractTableModel {
	
	private static int columnCount;
	private static int metadataRefCount;
	private static final long serialVersionUID = 327163363729345509L;
	
	private List<HciResultsItemModel> searchData = new ArrayList<HciResultsItemModel>();

	private static LinkedHashMap<Integer, Method> columnMap = new LinkedHashMap<Integer, Method>();
	
	public HciTableModel() {}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public HciTableModel(List<HciResultsItemModel> searchData) {
		this.searchData = searchData;
		if (columnMap.isEmpty()) {
			int count = 0;
			try {
				for (PropertyDescriptor pd : Introspector.getBeanInfo(HciResultsItemModel.class).getPropertyDescriptors()) {
					if (pd.getReadMethod() != null && !"HciResultsItemModel".equals(pd.getName()) && !"class".equals(pd.getName())) {
						String name = pd.getName();
						if (pd.getName().equals("metadata")) {
							metadataRefCount = count;
							LinkedHashMap<String, List<String>> metadata = (LinkedHashMap<String, List<String>>) pd.getReadMethod().invoke(searchData.get(0));
							for (String property : metadata.keySet()) {
								columnMap.put(count++, pd.getReadMethod());
							}
						} else {
							  columnMap.put(count++, pd.getReadMethod());
						}
					}
				}
				columnCount = count;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getRowCount() {
		return searchData.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HciResultsItemModel item = searchData.get(rowIndex);
		Object value =  null;
		try {
			value = columnMap.get(columnIndex).invoke(item);
			if (value instanceof LinkedHashMap) {
				LinkedHashMap<String, List<String>> metadata = (LinkedHashMap<String, List<String>>) value;
				List<List<String>> metadataList = new ArrayList<List<String>>(metadata.values());
				value = metadataList.get(columnIndex-metadataRefCount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
