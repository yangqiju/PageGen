package com.joyveb.mongo.genarator;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.joyveb.common.gens.bizcom.bean.FtlClassInfo;

/**
 * 
 * 项目名称：PageGen 类名称：MongoDbContext
 * 
 * @Company: 北京畅享互联有限公司
 * @Copyright: Copyright (c) 2012
 * @Author： 杨其桔 创建时间：2013-6-26 下午12:29:49 修改备注：
 * @version
 * 
 */
@Data
public class MongoDbContext {
	String projectName;
	String targetSource;
	String targetPackage;
	List<FtlClassInfo> tables = new ArrayList<FtlClassInfo>();
}
