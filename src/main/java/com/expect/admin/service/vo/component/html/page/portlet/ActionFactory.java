//package com.expect.admin.service.vo.component.html.page.portlet;
//
//import java.util.List;
//
//import com.expect.admin.service.vo.component.html.ButtonVo;
//import com.expect.admin.service.vo.component.html.page.portlet.ActionVo.Builder;
//
///**
// * Action操作的Vo
// */
//public class ActionFactory {
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param autoJudge
//	 *            是否需要自动判断包含批量删除和增加按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean autoJudge) {
//		ActionVo.Builder builder = new Builder();
//		builder.setAutoJudge(autoJudge);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param autoJudge
//	 *            是否需要自动判断包含批量删除和增加按钮
//	 * @param export
//	 *            是否需要导出按钮
//	 * @param query
//	 *            是否需要查询按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean autoJudge, boolean export, boolean query) {
//		ActionVo.Builder builder = new Builder();
//		builder.setAutoJudge(autoJudge).setExport(export).setQuery(query);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @param export
//	 *            是否需要导出按钮
//	 * @param query
//	 *            是否需要查询按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete, boolean export, boolean query) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete).setExport(export).setQuery(query);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @param button
//	 *            附加按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete, ButtonVo button) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete).addButton(button);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @param export
//	 *            是否需要导出按钮
//	 * @param query
//	 *            是否需要查询按钮
//	 * @param button
//	 *            附加按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete, boolean export, boolean query, ButtonVo button) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete).setExport(export).setQuery(query).addButton(button);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @param buttons
//	 *            附加按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete, List<ButtonVo> buttons) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete).setButtons(buttons);
//		return builder.create();
//	}
//
//	/**
//	 * 创建ActionVo实例
//	 * 
//	 * @param save
//	 *            是否需要增加按钮
//	 * @param delete
//	 *            是否需要删除按钮
//	 * @param export
//	 *            是否需要导出按钮
//	 * @param query
//	 *            是否需要查询按钮
//	 * @param buttons
//	 *            附加按钮
//	 * @return ActionVo实例
//	 */
//	public static ActionVo getInstance(boolean save, boolean delete, boolean export, boolean query,
//			List<ButtonVo> buttons) {
//		ActionVo.Builder builder = new Builder();
//		builder.setSave(save).setDelete(delete).setExport(export).setQuery(query).setButtons(buttons);
//		return builder.create();
//	}
//}
