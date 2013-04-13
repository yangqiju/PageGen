package com.joyveb.report.gens.terrace.ctrl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import com.joyveb.spring.mvc.bind.annotation.RequestJsonParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.joyveb.util.criteria.CondiSetExample;
import com.joyveb.report.gens.terrace.domain.StatWagerdata;
import com.joyveb.report.gens.terrace.domain.StatWagerdataExample;
import com.joyveb.report.gens.terrace.domain.StatWagerdataKey;

import com.joyveb.report.gens.terrace.service.iface.StatWagerdataService;
import com.joyveb.util.ExcelExportView;
import com.joyveb.util.FieldHelper;
import com.joyveb.util.JsonUtil;
import com.joyveb.util.PDFExportView;
import com.joyveb.bizcom.vo.ListVO;
import com.joyveb.bizcom.vo.ReturnVO;
@Controller
@Slf4j
@RequestMapping("/statwagerdata")
public class StatWagerdataCtrl {
	@Autowired
	private StatWagerdataService statwagerdataService;



	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO create(StatWagerdata statwagerdata) {
		ReturnVO rvo = ReturnVO.SuccessVO;
			statwagerdataService.create(statwagerdata);			
		return rvo;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO update(StatWagerdata statwagerdata) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		statwagerdataService.update(statwagerdata);
		return rvo;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	ListVO<StatWagerdata> list(
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam(value = "sortcolumn", required = false) String sortcolumn,
			@RequestParam(value = "desc", required = false) boolean desc,
			@RequestParam(value = "byrow", required = false) boolean byrow) {

		StatWagerdataExample example = new StatWagerdataExample();
		sortcolumn = StringUtils.trimToNull(sortcolumn);
		try {
			CondiSetExample.setExample(example, sortcolumn, desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!byrow){
				return statwagerdataService.listByExample(example, pageStart, pageCount);
		}else{
				return statwagerdataService.listrecByExample(example, pageStart, pageCount);
		}
		
	}

	public ModelAndView responseList(ListVO<StatWagerdata> ret, String format) {
		if (ret != null) {
			Map model = new HashMap();
			model.put("listvo", ret);
			model.put("clazz", StatWagerdata.class);
			model.put("filename", "statwagerdata");
			
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
	ListVO<StatWagerdata> search(
			@RequestBody ListVO<HashMap<String, String>> uuids,
			@RequestParam("start") int pageStart,
			@RequestParam("count") int pageCount,
			@RequestParam("sortcolumn") String sortcolumn,
			@RequestParam("desc") boolean desc,
			@RequestParam(value = "byrow", required = false) boolean byrow) {

		StatWagerdataExample example = new StatWagerdataExample();
		StatWagerdata bean = new StatWagerdata();
		try {
			CondiSetExample.setExample(example, sortcolumn, desc, uuids, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!byrow){
			return statwagerdataService.listByExample(example, pageStart, pageCount);
		}else{
			return statwagerdataService.listrecByExample(example, pageStart, pageCount);
		}
	}


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody
	StatWagerdata get(StatWagerdataKey primaryKey) {
		return statwagerdataService.get(primaryKey);
	}
	

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO deleteByKey(StatWagerdataKey primaryKey) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		statwagerdataService.deleteByKey(primaryKey);
		return rvo;
	}
	

	@RequestMapping(value = "/deleteList", method = RequestMethod.POST)
	public @ResponseBody
	ReturnVO deleteList(@RequestJsonParam(value = "items") List<StatWagerdata> items) {
		ReturnVO rvo = ReturnVO.SuccessVO;
		statwagerdataService.deleteList(items);
		return rvo;
	}

}
