package com.joyveb.report.gens.terrace.service.impl;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyveb.report.gens.terrace.dao.iface.StatWagerdataDAO;
import com.joyveb.report.gens.terrace.domain.StatWagerdata;
import com.joyveb.report.gens.terrace.domain.StatWagerdataExample;
import com.joyveb.report.gens.terrace.domain.StatWagerdataKey;
import com.joyveb.report.gens.terrace.service.iface.StatWagerdataService;
import com.joyveb.bizcom.vo.ListVO;

@Slf4j
@Service(StatWagerdataServiceImpl.BEAN_NAME)
public class StatWagerdataServiceImpl implements StatWagerdataService{
    public static final String BEAN_NAME="statwagerdataCRUDService";

    @Setter
    @Getter
	@Autowired
    private StatWagerdataDAO statwagerdataDAO;
	
	@Override
	public void create(StatWagerdata statwagerdata) {
		log.trace("create StatWagerdata:{}",statwagerdata);
		statwagerdataDAO.insertSelective(statwagerdata);
	}

	@Override
	public void update(StatWagerdata statwagerdata) {
		log.trace("update StatWagerdata:{}",statwagerdata);
		statwagerdataDAO.updateByPrimaryKeySelective(statwagerdata);
	}
	
	@Override
	public void updateList(List<StatWagerdata>statwagerdatas)
	{
		log.trace("deleteList:{}",statwagerdatas);
		statwagerdataDAO.batchUpdateSelective(statwagerdatas);
	}

	@Override
	public ListVO<StatWagerdata> list(int pageStart, int pageCount) {
		log.debug("list StatWagerdata:pageStart={},pageCount={}",pageStart,pageCount);
		StatWagerdataExample example=new StatWagerdataExample() ;
		List<StatWagerdata> vos=statwagerdataDAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount);
		ListVO<StatWagerdata> rets=new ListVO<StatWagerdata>(statwagerdataDAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<StatWagerdata> listByExample(StatWagerdataExample example,int pageStart, int pageCount) {
		if(example==null)
		{
			return list(pageStart,pageCount);
		}
		log.trace("listByExample StatWagerdata:pageStart={},pageCount={}",pageStart,pageCount);
		List<StatWagerdata> vos=statwagerdataDAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount,example.getOrderByClause());
		ListVO<StatWagerdata> rets=new ListVO<StatWagerdata>(statwagerdataDAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<StatWagerdata> listrec(int rowstart, int count) {
		log.debug("list StatWagerdata:pageStart={},pageCount={}",rowstart,count);
		StatWagerdataExample example=new StatWagerdataExample() ;
		List<StatWagerdata> vos=statwagerdataDAO.findByPage(example, rowstart, rowstart+count);
		ListVO<StatWagerdata> rets=new ListVO<StatWagerdata>(statwagerdataDAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public ListVO<StatWagerdata> listrecByExample(StatWagerdataExample example,int rowstart, int count) {
		if(example==null)
		{
			return listrec(rowstart,count);
		}
		log.trace("listByExample StatWagerdata:pageStart={},pageCount={}",rowstart,count);
		List<StatWagerdata> vos=statwagerdataDAO.findByPage(example, rowstart, rowstart+count,example.getOrderByClause());
		ListVO<StatWagerdata> rets=new ListVO<StatWagerdata>(statwagerdataDAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public void deleteList(List<StatWagerdata>statwagerdatas)
	{
		log.trace("deleteList:{}",statwagerdatas);
		statwagerdataDAO.batchDeleteSelective(statwagerdatas);
	}


	@Override
	public StatWagerdata get(StatWagerdataKey primaryKey) {
		log.trace("get byKey:primaryKey={}",primaryKey);
		return statwagerdataDAO.selectByPrimaryKey(primaryKey);
	}
	
	@Override
	public void deleteByKey(StatWagerdataKey primaryKey) {
		log.trace("deleteByKey primaryKey={}",primaryKey);
		statwagerdataDAO.deleteByPrimaryKey(primaryKey);
	}

	@Override
	public void deleteByExample(StatWagerdataExample example) {
		log.trace("deleteByExample ={}",example);
		statwagerdataDAO.deleteByExample(example);
	}

}
