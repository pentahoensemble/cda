package pt.webdetails.cda.dataaccess.hci;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import pt.webdetails.cda.connections.hci.HciFacetResultModel;
import pt.webdetails.cda.connections.hci.HciResultsItemModel;

public class HciFacetTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 3748256425284462447L;

	private List<HciFacetResultModel> searchFacetData = new ArrayList<HciFacetResultModel>();

	private static Map<Integer, Method> columnMap = new HashMap<Integer, Method>();
	
	public HciFacetTableModel() {}
	
	public HciFacetTableModel(List<HciFacetResultModel> searchFacetData) {
		this.searchFacetData = searchFacetData;
		if (columnMap.isEmpty()) {
			int count = 0;
			try {
				for (PropertyDescriptor pd : Introspector.getBeanInfo(HciResultsItemModel.class).getPropertyDescriptors()) {
					if (pd.getReadMethod() != null && !"HciFacetResultModel".equals(pd.getName()) && !"class".equals(pd.getName())) {
						  columnMap.put(count++, pd.getReadMethod());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getRowCount() {
		return searchFacetData.size();
	}

	@Override
	public int getColumnCount() {
		return searchFacetData.get(0).getClass().getDeclaredFields().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HciFacetResultModel item = searchFacetData.get(rowIndex);
		Object value =  null;
		try {
			value = columnMap.get(columnIndex).invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
