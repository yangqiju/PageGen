package com.joyveb.report.gens.terrace.service.iface;

import java.util.List;

import com.joyveb.report.gens.terrace.domain.ParaMerchant;
import com.joyveb.report.gens.terrace.domain.ParaMerchantExample;

import com.joyveb.bizcom.vo.ListVO;
public interface ParaMerchantService {
	// create
	public void create(ParaMerchant paramerchant);

	//update
	public void update(ParaMerchant paramerchant);
	
	//updateList
	public void updateList(List<ParaMerchant> paramerchant);

	//list all
	public ListVO<ParaMerchant> list(int pageStart, int pageCount);
	
	public ListVO<ParaMerchant> listByExample(ParaMerchantExample example,int pageStart, int pageCount) ;

	//list all
	public ListVO<ParaMerchant> listrec(int pageStart, int pageCount);
	
	public ListVO<ParaMerchant> listrecByExample(ParaMerchantExample example,int pageStart, int pageCount) ;

	public ParaMerchant get(String primaryKey);
	
	public void deleteByKey(String primaryKey);
	
	public void deleteByExample(ParaMerchantExample exmple);
	
	public void deleteList(List<ParaMerchant> paramerchant);
}
