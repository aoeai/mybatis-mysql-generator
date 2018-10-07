package ${service.serviceImplPackageName};

import ${mapper.entityPackageName}.${mapper.entityBeanName};
import ${mapper.mapperPackageName}.${mapper.className};
import ${service.serviceInterfacePackageName}.${service.interfaceClassName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ${mapper.entityBeanVarName}.setId(null);
        return ${mapper.varName}.${methodSavePrefix}(${mapper.entityBeanVarName});
    }

	/**
	 * 更新数据
	 */
    @Override
    public int update(${mapper.entityBeanName} ${mapper.entityBeanVarName}){
        return ${mapper.varName}.update(${mapper.entityBeanVarName});
    }

	/**
	 * 查询列表
	 */
    @Override
    public List<${mapper.entityBeanName}> ${methodSelectPrefix}List(Map<String,Object> params){
        return ${mapper.varName}.${methodSelectPrefix}List(params);
    }

    /**
     * 查询列表总数
     */
    @Override
    public long ${methodSelectPrefix}Count(Map<String, Object> params){
        return ${mapper.varName}.${methodSelectPrefix}Count(params);
    }

    /**
	 * 查询数据
	 */
    @Override
    public ${mapper.entityBeanName} ${methodSelectPrefix}ByPrimaryKey(long id){
        return ${mapper.varName}.${methodSelectPrefix}ByPrimaryKey(id);
    }

}
