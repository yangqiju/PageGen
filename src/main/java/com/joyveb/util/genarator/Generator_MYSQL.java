package com.joyveb.util.genarator;

public class Generator_MYSQL extends Generator_base {

	public static void main(String[] args) {
		moduleCHName.put("sys", "系统管理");
		moduleCHName.put("gold", "资金管理");
		moduleCHName.put("stat", "统计");
		moduleCHName.put("cm", "内容管理");
		moduleCHName.put("para", "参数管理");
		moduleCHName.put("day", "日统计");
		moduleCHName.put("week", "周统计");
		moduleCHName.put("month", "月统计");
		moduleCHName.put("year", "年统计");
		moduleCHName.put("core", "VO表");
		moduleCHName.put("admin", "管理员");
		cfgs = new String[] { "/generatorConfig-saleReport-mysql.xml" };// set xml
		Generator_MYSQL gen = new Generator_MYSQL();
		gen.generator();
	}

	@Override
	public String getSQL(String table) {
		String query = "select upper(t.table_name) as table_name,c.is_nullable as NULLABLE,c.column_name,c.column_comment as comments,"
				+ "c.column_type as data_type,t.table_comment as tbcomments,pkcount.count "
				+ "from information_schema.tables t left join information_schema.columns c "
				+ "on t.table_name=c.table_name "
				+ "and t.table_schema=c.table_schema "
				+ "left join(SELECT count(kcu.column_name) as count,tc.table_name "
				+ "FROM  INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS tc "
				+ "inner join "
				+ "INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS kcu "
				+ "on tc.TABLE_NAME = kcu.TABLE_NAME "
				+ "where tc.CONSTRAINT_TYPE = 'PRIMARY KEY' "
				+ "group by tc.table_name "
				+ ")pkcount "
				+ "on pkcount.table_name=t.table_name "
				+ "where t.table_name='" + table + "'";
		return query;
	}
}
