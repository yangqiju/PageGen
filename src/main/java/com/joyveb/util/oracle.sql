select t.table_name,
       t.NULLABLE,
       t.column_name,
       c.comments,
       t.data_type,
       u.comments as tbcomments,
       a.count
  from user_tab_columns t
  LEFT JOIN user_col_comments c
    ON c.table_name = t.table_name
   and c.column_name = t.column_name
  LEFT JOIN user_tab_comments u
    ON u.table_name = t.table_name
  left join (select count(col.column_name) as count,con.TABLE_NAME
                       from user_constraints con
                      inner join user_cons_columns col
                         on con.constraint_name = col.constraint_name
                      where con.constraint_type = 'P'
                      group by con.table_name) a
    on a.table_name = t.table_name
 where t.TABLE_NAME = 'T_DAY_STAT';
