package com.joyveb.report.gens.terrace.dao.iface;

import com.joyveb.report.gens.terrace.domain.ParaMerchant;
import com.joyveb.report.gens.terrace.domain.ParaMerchantExample;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;;

@Repository
public interface ParaMerchantDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int countByExample(ParaMerchantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int deleteByExample(ParaMerchantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int deleteByPrimaryKey(String merchantid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    void insert(ParaMerchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    void insertSelective(ParaMerchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    List<ParaMerchant> selectByExample(ParaMerchantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    ParaMerchant selectByPrimaryKey(String merchantid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByExampleSelective(ParaMerchant record, ParaMerchantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByExample(ParaMerchant record, ParaMerchantExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByPrimaryKeySelective(ParaMerchant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PARA_MERCHANT
     *
     * @mbggenerated Sat Mar 15 06:42:48 CST 2014
     */
    int updateByPrimaryKey(ParaMerchant record);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<ParaMerchant> findByPage(ParaMerchantExample example, int startRow, int endRow);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<ParaMerchant> findByPage(ParaMerchantExample example, int startRow, int endRow, String orderByClause);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchInsertSelective(final List<ParaMerchant> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    int sumByExample(ParaMerchantExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<HashMap> groupByExample(ParaMerchantExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchUpdateSelective(final List<ParaMerchant> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void batchDeleteSelective(final List<ParaMerchant> list);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    List<ParaMerchant> selectByExampleAnd(ParaMerchantExample example);

    /**
    * This method was generated by MyBatis Generator.
    * This method corresponds to the database table T_PARA_MERCHANT
    *
    * @generated Sat Mar 11 06:42:48 CST 2014
    */
    void initSqlMapClient();
}