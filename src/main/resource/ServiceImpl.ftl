package ${packageName}.service.impl;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.dao.iface.${domainClazz}DAO;
import ${packageName}.domain.${domainClazz};
import ${packageName}.domain.${domainClazz}Example;
<#if clazzinfo.count <= 1 >
<#else>
import ${packageName}.domain.${domainClazz}Key;
</#if>
import ${packageName}.service.iface.${domainClazz}Service;
import com.joyveb.common.gens.bizcom.vo.ListVO;

@Slf4j
@Service(${domainClazz}ServiceImpl.BEAN_NAME)
public class ${domainClazz}ServiceImpl implements ${domainClazz}Service{
    public static final String BEAN_NAME="${domainObject}CRUDService";

    @Setter
    @Getter
	@Autowired
    private ${domainClazz}DAO ${domainObject}DAO;
	
	@Override
	public void create(${domainClazz} ${domainObject}) {
		log.trace("create ${domainClazz}:{}",${domainObject});
		${domainObject}DAO.insertSelective(${domainObject});
	}

	@Override
	public void update(${domainClazz} ${domainObject}) {
		log.trace("update ${domainClazz}:{}",${domainObject});
		${domainObject}DAO.updateByPrimaryKeySelective(${domainObject});
	}
	
	@Override
	public void updateList(List<${domainClazz}>${domainObject}s)
	{
		log.trace("deleteList:{}",${domainObject}s);
		${domainObject}DAO.batchUpdateSelective(${domainObject}s);
	}

	@Override
	public ListVO<${domainClazz}> list(int pageStart, int pageCount) {
		log.debug("list ${domainClazz}:pageStart={},pageCount={}",pageStart,pageCount);
		${domainClazz}Example example=new ${domainClazz}Example() ;
		List<${domainClazz}> vos=${domainObject}DAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount);
		ListVO<${domainClazz}> rets=new ListVO<${domainClazz}>(${domainObject}DAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<${domainClazz}> listByExample(${domainClazz}Example example,int pageStart, int pageCount) {
		if(example==null)
		{
			return list(pageStart,pageCount);
		}
		log.trace("listByExample ${domainClazz}:pageStart={},pageCount={}",pageStart,pageCount);
		List<${domainClazz}> vos=${domainObject}DAO.findByPage(example, pageStart*pageCount, (pageStart+1)*pageCount,example.getOrderByClause());
		ListVO<${domainClazz}> rets=new ListVO<${domainClazz}>(${domainObject}DAO.countByExample(example), vos,pageStart, pageCount);
		return rets;
	}
	
	@Override
	public ListVO<${domainClazz}> listrec(int rowstart, int count) {
		log.debug("list ${domainClazz}:pageStart={},pageCount={}",rowstart,count);
		${domainClazz}Example example=new ${domainClazz}Example() ;
		List<${domainClazz}> vos=${domainObject}DAO.findByPage(example, rowstart, rowstart+count);
		ListVO<${domainClazz}> rets=new ListVO<${domainClazz}>(${domainObject}DAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public ListVO<${domainClazz}> listrecByExample(${domainClazz}Example example,int rowstart, int count) {
		if(example==null)
		{
			return listrec(rowstart,count);
		}
		log.trace("listByExample ${domainClazz}:pageStart={},pageCount={}",rowstart,count);
		List<${domainClazz}> vos=${domainObject}DAO.findByPage(example, rowstart, rowstart+count,example.getOrderByClause());
		ListVO<${domainClazz}> rets=new ListVO<${domainClazz}>(${domainObject}DAO.countByExample(example), vos,rowstart/count, count);
		return rets;
	}
	
	@Override
	public void deleteList(List<${domainClazz}>${domainObject}s)
	{
		log.trace("deleteList:{}",${domainObject}s);
		${domainObject}DAO.batchDeleteSelective(${domainObject}s);
	}


	<#if clazzinfo.count <= 1 >
	@Override
	public ${domainClazz} get(String ${primaryKey}) {
		log.trace("get byKey:${primaryKey}={}",${primaryKey});
		return ${domainObject}DAO.selectByPrimaryKey(${primaryKey});
	}
	<#else>
	@Override
	public ${domainClazz} get(${domainClazz}Key ${primaryKey}) {
		log.trace("get byKey:${primaryKey}={}",${primaryKey});
		return ${domainObject}DAO.selectByPrimaryKey(${primaryKey});
	}
	</#if>
	
	<#if clazzinfo.count <= 1 >
	@Override
	public void deleteByKey(String ${primaryKey}) {
		log.trace("deleteByKey ${primaryKey}={}",${primaryKey});
		${domainObject}DAO.deleteByPrimaryKey(${primaryKey});
	}
	<#else>
	@Override
	public void deleteByKey(${domainClazz}Key ${primaryKey}) {
		log.trace("deleteByKey ${primaryKey}={}",${primaryKey});
		${domainObject}DAO.deleteByPrimaryKey(${primaryKey});
	}
	</#if>	

	@Override
	public void deleteByExample(${domainClazz}Example example) {
		log.trace("deleteByExample ={}",example);
		${domainObject}DAO.deleteByExample(example);
	}

}
