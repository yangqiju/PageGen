package com.joyveb.util.genarator;


public class Generator_ORACLE extends Generator_base {
	

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
		cfgs = new String[] { "/generatorConfig-terrace.xml" };//set xml
		Generator_ORACLE gen = new Generator_ORACLE();
		gen.generator();
	}


	@Override
	public  String getSQL(String table) {
		String query = "select t.table_name,t.NULLABLE,t.column_name, c.comments,t.data_type,u.comments as tbcomments,a.count from user_tab_columns t "
			+ "LEFT JOIN user_col_comments c "
			+ "ON c.table_name=t.table_name and c.column_name=t.column_name "
			+ "LEFT JOIN user_tab_comments u "
			+ "ON u.table_name=t.table_name "
			+ "left join (select count(col.column_name)as count,con.TABLE_NAME "
			+ "from user_constraints con inner join  user_cons_columns col "
			+ "on con.constraint_name = col.constraint_name "
			+ "where con.constraint_type='P' group by con.table_name) a "
			+ "on a.table_name = t.table_name "
			+ "where t.TABLE_NAME = '"
			+ table
			+ "'";
	return query;
	}
}
