package com.joyveb.report.gens.terrace.service.impl;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyveb.report.gens.terrace.dao.iface.ParaMerchantDAO;
import com.joyveb.report.gens.terrace.domain.ParaMerchant;
import com.joyveb.report.gens.terrace.domain.ParaMerchantExample;
import com.joyveb.report.gens.terrace.service.iface.ParaMerchantService;
import com.joyveb.bizcom.vo.ListVO;

@Slf4j
@Service(ParaMerchantServiceImpl.BEAN_NAME)
public class ParaMerchantServiceImpl implements ParaMerchantService{
    public static final String BEAN_NAME="paramerchantCRUDService";

    @Setter
    @Getter
	@Autowired
    private ParaMerchantDAO paramerchantDAO;
	
	@Override
	public void create(ParaMerchant paramerchant) {
		log.trace("create ParaMerchant:{}",paramerchant);
		paramerchantDAO.insertSelective(paramerchant);
	}

	@Override
	public void update(ParaMerchant paramerchant) {
		log.trace("update ParaMerchant:{}",paramerchant);
		paramerchantDAO.updateByPrimaryKeySelective(paramerchant);
	}
	
	@Override
	public void updateList(List<ParaMerchant>paramerchants)
	{
		log.trace("deleteList:{}",paramerchants);
		paramerchantDAO.batchUpdateSelective(paramerchants);
	}

	@Override
	public ListVO<ParaMerchant> list(int pageStart, int pageCount) {
		log.debug("list ParaMerchant:pageStart={},pageCount={}",pageStart,pageCount);
		ParaMerchantExample example=new ParaMerchantExample() ;
		List<ParaMerchant> vos=paramerchantDAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount);
		ListVO<ParaMerchant> rets=new ListVO<ParaMerchant>(paramerchantDAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<ParaMerchant> listByExample(ParaMerchantExample example,int pageStart, int pageCount) {
		if(example==null)
		{
			return list(pageStart,pageCount);
		}
		log.trace("listByExample ParaMerchant:pageStart={},pageCount={}",pageStart,pageCount);
		List<ParaMerchant> vos=paramerchantDAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount,example.getOrderByClause());
		ListVO<ParaMerchant> rets=new ListVO<ParaMerchant>(paramerchantDAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<ParaMerchant> listrec(int rowstart, int count) {
		log.debug("list ParaMerchant:pageStart={},pageCount={}",rowstart,count);
		ParaMerchantExample example=new ParaMerchantExample() ;
		List<ParaMerchant> vos=paramerchantDAO.findByPage(example, rowstart, rowstart+count);
		ListVO<ParaMerchant> rets=new ListVO<ParaMerchant>(paramerchantDAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public ListVO<ParaMerchant> listrecByExample(ParaMerchantExample example,int rowstart, int count) {
		if(example==null)
		{
			return listrec(rowstart,count);
		}
		log.trace("listByExample ParaMerchant:pageStart={},pageCount={}",rowstart,count);
		List<ParaMerchant> vos=paramerchantDAO.findByPage(example, rowstart, rowstart+count,example.getOrderByClause());
		ListVO<ParaMerchant> rets=new ListVO<ParaMerchant>(paramerchantDAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public void deleteList(List<ParaMerchant>paramerchants)
	{
		log.trace("deleteList:{}",paramerchants);
		paramerchantDAO.batchDeleteSelective(paramerchants);
	}


	@Override
	public ParaMerchant get(String primaryKey) {
		log.trace("get byKey:primaryKey={}",primaryKey);
		return paramerchantDAO.selectByPrimaryKey(primaryKey);
	}
	
	@Override
	public void deleteByKey(String primaryKey) {
		log.trace("deleteByKey primaryKey={}",primaryKey);
		paramerchantDAO.deleteByPrimaryKey(primaryKey);
	}

	@Override
	public void deleteByExample(ParaMerchantExample example) {
		log.trace("deleteByExample ={}",example);
		paramerchantDAO.deleteByExample(example);
	}

}
