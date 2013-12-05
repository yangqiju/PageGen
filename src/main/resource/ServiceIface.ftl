package ${packageName}.service.iface;

import java.util.List;

import ${packageName}.domain.${domainClazz};
import ${packageName}.domain.${domainClazz}Example;
<#if clazzinfo.count <= 1 >
<#else>
import ${packageName}.domain.${domainClazz}Key;
</#if>

import com.joyveb.common.gens.bizcom.vo.ListVO;
public interface ${domainClazz}Service {
	// create
	public void create(${domainClazz} ${domainObject});

	//update
	public void update(${domainClazz} ${domainObject});
	
	//updateList
	public void updateList(List<${domainClazz}> ${domainObject});

	//list all
	public ListVO<${domainClazz}> list(int pageStart, int pageCount);
	
	public ListVO<${domainClazz}> listByExample(${domainClazz}Example example,int pageStart, int pageCount) ;

	//list all
	public ListVO<${domainClazz}> listrec(int pageStart, int pageCount);
	
	public ListVO<${domainClazz}> listrecByExample(${domainClazz}Example example,int pageStart, int pageCount) ;

	<#if clazzinfo.count <= 1 >
	public ${domainClazz} get(String ${primaryKey});
	<#else>
	public ${domainClazz} get(${domainClazz}Key ${primaryKey});
	</#if>	
	
	<#if clazzinfo.count <= 1 >
	public void deleteByKey(String ${primaryKey});
	<#else>
	public void deleteByKey(${domainClazz}Key ${primaryKey});
	</#if>	
	
	public void deleteByExample(${domainClazz}Example exmple);
	
	public void deleteList(List<${domainClazz}> ${domainObject});
}
