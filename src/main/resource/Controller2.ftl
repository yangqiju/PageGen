package ${packageName}.ctrl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import cn.javass.spring.mvc.bind.annotation.RequestJsonParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${packageName}.domain.${domainClazz};
import ${packageName}.domain.${domainClazz}Example;
<#if clazzinfo.count <= 1 >
<#else>
import ${packageName}.domain.${domainClazz}Key;
</#if>

import ${packageName}.service.iface.${domainClazz}Service;
import com.joyveb.util.ExcelExportView;
import com.joyveb.util.FieldHelper;
import com.joyveb.util.IDGenerator;
import com.joyveb.util.JsonUtil;
import com.joyveb.util.PDFExportView;
import com.joyveb.bizcom.vo.ListVO;
import com.joyveb.bizcom.vo.ReturnVO;
@Controller
@Slf4j
@RequestMapping("/${domainObject}")
public class ${domainClazz}Ctrl {
	@Autowired
	private ${domainClazz}Service ${domainObject}Service;



	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO create(${domainClazz} ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		<#list clazzinfo.fields as field>
			<#if  field.name == "createdate" >
				${domainObject}.setCreatedate(new Date());
			</#if>
		</#list>
		<#if clazzinfo.primaryKey != "uuid" >
			${domainObject}Service.create(${domainObject});			
		<#else>
		if(StringUtils.isBlank(${domainObject}.getUuid()))
		{
			${domainObject}.setUuid(IDGenerator.nextID());
			${domainObject}Service.create(${domainObject});			
		}
		else
		{
			${domainObject}Service.update(${domainObject});
		}
		</#if>		
		return rvo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO update(${domainClazz} ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.update(${domainObject});
		return rvo;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	ListVO<${domainClazz}> list(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "condition", required = false) String condition
			,@RequestParam(value = "byrow", required = false) boolean byrow) {

		${domainClazz}Example example = null;
		sortcolumn = StringUtils.trimToNull(sortcolumn);
		condition = StringUtils.trimToNull(condition);
		if (sortcolumn != null) {
			example = new ${domainClazz}Example();
			if (desc) {
				example.setOrderByClause(sortcolumn + " DESC");
			} else {
				example.setOrderByClause(sortcolumn + " ASC");
			}
		}
		if (condition != null) {
			if (example == null) {
				example = new ${domainClazz}Example();
			}
			example.createCriteria().addCriterion(condition);
		}
		if(!byrow){
			if (example != null) {
				return ${domainObject}Service.listByExample(example, pageStart, pageCount);
			} else {
				return ${domainObject}Service.list(pageStart, pageCount);
			}
		}else{
			if (example != null) {
				return ${domainObject}Service.listrecByExample(example, pageStart, pageCount);
			} else {
				return ${domainObject}Service.listrec(pageStart, pageCount);
			}
		
		}
		
	}

	public ModelAndView responseList(ListVO<${domainClazz}> ret, String format) {
		if (ret != null) {
			Map model = new HashMap();
			model.put("listvo", ret);
			model.put("clazz", ${domainClazz}.class);
			model.put("filename", "${domainObject}");
			
			if ("pdf".equalsIgnoreCase(format)) {
				return new ModelAndView(new PDFExportView(), model);

			} else {
				return new ModelAndView(new ExcelExportView(), model);
			}
		}
		return null;
	}

	@RequestMapping(value = "/exportlist", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView exportList(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "format", required = false) String format) {
		return responseList(
				list(pageStart, pageCount, sortcolumn, desc, condition,false), format);

	}

@RequestMapping(value = "/exportsearch", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView exportsearch(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "sj", required = false) String sj) {
		ListVO<HashMap<String, String>> uuids=new ListVO<HashMap<String,String>>();
		
		if(sj!=null){
			log.debug("search sj:"+sj); 
			uuids=JsonUtil.json2Bean(sj, uuids.getClass());
		}
		return responseList(
				search(uuids, pageStart, pageCount, sortcolumn, desc, condition,false),
				format);

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody
	ListVO<${domainClazz}> search(
			@RequestBody ListVO<HashMap<String, String>> uuids,
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam("sortcolumn") String sortcolumn,
			@RequestParam("desc") boolean desc,
			@RequestParam(value = "condition", required = false) String condition
			,@RequestParam(value = "byrow", required = false) boolean byrow) {

		${domainClazz}Example example = new ${domainClazz}Example();
		HashMap<String, HashMap<String, Object>> betweenQ = new HashMap<String, HashMap<String, Object>>();
		${domainClazz}Example.Criteria c = example.createCriteria();

		sortcolumn = StringUtils.trimToNull(sortcolumn);
		condition = StringUtils.trimToNull(condition);

		if (sortcolumn != null) {
			if (desc) {
				example.setOrderByClause(sortcolumn + " DESC");
			} else {
				example.setOrderByClause(sortcolumn + " ASC");
			}
		}
		${domainClazz} obj = new ${domainClazz}();
		for (HashMap<String, String> map : uuids.getItems()) {
			String field = StringUtils.trimToEmpty(map.get("field"));
			String svalue = StringUtils.trimToEmpty(map.get("value"));

			if (StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(svalue)
					&& field.length() > 2) {
				String realField = null;
				if (field.startsWith("s_")) {
					realField = field.replaceFirst("s_", "");

					try {
						c.addCriterion(
								realField.toUpperCase() + " like",
								"%"
										+ FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(obj, FieldHelper
												.column2Field(realField)),
												svalue) + "%", realField);
					} catch (SecurityException e) {
					} catch (NoSuchFieldException e) {
					}} else if (field.startsWith("streq_")) {
					realField = field.replaceFirst("streq_", "");

					try {
						c.addCriterion(
								realField.toUpperCase() + "=",
								"'"
										+ FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(obj, FieldHelper
												.column2Field(realField)),
												svalue) + "'", realField);
					} catch (SecurityException e) {
					} catch (NoSuchFieldException e) {
					}
				} else if (field.startsWith("eq_")) {
					realField = field.replaceFirst("eq_", "");

					try {
						c.addCriterion(
								realField.toUpperCase() + "=",
								""
										+ FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(obj, FieldHelper
												.column2Field(realField)),
												svalue) + "", realField);
					} catch (SecurityException e) {
					} catch (NoSuchFieldException e) {
					}
				} else if (field.startsWith("ss_") && field.length() > 3) {
					realField = field.replaceFirst("ss_", "");
					HashMap<String, Object> omap = betweenQ.get(realField);
					if (omap == null) {
						omap = new HashMap<String, Object>();
						betweenQ.put(realField, omap);
					}
					try {
						omap.put("start", FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(obj, FieldHelper
												.column2Field(realField)), svalue));
						omap.put("realField", realField);
					} catch (SecurityException e) {
					} catch (NoSuchFieldException e) {
					}

				} else if (field.startsWith("se_") && field.length() > 3) {
					realField = field.replaceFirst("se_", "");
					HashMap<String, Object> omap = betweenQ.get(realField);
					if (omap == null) {
						omap = new HashMap<String, Object>();
						betweenQ.put(realField, omap);
					}
					try {
						omap.put("end", FieldHelper.getTypeField(FieldHelper
										.getDeclaredField(obj, FieldHelper
												.column2Field(realField)), svalue));
						omap.put("realField", realField);
					} catch (SecurityException e) {
					} catch (NoSuchFieldException e) {
					}
				}
			}
		}
		for (HashMap<String, Object> omap : betweenQ.values()) {
			Object start = omap.get("start");
			Object end = omap.get("end");
			String realField = (String) omap.get("realField");
			if (end != null) {
				c.addCriterion(realField.toUpperCase() + " between", start,
						end, realField);
			} else if (start != null) {
				c.addCriterion(realField.toUpperCase() + " >=", start,
						realField);
			}
		}
		if (condition != null) {
			c.addCriterion(condition);
		}

		if(!byrow){
			return ${domainObject}Service.listByExample(example, pageStart, pageCount);
		}else{
			return ${domainObject}Service.listrecByExample(example, pageStart, pageCount);
		}
	}


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody
	<#if clazzinfo.count <= 1 >
	${domainClazz} get(@RequestParam("${primaryKey}") String ${primaryKey}) {
		return ${domainObject}Service.get(${primaryKey});
	}
	<#else>
	${domainClazz} get(${domainClazz}Key ${primaryKey}) {
		return ${domainObject}Service.get(${primaryKey});
	}
	</#if>	
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	<#if clazzinfo.count <= 1 >
	ReturnVO deleteByKey(@RequestParam("${primaryKey}") String ${primaryKey}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.deleteByKey(${primaryKey});
		return rvo;
	}
	<#else>
	ReturnVO deleteByKey(${domainClazz}Key ${primaryKey}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.deleteByKey(${primaryKey});
		return rvo;
	}
	</#if>
	

	@RequestMapping(value = "/deleteList", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO deleteList(@RequestJsonParam(value = "items") List<${domainClazz}> items) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.deleteList(items);
		return rvo;
	}

}
