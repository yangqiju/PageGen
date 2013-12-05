package ${packageName}.domain;


import java.util.List;
import java.util.Date;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

public @Data
class ${domainClazz}Example {
	protected String orderByClause;
	protected Criteria criteria = new Criteria();
	
	public ${domainClazz}Example() {
	}
	
	<#list clazzinfo.fields as field>
		public void and${field.comments}IsNull() {
			criteria.and("${field.column}").exists(false);
		}
		public void and${field.comments}IsNotNull() {
			criteria.and("${field.column}").exists(true);
		}
		public void and${field.comments}IsEqualTo(${field.type} ${field.column}) {
			criteria.and("${field.column}").is(${field.column});
		}
		public void and${field.comments}IsNotEqualTo(${field.type} ${field.column}) {
			criteria.and("${field.column}").ne(${field.column});
		}
		public void and${field.comments}GreaterThan(${field.type} ${field.column}) {
			criteria.and("${field.column}").gt(${field.column});
		}
		public void and${field.comments}GreaterThanOrEqualTo(${field.type} ${field.column}) {
			criteria.and("${field.column}").gte(${field.column});
		}
		public void and${field.comments}LessThan(${field.type} ${field.column}) {
			criteria.and("${field.column}").lt(${field.column});
		}
		public void and${field.comments}LessThanOrEqualTo(${field.type} ${field.column}) {
			criteria.and("${field.column}").lte(${field.column});
		}
		public void and${field.comments}In(List<${field.type}> values) {
			criteria.and("${field.column}").in(values);
		}
		public void and${field.comments}NotIn(List<${field.type}> values) {
			criteria.and("${field.column}").nin(values);
		}
		public void and${field.comments}Between(${field.type} value1, ${field.type} value2) {
			criteria.and("${field.column}").gte(value1).lte(value2);
		}
	</#list>  
	
}
