package ${controller.packageName};

import com.aoeai.helper.ControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ${mapper.entityPackageName}.${mapper.entityBeanName};
import ${service.serviceInterfacePackageName}.${service.interfaceClassName};
import java.util.List;

@RestController
@RequestMapping(value="${mapper.entityBeanVarName}")
public class ${controller.className} {

    @Autowired
    private ${service.interfaceClassName} ${service.interfaceVarClassName};

    @PostMapping
    public ResponseEntity ${methodSavePrefix}(@Validated ${mapper.entityBeanName} ${mapper.entityBeanVarName}, BindingResult bindingResult){
        StringBuilder errors = ControllerHelper.getErrorMessage(bindingResult);
        if (errors != null){
            return ResponseEntity.badRequest().body(errors);
        }

        int result = ${service.interfaceVarClassName}.${methodSavePrefix}(workerNode);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.CREATED).body(${mapper.entityBeanVarName});
        }

        return ResponseEntity.badRequest().body("保存失败");
    }

    @PutMapping
    public ResponseEntity update(@Validated ${mapper.entityBeanName} ${mapper.entityBeanVarName}, BindingResult bindingResult){
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

}
