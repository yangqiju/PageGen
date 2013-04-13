package com.joyveb.report.gens.terrace.dao.iface;

import com.joyveb.report.gens.terrace.domain.StatWagerdata;
import com.joyveb.report.gens.terrace.domain.StatWagerdataExample;
import com.joyveb.report.gens.terrace.domain.StatWagerdataKey;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;;

@Repository
public interface StatWagerdataDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int countByExample(StatWagerdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int deleteByExample(StatWagerdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int deleteByPrimaryKey(StatWagerdataKey _key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    void insert(StatWagerdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    void insertSelective(StatWagerdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    List<StatWagerdata> selectByExample(StatWagerdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    StatWagerdata selectByPrimaryKey(StatWagerdataKey _key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByExampleSelective(StatWagerdata record, StatWagerdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByExample(StatWagerdata record, StatWagerdataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByPrimaryKeySelective(StatWagerdata record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_STAT_WAGERDATA
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByPrimaryKey(StatWagerdata record);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<StatWagerdata> findByPage(StatWagerdataExample example, int startRow, int endRow);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<StatWagerdata> findByPage(StatWagerdataExample example, int startRow, int endRow, String orderByClause);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchInsertSelective(final List<StatWagerdata> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    int sumByExample(StatWagerdataExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<HashMap> groupByExample(StatWagerdataExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchUpdateSelective(final List<StatWagerdata> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchDeleteSelective(final List<StatWagerdata> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<StatWagerdata> selectByExampleAnd(StatWagerdataExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_STAT_WAGERDATA
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void initSqlMapClient();
}