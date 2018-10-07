<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${mapper.mapperPackageName}.${mapper.className}">

	<resultMap id="${mapper.resultMapId}" type="${mapper.entityPackageName}.${mapper.entityBeanName}">
	<#list table.columns as column>
        <<#if column.isPrimaryKey??>id<#else>result</#if> column="${column.sqlFieldName}" property="${column.javaFieldName}" />
	</#list>
    </resultMap>

    <!-- 插入数据 -->
    <insert id="${methodSavePrefix}" parameterType="${mapper.entityPackageName}.${mapper.entityBeanName}"
            useGeneratedKeys="true" keyProperty="id">
        <selectKey resultType="_long" keyProperty="id" order="AFTER">
            select @@IDENTITY as id
        </selectKey>
        INSERT INTO ${table.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.columns as column>
            <if test="${column.javaFieldName} != null">
                ${column.sqlFieldName},
            </if>
		</#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list table.columns as column>
            <if test="${column.javaFieldName} != null">
                #${r'{'}${column.javaFieldName}},
            </if>
		</#list>
        </trim>
    </insert>

    <!-- 更新数据 -->
    <update id="update" parameterType="${mapper.entityPackageName}.${mapper.entityBeanName}">
        UPDATE ${table.name}
        <set>
            <#list table.updateColumns as field>
            <if test="${field.javaFieldName} != null">
                ${field.sqlFieldName} = #${r'{'}${field.javaFieldName}},
            </if>
            </#list>
        </set>
    <#list table.primaryKeyColumns as column>
        <#if column_index == 0>WHERE<#else>AND</#if> ${column.sqlFieldName} = #${r'{'}${column.javaFieldName}}
    </#list>
    </update>

    <sql id="Base_Column_List">
        <#list table.columns as column>
            ${column.sqlFieldName}<#if column_has_next>,</#if>
        </#list>
    </sql>

    <select id="${methodSelectPrefix}ByPrimaryKey" parameterType="${mapper.entityPackageName}.${mapper.entityBeanName}" resultMap="${mapper.resultMapId}">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${table.name}
    <#list table.primaryKeyColumns as column>
        <#if column_index == 0>WHERE<#else>AND</#if> ${column.sqlFieldName} = #${r'{'}${column.javaFieldName}}
    </#list>
    </select>

    <!-- 搜索条件 -->
    <sql id="selectWhere">
        <where>
        <#list table.columns as column>
            <if test="${column.javaFieldName} != null">
                AND ${column.sqlFieldName} = #${r'{'}${column.javaFieldName}},
            </if>
        </#list>
        </where>
    </sql>

</mapper>