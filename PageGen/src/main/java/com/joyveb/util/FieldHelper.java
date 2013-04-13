package com.joyveb.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import freemarker.template.utility.StringUtil;

public class FieldHelper {

	public static boolean isDateType(Field field) {
		return field.getType().equals(Date.class);
	}
	public static Object getTypeField(Field field,String value)
	{
		if(isDateType(field)) 
		{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
			try {
				return df.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public static String column2Field(String column_name)
	{
		String name=column_name.toLowerCase();
		if(column_name.contains("_"))
		{
			int index;
			while((index=name.indexOf("_"))>0)
			{
				name=name.substring(0,index)+name.substring(index+1,index+2).toUpperCase()
						+name.substring(index+2);
				
			}
		}
		return name;
	}

	public static String field2Colomn(String field)
	{
		StringBuffer buff=new StringBuffer(); 
		for(int i=0;i<field.length();i++)
		{
			char ch=field.charAt(i);
			if(ch>='A'&&ch<='Z')
			{
				buff.append("_").append(ch);
			}
			else
			{
				buff.append(ch);
			}
		}
		return buff.toString().toUpperCase();
	}
	
	
	/** 
     * 循环向上转型, 获取对象的 DeclaredField 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @return 父类中的属性对象 
     */  
      
    public static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
        Class<?> clazz = object.getClass() ;  
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {  
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
            }   
        }  
        return null;  
    }  
    
	public static void main(String[] args) {
		try {
			System.out.println(field2Colomn("companyId"));
//			System.out.println(getTypeField(User.class.getDeclaredField("createdate"),"2011-08-07T16:00:00.000Z"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
