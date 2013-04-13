	 select  t.table_name,c.column_name,c.column_comment as comments,
	 c.column_type as data_type,t.table_comment as tbcomments,
	 pktb.column_name as pk,pkcount.count
	 from information_schema.tables t left join information_schema.columns c
	    on t.table_name=c.table_name
	    and t.table_schema=c.table_schema
	    left join(SELECT
             kcu.column_name,tc.table_schema,tc.table_name
            FROM
              INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS tc,
              INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS kcu
            WHERE
              tc.TABLE_NAME = kcu.TABLE_NAME
             AND tc.CONSTRAINT_TYPE = 'PRIMARY KEY' 
              AND tc.table_name='
		T_STAT_WAGERDATA
		'
    	 )pktb on t.table_schema=pktb.table_schema
   	     and t.table_name=pktb.table_name
        left join(
        SELECT
            count(kcu.column_name) as count,tc.table_name
           FROM
              INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS tc,
              INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS kcu
            WHERE
              tc.TABLE_NAME = kcu.TABLE_NAME
              AND tc.CONSTRAINT_TYPE = 'PRIMARY KEY'
              AND tc.table_name='
	 T_STAT_WAGERDATA
		'
      	  )pkcount
    	    on pkcount.table_name=t.table_name
   		 where t.table_name='
	 T_STAT_WAGERDATA
	';