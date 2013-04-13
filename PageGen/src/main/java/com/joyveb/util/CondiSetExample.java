package com.joyveb.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.joyveb.bizcom.vo.ListVO;
import com.joyveb.util.FieldHelper;

/**
 * 
 * ��Ŀ��ƣ�SpringMVCTest ����ƣ�ClassTest
 * 
 * @Company: �������?�����޹�˾
 * @Copyright: Copyright (c) 2012
 * @Author�� ����� ����ʱ�䣺2014-3-14 ����7:16:04 �޸ı�ע��
 * @version
 * 
 */
public class CondiSetExample {

	/**
	 * @Description: ִ�в�ѯ����Example����
	 * @param @param example
	 * @param @param sortcolumn
	 * @param @param desc
	 * @param @param uuids
	 * @param @param bean
	 * @param @throws Exception �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public static void setExample(Object example, 
			String sortcolumn, boolean desc,
			ListVO<HashMap<String, String>> uuids, Object bean)
			throws Exception {
		Object exampleCriteria_ = invokeMethod(example,"createCriteria",new Object[]{});
		sortcolumn = StringUtils.trimToNull(sortcolumn);
		HashMap<String, HashMap<String, Object>> betweenQ = new HashMap<String, HashMap<String, Object>>();
		if (sortcolumn != null) {
			String tmp = null;
			if (desc) {
				tmp = sortcolumn + " DESC";
			} else {
				tmp = sortcolumn + " ASC";
			}
			invokeMethod(example, "setOrderByClause", new Object[] { tmp });
		}

		for (HashMap<String, String> map : uuids.getItems()) {
			String field = StringUtils.trimToEmpty(map.get("field"));
			String svalue = StringUtils.trimToEmpty(map.get("value"));
			if (StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(svalue)
					&& field.length() > 2) {
				String realField = null;
				String key = null;
				if (field.startsWith("s_")) {//��ͨ��ѯ����
					realField = field.replaceFirst("s_", "");
					key = FieldHelper.column2Field(realField);
					try {
						String condi1 = realField.toUpperCase() + " like";
						String condi2 = "%"
								+ FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(bean, FieldHelper
												.column2Field(realField)),
										svalue) + "%";
						invokeMethod(exampleCriteria_, "addCriterion",
								new Object[] { condi1, (Object) condi2, key });
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				} else if (field.startsWith("streq_")) {
					realField = field.replaceFirst("streq_", "");
					key = FieldHelper.column2Field(realField);
					try {
						String condi1 = realField.toUpperCase() + "=";
						String condi2 = "'"
								+ FieldHelper
										.getTypeField(FieldHelper
												.getDeclaredField(bean, key),
												svalue) + "'";
						invokeMethod(exampleCriteria_, "addCriterion",
								new Object[] { condi1, condi2, key });
					} catch (SecurityException e) {
					}
				} else if (field.startsWith("eq_")) {
					realField = field.replaceFirst("eq_", "");
					key = FieldHelper.column2Field(realField);
					try {
						String condi1 = realField.toUpperCase() + "=";
						String condi2 = ""
								+ FieldHelper
										.getTypeField(FieldHelper
												.getDeclaredField(bean, key),
												svalue) + "";
						invokeMethod(exampleCriteria_, "addCriterion",
								new Object[] { condi1, condi2, key });
					} catch (SecurityException e) {
					}
				} else if (field.startsWith("ss_") && field.length() > 3) {//ʱ���ѯ
					realField = field.replaceFirst("ss_", "");
					key = FieldHelper.column2Field(realField);
					HashMap<String, Object> omap = betweenQ.get(realField);
					if (omap == null) {
						omap = new HashMap<String, Object>();
						betweenQ.put(realField, omap);
					}
					try {
						omap.put(
								"start",
								FieldHelper.getTypeField(
										FieldHelper.getDeclaredField(bean, key),
										svalue));
						omap.put("realField", realField);
					} catch (SecurityException e) {
					}
				} else if (field.startsWith("se_") && field.length() > 3) {
					realField = field.replaceFirst("se_", "");
					key = FieldHelper.column2Field(realField);
					HashMap<String, Object> omap = betweenQ.get(realField);
					if (omap == null) {
						omap = new HashMap<String, Object>();
						betweenQ.put(realField, omap);
					}
					try {
						omap.put(
								"end",
								FieldHelper.getTypeField(
										FieldHelper.getDeclaredField(bean, key),
										svalue));
						omap.put("realField", realField);
					} catch (SecurityException e) {
					}
				}
			}
		}
		for (HashMap<String, Object> omap : betweenQ.values()) {
			Object start = omap.get("start");
			Object end = omap.get("end");
			String realField = (String) omap.get("realField");
			String condi1 = "";
			if (end != null) {
				condi1 = realField.toUpperCase() + " between";
				invokeMethod(exampleCriteria_, "addCriterion", new Object[] {
						condi1, start, end, realField });
			} else if (start != null) {
				condi1 = realField.toUpperCase() + " >=";
				invokeMethod(exampleCriteria_, "addCriterion", new Object[] {
						condi1, start, realField });
			}
		}
	}

	/**
	 * @Title: invokeMethod
	 * @param @param owner
	 * @param @param methodName
	 * @param @param args
	 * @param @return
	 * @param @throws Exception �趨�ļ�
	 * @return Object ��������
	 * @throws ͨ����ִ�з���
	 */
	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws Exception {
		Class<?> ownerClass = owner.getClass();
		Class<?>[] argsClass = new Class[args.length];
		if (args.length == 1) {
			argsClass[0] = String.class;
		} else if (args.length == 3) {
			argsClass[0] = String.class;
			argsClass[1] = Object.class;
			argsClass[2] = String.class;
		} else if (args.length == 4) {
			argsClass[0] = String.class;
			argsClass[1] = Object.class;
			argsClass[2] = Object.class;
			argsClass[3] = String.class;
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

}
