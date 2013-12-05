package ${packageName}.dao;


import java.util.List;

import ${packageName}.domain.${domainClazz};
import ${packageName}.domain.${domainClazz}Example;

public interface ${domainClazz}DAO{

	void insert(${domainClazz} record);
    void batchInsertSelective(final List<${domainClazz}> list);
	
	${domainClazz} selectByPrimaryKey(String id);
	List<${domainClazz}> selectByExample(${domainClazz}Example example);
	List<${domainClazz}> findByPage(${domainClazz}Example example, int startRow, int limit);
	
	int updateByPrimaryKey(${domainClazz} record);
	int updateByExample(${domainClazz} record, ${domainClazz}Example example);
	
	void deleteByPrimaryKey(String id);
	void deleteByExample(${domainClazz}Example example);
	long countByExample(${domainClazz}Example example);
	
}
