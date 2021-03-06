package com.aoeai.tools.mybatis.utils;

import com.aoeai.tools.exception.OppsException;
import com.aoeai.tools.mybatis.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * 工具类
 */
@Component
@Slf4j
public final class Tools {

	@Autowired
	private ConfigService configService;

	private final static String TYPE_STRING = "String";
	private final static String TYPE_INTEGER = "Integer";
	public final static String TYPE_DATE = "Date";
	private final static String TYPE_LONG = "Long";
	private final static String TYPE_FLOAT = "Float";
	public final static String TYPE_BIGDECIMAL = "BigDecimal";

	/**
	 * 数据类型映射
	 * key 数据库类型；value Java类型
	 */
	private Map<String, String> COLUMN_JAVA_TYPE_MAP;

	/**
	 * Java类型映射
	 * key 短名称；value 全名
	 */
	private Map<String, String> JAVA_TYPE_MAP;

	@PostConstruct
	private void init(){
		COLUMN_JAVA_TYPE_MAP = new HashMap();
		COLUMN_JAVA_TYPE_MAP.put("varchar", TYPE_STRING);
		COLUMN_JAVA_TYPE_MAP.put("int", TYPE_INTEGER);
		COLUMN_JAVA_TYPE_MAP.put("date", TYPE_DATE);
		COLUMN_JAVA_TYPE_MAP.put("datetime", TYPE_DATE);
		COLUMN_JAVA_TYPE_MAP.put("timestamp", TYPE_DATE);
		COLUMN_JAVA_TYPE_MAP.put("nvarchar", TYPE_STRING);
		COLUMN_JAVA_TYPE_MAP.put("char", TYPE_STRING);
		COLUMN_JAVA_TYPE_MAP.put("uniqueidentifier", TYPE_STRING);
		COLUMN_JAVA_TYPE_MAP.put("bigint", TYPE_LONG);
		COLUMN_JAVA_TYPE_MAP.put("tinyint", TYPE_INTEGER);
		COLUMN_JAVA_TYPE_MAP.put("smallint", TYPE_INTEGER);
		COLUMN_JAVA_TYPE_MAP.put("text", TYPE_STRING);
		COLUMN_JAVA_TYPE_MAP.put("float", TYPE_FLOAT);
		COLUMN_JAVA_TYPE_MAP.put("decimal", TYPE_BIGDECIMAL);
		COLUMN_JAVA_TYPE_MAP.put("longtext", TYPE_STRING);

		JAVA_TYPE_MAP = new HashMap<>();
		JAVA_TYPE_MAP.put(TYPE_LONG, "java.lang.Long");
		JAVA_TYPE_MAP.put(TYPE_DATE, "java.util.Date");
		JAVA_TYPE_MAP.put(TYPE_BIGDECIMAL, "java.math.BigDecimal");
	}

	public String getJavaTypeFullName(String shortName){
		String fullName = JAVA_TYPE_MAP.get(shortName);

		if(fullName == null){
			throw new OppsException(shortName
					+ " 不存在，请在com.aoeai.tools.mybatis.utils.Tools.java 中添加对应的类型： init()方法中 JAVA_TYPE_MAP");
		}

		return fullName;
	}

	public String getPropertyType(String columnType) {
		if (columnType.contains("(")) {
			columnType = columnType.substring(0,
					columnType.indexOf("("));
		}
		String tmp = columnType.toLowerCase();
		StringTokenizer st = new StringTokenizer(tmp);
		String type = COLUMN_JAVA_TYPE_MAP.get(st.nextToken());

		if(type == null){
			throw new OppsException(columnType
					+ " 未知，请在com.aoeai.tools.mybatis.utils.Tools.java 中添加对应的类型： init()方法中 COLUMN_JAVA_TYPE_MAP");
		}

		return type;
	}

	/**
	 * @param className
	 * @return 类的全名
	 */
	public String getClassFullName(String className){
		String name = JAVA_TYPE_MAP.get(className);
		return name == null ? className + "还没有配置全名" : name;
	}

	/**
	 * 根据表名获取Java类的名字
	 *
	 * @param tableName 表名
	 * @return
	 */
	public String getEntityClassName(String tableName) {
		if (configService.getFilterTablePrefix() != null) {
			for (String str : configService.getFilterTablePrefix()) {
				tableName = tableName.toLowerCase().replaceAll(str.toLowerCase(), "");
			}
		}

		return StringUtils.capitalize(fixName(tableName));
	}

	/**
	 * @param tableName
	 * @return service接口类名称
	 */
	public String getServiceInterfaceClassName(String tableName){
		return getEntityClassName(tableName) + "Service";
	}

	/**
	 *
	 * @param tableName
	 * @return service实现类名称
	 */
	public String getServiceImplClassName(String tableName){
		return getServiceInterfaceClassName(tableName) + "Impl";
	}

	/**
	 *
	 * @param tableName
	 * @return Controller类名称
	 */
	public String getControllerClassName(String tableName){
		return getEntityClassName(tableName) + "Controller";
	}

	public static String fixName(String name) {
		name = name.toLowerCase();
		while (name.contains("_")) {
			int i = name.indexOf("_");
			name = name.substring(0, i)
					+ name.substring(++i, ++i).toUpperCase()
					+ name.subSequence(i, name.length());
		}

		return name;
	}

	/**
	 * @return 实体类包名
	 */
	public String getEntityPackage() {
		return configService.getRootPackageName() + "." + configService.getEntityPackageSuffix();
	}

	/**
	 * 获得Mapper的xml文件路径
	 * @return
	 */
	public static String getMapperXmlPath(){
		return "resources" + File.separator + "mapper" + File.separator;
	}

	/**
	 * 获得mapper的xml文件名称
	 *
	 * @param str
	 * @return
	 */
	public static String getMapperXmlName(String str) {
		str = getStartSmallName(str);
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				String upper = c + "";
				String lower = "-" + upper.toLowerCase();
				str = str.replace(upper, lower);
			}
		}
		return str;
	}

	/**
	 * @return Mapper类包名
	 */
	public String getMapperPackage() {
		return configService.getRootPackageName() + "." + configService.getMapperPackageSuffix();
	}

	/**
	 * @return service接口类包名
	 */
	public String getServiceInterfacePackage() {
		return configService.getRootPackageName() + ".service";
	}

	/**
	 * @return service实现类包名
	 */
	public String getServiceImplPackage() {
		return getServiceInterfacePackage() + ".impl";
	}

	/**
	 * @return Controller类包名
	 */
	public String getControllerPackage() {
		return configService.getRootPackageName() + ".controller";
	}


	/**
	 * 获得开头是小写的驼峰式命名
	 *
	 * @param name
	 * @return
	 */
	public static String getStartSmallName(String name) {
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return name;
	}

	/**
	 * 根据包名获得java文件路径
	 * @param packageName
	 * @return
	 */
	public static String getJavaPathFromPackageName(String packageName){
		StringBuilder path = new StringBuilder("java").append(File.separator);
		for (String s : packageName.split("[.]")) {
			path.append(s).append(File.separator);
		}
		return path.toString();
	}
}
