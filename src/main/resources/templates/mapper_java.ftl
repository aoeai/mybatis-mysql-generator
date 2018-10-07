package ${mapper.mapperPackageName};

import ${mapper.entityPackageName}.${mapper.entityBeanName};

import java.util.List;
import java.util.Map;

/**
 * ${mapper.classComment}Mapper
 *
 */
public interface ${mapper.className} {

   /**
	 * 添加数据
	 */
	int ${methodSavePrefix}(${mapper.entityBeanName} ${mapper.entityBeanVarName});

	/**
	 * 更新数据
	 */
	int update(${mapper.entityBeanName} ${mapper.entityBeanVarName});

	/**
	 * 查询列表
	 */
	List<${mapper.entityBeanName}> ${methodSelectPrefix}List(Map<String,Object> params);

    /**
     * 查询列表总数
     */
    long ${methodSelectPrefix}Count(Map<String, Object> params);

	/**
	 * 查询数据
	 */
	${mapper.entityBeanName} ${methodSelectPrefix}ByPrimaryKey(long id);

}
