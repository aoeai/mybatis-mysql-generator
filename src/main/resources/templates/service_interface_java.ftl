package ${service.serviceInterfacePackageName};

import com.aoeai.common.utils.Pagination;
import ${mapper.entityPackageName}.${mapper.entityBeanName};

import java.util.Map;

/**
 * ${service.classComment}
 *
 */
public interface ${service.interfaceClassName} {

    /**
	 * 添加数据
	 */
    int ${methodSavePrefix}(${mapper.entityBeanName} ${mapper.entityBeanVarName});

	/**
	 * 更新数据
	 */
    int update(${mapper.entityBeanName} ${mapper.entityBeanVarName});

	/**
	 * 根据主键查询数据
	 */
    ${mapper.entityBeanName} ${methodSelectPrefix}ByPrimaryKey(<#list table.primaryKeyColumns as column>${column.javaFieldType} ${column.javaFieldName}<#if column_has_next>, </#if></#list>);

    /**
    * 查询列表总数
    */
    long ${methodSelectPrefix}Count(Map<String, Object> params);

	/**
	 * 查询列表(分页)
	 */
	Pagination<${mapper.entityBeanName}> ${methodSelectPrefix}List(Map<String,Object> params);

}
