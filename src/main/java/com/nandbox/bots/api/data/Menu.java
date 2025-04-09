///**
// *
// */
//package com.nandbox.bots.api.data;
//
//import net.minidev.json.JSONArray;
//import net.minidev.json.JSONObject;
//
///**
// * @author Ahmed A. El-Malatawy
// *
// */
//public class Menu {
//
//	private static final String KEY_MENU_REF = "menu_ref";
//	private static final String KEY_ROWS = "rows";
//	private static final String KEY_MENU_ID = "menu_id";
//	private static final String KEY_MENU_GROUP= "menu_group";
//
//	private String menuRef;
//	private Row[] rows;
//	private String menuId;
//	private String menuGroup;
//	public Menu() {
//
//	}
//
//	public Menu(JSONObject obj) {
//
//		JSONArray rowsArrayObj = (JSONArray) obj.get(KEY_ROWS);
//		this.rows = new Row[rowsArrayObj.size()];
//		for (int i = 0; i < rowsArrayObj.size(); i++) {
//			rows[i] = new Row((JSONObject) rowsArrayObj.get(i));
//		}
//		this.menuRef = (String) obj.get(KEY_MENU_REF);
//		this.menuId = (String) obj.get(KEY_MENU_ID);
//		this.menuGroup = (String) obj.get(KEY_MENU_GROUP);
//	}
//
//	public JSONObject toJsonObject() {
//		JSONObject obj = new JSONObject();
//
//		if (menuRef != null)
//			obj.put(KEY_MENU_REF, menuRef);
//		if (menuGroup != null)
//			obj.put(KEY_MENU_GROUP, menuGroup);
//		if (menuId != null)
//			obj.put(KEY_MENU_ID, menuId);
//		if (rows != null) {
//
//			JSONArray rowsArrayObj = new JSONArray();
//			for (int i = 0; i < rows.length; i++) {
//				rowsArrayObj.add(rows[i].toJsonObject());
//			}
//			obj.put(KEY_ROWS, rowsArrayObj);
//		}
//		return obj;
//
//	}
//
//	public String getMenuId() {
//		return menuId;
//	}
//
//	public void setMenuId(String menuId) {
//		this.menuId = menuId;
//	}
//
//	public void setMenuGroup(String menuGroup) {
//		this.menuGroup = menuGroup;
//	}
//
//	public String getMenuGroup() {
//		return menuGroup;
//	}
//
//	/**
//	 * @return the menuRef
//	 */
//	public String getMenuRef() {
//		return menuRef;
//	}
//
//	/**
//	 * @param menuRef
//	 *            the menuRef to set
//	 */
//	public void setMenuRef(String menuRef) {
//		this.menuRef = menuRef;
//	}
//
//	/**
//	 * @return the rows
//	 */
//	public Row[] getRows() {
//		return rows;
//	}
//
//	/**
//	 * @param rows
//	 *            the rows to set
//	 */
//	public void setRows(Row[] rows) {
//		this.rows = rows;
//	}
//
//}
