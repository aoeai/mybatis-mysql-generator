package ${service.serviceImplPackageName};

import com.aoeai.common.utils.Page;
import com.aoeai.common.utils.PageHelper;
import com.aoeai.common.utils.Pagination;
import com.aoeai.common.utils.ServiceHelper;
import ${mapper.entityPackageName}.${mapper.entityBeanName};
import ${mapper.mapperPackageName}.${mapper.className};
import ${service.serviceInterfacePackageName}.${service.interfaceClassName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${service.classComment}
 *
 */
@Service
public class ${service.implClassName} implements ${service.interfaceClassName} {

    @Autowired
    private ${mapper.className} ${mapper.varName};

    /**
	 * 添加数据
	 */
    @Override
    public int ${methodSavePrefix}(${mapper.entityBeanName} ${mapper.entityBeanVarName}){
        <#--(保存时)所有主键默认值设置为null-->
        <#list primaryKeySetNullList as nullStr>
        ${mapper.entityBeanVarName}.${nullStr}
        </#list>
        <#--保存时数据初始化-->
        Date date = new Date();
        <#list saveDataInitList as initValue>
        ${mapper.entityBeanVarName}.${initValue}
        </#list>

        return ${mapper.varName}.${methodSavePrefix}(${mapper.entityBeanVarName});
    }

	/**
	 * 更新数据
	 */
    @Override
    public int update(${mapper.entityBeanName} ${mapper.entityBeanVarName}){
        <#--更新时数据初始化-->
        <#list updateDataInitList as initValue>
        ${mapper.entityBeanVarName}.${initValue}
        </#list>

        return ${mapper.varName}.update(${mapper.entityBeanVarName});
    }

	/**
	 * 根据主键查询数据
	 */
    public ${mapper.entityBeanName} ${methodSelectPrefix}ByPrimaryKey(<#list table.primaryKeyColumns as column>${column.javaFieldType} ${column.javaFieldName}<#if column_has_next>, </#if></#list>){
        return ${mapper.varName}.${methodSelectPrefix}ByPrimaryKey(<#list table.primaryKeyColumns as column>${column.javaFieldName}<#if column_has_next>, </#if></#list>);
    }

    /**
    * 查询列表总数
    */
    public long ${methodSelectPrefix}Count(Map<String, Object> params){
        return ${mapper.varName}.${methodSelectPrefix}Count(params);
    }

	/**
	 * 查询列表(分页)
	 */
	public Pagination<${mapper.entityBeanName}> ${methodSelectPrefix}List(Map<String,Object> params){
        long totalCount = selectCount(params);
        Page page = ServiceHelper.buildPage(totalCount, params);
        List records = ${mapper.varName}.selectList(params);

        return PageHelper.buildPagination(page, records);
    }
}
