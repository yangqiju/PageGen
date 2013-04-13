package com.joyveb.report.gens.terrace.service.iface;

import java.util.List;

import com.joyveb.report.gens.terrace.domain.StatWagerdata;
import com.joyveb.report.gens.terrace.domain.StatWagerdataExample;
import com.joyveb.report.gens.terrace.domain.StatWagerdataKey;

import com.joyveb.bizcom.vo.ListVO;
public interface StatWagerdataService {
	// create
	public void create(StatWagerdata statwagerdata);

	//update
	public void update(StatWagerdata statwagerdata);
	
	//updateList
	public void updateList(List<StatWagerdata> statwagerdata);

	//list all
	public ListVO<StatWagerdata> list(int pageStart, int pageCount);
	
	public ListVO<StatWagerdata> listByExample(StatWagerdataExample example,int pageStart, int pageCount) ;

	//list all
	public ListVO<StatWagerdata> listrec(int pageStart, int pageCount);
	
	public ListVO<StatWagerdata> listrecByExample(StatWagerdataExample example,int pageStart, int pageCount) ;

	public StatWagerdata get(StatWagerdataKey primaryKey);
	
	public void deleteByKey(StatWagerdataKey primaryKey);
	
	public void deleteByExample(StatWagerdataExample exmple);
	
	public void deleteList(List<StatWagerdata> statwagerdata);
}
