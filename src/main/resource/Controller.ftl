package ${packageName}.ctrl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
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
import com.joyveb.common.gens.spring.util.IDGenerator;
<#else>
import ${packageName}.domain.${domainClazz}Key;
</#if>

import com.joyveb.common.gens.spring.util.CondiSetExample;
import com.joyveb.common.gens.spring.bean.RequestJsonParam;
import ${packageName}.service.iface.${domainClazz}Service;
import com.joyveb.common.gens.spring.util.ExcelExportView;
import com.joyveb.common.gens.spring.util.FieldHelper;
import com.joyveb.common.gens.spring.util.JsonUtil;
import com.joyveb.common.gens.spring.util.PDFExportView;
import com.joyveb.common.gens.bizcom.vo.ListVO;
import com.joyveb.common.gens.bizcom.vo.ReturnVO;
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
			<#elseif field.name=="uuid">
			${domainObject}.setUuid(IDGenerator.nextID());
			</#if>
		</#list>
			${domainObject}Service.create(${domainObject});			
		return rvo;
	}
	
	@RequestMapping(value = "/jsonCreate", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO JsonCreate(@RequestJsonParam(value = "data")${domainClazz} ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		<#list clazzinfo.fields as field>
			<#if  field.name == "createdate" >
				${domainObject}.setCreatedate(new Date());
			<#elseif field.name=="uuid">
			${domainObject}.setUuid(IDGenerator.nextID());
			</#if>
		</#list>
			${domainObject}Service.create(${domainObject});			
		return rvo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO update(${domainClazz} ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.update(${domainObject});
		return rvo;
	}
	
	@RequestMapping(value = "/jsonUpdate", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO jsonUpdate(@RequestJsonParam(value="data") ${domainClazz} ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.update(${domainObject});
		return rvo;
	}
	
	@RequestMapping(value = "/updateList", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO jsonUpdate(@RequestJsonParam(value="data") List<${domainClazz}> ${domainObject}) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		${domainObject}Service.updateList(${domainObject});
		return rvo;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	ListVO<${domainClazz}> list(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "byrow", required = false) boolean byrow) {

		${domainClazz}Example example = new ${domainClazz}Example();
		sortcolumn = StringUtils.trimToNull(sortcolumn);
		try {
			CondiSetExample.setExample(example, sortcolumn, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!byrow){
				return ${domainObject}Service.listByExample(example, pageStart, pageCount);
		}else{
				return ${domainObject}Service.listrecByExample(example, pageStart, pageCount);
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
			@RequestParam(value = "format", required = false) String format) {
		return responseList(
				list(pageStart, pageCount, sortcolumn, desc,false), format);

	}

@RequestMapping(value = "/exportsearch", method = RequestMethod.GET)
	public @ResponseBody
	ModelAndView exportsearch(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "sj", required = false) String sj) {
		ListVO<HashMap<String, String>> uuids=new ListVO<HashMap<String,String>>();
		
		if(sj!=null){
			log.debug("search sj:"+sj); 
			uuids=JsonUtil.json2Bean(sj, uuids.getClass());
		}
		return responseList(
				search(uuids, pageStart, pageCount, sortcolumn, desc,false),
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
			@RequestParam(value = "byrow", required = false) boolean byrow) {

		${domainClazz}Example example = new ${domainClazz}Example();
		${domainClazz} bean = new ${domainClazz}();
		try {
			CondiSetExample.setExample(example, sortcolumn, desc, uuids, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!byrow){
			return ${domainObject}Service.listByExample(example, pageStart, pageCount);
		}else{
			return ${domainObject}Service.listrecByExample(example, pageStart, pageCount);
		}
	}
	
	@RequestMapping(value = "/jsonSearch", method = RequestMethod.POST)
	public @ResponseBody
	ListVO<${domainClazz}> jsonSearch(
			@RequestJsonParam("data") ListVO<HashMap<String, String>> uuids,
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam("sortcolumn") String sortcolumn,
			@RequestParam("desc") boolean desc,
			@RequestParam(value = "byrow", required = false) boolean byrow) {

		${domainClazz}Example example = new ${domainClazz}Example();
		${domainClazz} bean = new ${domainClazz}();
		try {
			CondiSetExample.setExample(example, sortcolumn, desc, uuids, bean);
		} catch (Exception e) {
			e.printStackTrace();
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
