package ${controller.packageName};

import com.aoeai.common.exception.PageException;
import com.aoeai.common.utils.Pagination;
import com.aoeai.common.utils.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ${mapper.entityPackageName}.${mapper.entityBeanName};
import ${service.serviceInterfacePackageName}.${service.interfaceClassName};
import java.util.Map;

@RestController
@RequestMapping(value="${mapper.entityBeanVarName}")
public class ${controller.className} {

    @Autowired
    private ${service.interfaceClassName} ${service.interfaceVarClassName};

    @PostMapping
    public ResponseEntity ${methodSavePrefix}(@RequestBody @Validated ${mapper.entityBeanName} ${mapper.entityBeanVarName}, BindingResult bindingResult){
        StringBuilder errors = ControllerHelper.getErrorMessage(bindingResult);
        if (errors != null){
            return ResponseEntity.badRequest().body(errors);
        }

        int result = ${service.interfaceVarClassName}.${methodSavePrefix}(${mapper.entityBeanVarName});
        if (result == 1){
            return ResponseEntity.status(HttpStatus.CREATED).body(${mapper.entityBeanVarName});
        }

        return ResponseEntity.badRequest().body("保存失败");
    }

    @PutMapping
    public ResponseEntity update(@Validated ${mapper.entityBeanName} ${mapper.entityBeanVarName}, BindingResult bindingResult){
        <#list primaryKeyGetMethodList as column>
        if (${mapper.entityBeanVarName}.${column.method} == null){
            return ResponseEntity.badRequest().body("${column.javaFieldName} 不能为空");
        }
        </#list>

        StringBuilder errors = ControllerHelper.getErrorMessage(bindingResult);
        if (errors != null){
            return ResponseEntity.badRequest().body(errors);
        }

        int result = ${service.interfaceVarClassName}.update(${mapper.entityBeanVarName});
        if (result == 1){
            return ResponseEntity.status(HttpStatus.CREATED).body(${mapper.entityBeanVarName});
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
    }

    @GetMapping(value="/<#list primaryKeyColumns as column>${r'{'}${column.javaFieldName}}<#if column_has_next>/</#if></#list>")
    public ResponseEntity ${methodSelectPrefix}ByPrimaryKey(<#list primaryKeyColumns as column>@PathVariable("${column.javaFieldName}") ${column.javaFieldType} ${column.javaFieldName}<#if column_has_next>, </#if></#list>){
        ${mapper.entityBeanName} ${mapper.entityBeanVarName} = ${service.interfaceVarClassName}.${methodSelectPrefix}ByPrimaryKey(<#list primaryKeyColumns as column>${column.javaFieldName}<#if column_has_next>, </#if></#list>);

        if(${mapper.entityBeanVarName} == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(${mapper.entityBeanVarName});
    }

    /**
     * 查询分页记录
     *
     * @param ${mapper.entityBeanVarName} 查询条件(=)
     * @param pageSize   每页显示的记录数
     * @param pageNum    当前页
     * @return
     */
    @GetMapping(value="/list")
    public ResponseEntity records(${mapper.entityBeanName} ${mapper.entityBeanVarName}, int pageSize, int pageNum) {
        String sort = "add_time desc"; // 排序语句
        Map<String, Object> params = ControllerHelper.getParams(${mapper.entityBeanVarName}, pageSize, pageNum, sort);

        Pagination<${mapper.entityBeanName}> pagination;
        try {
            pagination = ${service.interfaceVarClassName}.selectList(params);
        } catch (PageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(pagination);
    }

}
