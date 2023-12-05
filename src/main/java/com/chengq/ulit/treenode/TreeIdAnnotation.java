package com.chengq.ulit.treenode;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 树结构id注解
 */
@Target({ElementType.TYPE,ElementType.FIELD}) //表示@RestController可以定义在类上
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeIdAnnotation {


}
