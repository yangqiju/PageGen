package ${packageName}.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.mongodb.WriteResult;
import ${packageName}.domain.${domainClazz};
import ${packageName}.domain.${domainClazz}Example;
import ${packageName}.dao.${domainClazz}DAO;


@Repository("${domainClazz}DAO")
public class ${domainClazz}DAOImpl implements ${domainClazz}DAO {

	@Autowired
	public MongoTemplate mongoTemplate;
	
	@Override
	public void insert(${domainClazz} record) {
		this.mongoTemplate.insert(record);
	}
	
	@Override
	public void batchInsertSelective(List<${domainClazz}> list) {
		this.mongoTemplate.insertAll(list);
	}

	@Override
	public ${domainClazz} selectByPrimaryKey(String id) {
		return this.mongoTemplate.findById(id, ${domainClazz}.class);
	}

	@Override
	public List<${domainClazz}> selectByExample(${domainClazz}Example example) {
		Query query = new Query();
		query.addCriteria(example.getCriteria());
		return this.mongoTemplate.find(query, ${domainClazz}.class);
	}

	@Override
	public List<${domainClazz}> findByPage(${domainClazz}Example example, int startRow, int limit) {
		Query query = new Query();
		query.addCriteria(example.getCriteria());
		query.skip(startRow==0?0:startRow-1).limit(limit);//skip 跳过多少行
		return this.mongoTemplate.find(query, ${domainClazz}.class);
	}

	@Override
	public int updateByPrimaryKey(${domainClazz} record) {
		Query query = new Query(Criteria.where("_id").is(record.getId()));
		Update update = new Update();
		<#list clazzinfo.fields as field>
		update.set("${field.column}", record.get${field.comments}());
		</#list>  
		WriteResult wr = mongoTemplate.updateMulti(query, update,${domainClazz}.class);
		return wr.getN();
	}

	@Override
	public int updateByExample(${domainClazz} record, ${domainClazz}Example example) {
		Query query = new Query();
		query.addCriteria(example.getCriteria());
		Update update = new Update();
		<#list clazzinfo.fields as field>
		update.set("${field.column}", record.get${field.comments}());
		</#list>  
		WriteResult wr = mongoTemplate.updateMulti(query, update,${domainClazz}.class);
		return wr.getN();
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		this.mongoTemplate.remove(query);
	}

	@Override
	public void deleteByExample(${domainClazz}Example example) {
		Query query = new Query(example.getCriteria());
		this.mongoTemplate.remove(query);
	}


	@Override
	public long countByExample(${domainClazz}Example example) {
		Query query = new Query(example.getCriteria());
		return this.mongoTemplate.count(query,${domainClazz}.class);
	}
}
