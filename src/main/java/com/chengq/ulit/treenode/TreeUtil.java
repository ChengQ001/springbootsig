package com.chengq.ulit.treenode;

import cn.hutool.core.util.ObjectUtil;
import com.chengq.ulit.helper.ToolUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cq
 * 树结构转换器
 * @date 2023/02/14 14:26
 */
@Data
@Accessors(chain = true)
@Slf4j
public class TreeUtil<T> {

    /**
     * 查询数据源 必填
     */
    private List<T> list;

    /**
     * id字段名字 需要与泛型T里面字段名字保持一致
     */
    private String idName;

    /**
     * 父节点ID字段名字 需要与泛型T里面字段名字保持一致
     */
    private String pIdName;

    /**
     * 展示名称字段名字 需要与泛型T里面字段名字保持一致
     */
    private String textName;


    /**
     * 转换后数据源 无需传
     */
    private List<Node> dataSource = null;


    /**
     * 初始化通过自定义对象字段名
     *
     * @param list     查询数据源
     * @param idName   id字段名字
     * @param pIdName  父节点字段名字
     * @param textName 展示字段名字
     */
    public static TreeUtil init(List list, String idName, String pIdName, String textName) {
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.list = list;
        treeUtil.idName = idName;
        treeUtil.pIdName = pIdName;
        treeUtil.textName = textName;
        treeUtil.convertDataSource();//初始化

        return treeUtil;
    }

    /**
     * 初始化通过注解初始化(TreeIdAnnotation,TreeParentIdAnnotation,TreeTextAnnotation)
     *
     * @param list 查询数据源
     */
    public static TreeUtil init(List list) {
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.list = list;
        treeUtil.convertAnnotationDataSource();//初始化
        return treeUtil;
    }


    private void convertAnnotationDataSource() {
        dataSource = new ArrayList<>();
        for (T item : list) {
            Node node = new Node();
            node.setObjectInfo(item);
            Object idNameValue = getAnnotationValue(item, TreeIdAnnotation.class);
            Object textNameValue = getAnnotationValue(item, TreeTextAnnotation.class);
            Object pIdNameValue = getAnnotationValue(item, TreeParentIdAnnotation.class);
            node.setId(toString(idNameValue));
            node.setText(toString(textNameValue));
            node.setParentId(toString(pIdNameValue));
            node.setChildren(null);
            dataSource.add(node);
        }
    }

    private void convertDataSource() {
        dataSource = new ArrayList<>();
        for (T item : list) {
            Node node = new Node();
            node.setObjectInfo(item);
            Object idNameValue = getValue(item, idName);
            Object textNameValue = getValue(item, textName);
            Object pIdNameValue = getValue(item, pIdName);
            node.setId(toString(idNameValue));
            node.setText(toString(textNameValue));
            node.setParentId(toString(pIdNameValue));
            node.setChildren(null);
            dataSource.add(node);
        }
    }


    private Map<String, Node> convertMap() {
        Map<String, Node> nodeMap = new HashMap<>();
        for (Node node : this.dataSource) {
            nodeMap.put(node.getId(), node);
        }
        return nodeMap;
    }

    /**
     * 创建树结构
     *
     * @return
     */
    public List<Node> buildTree() {
        if (ToolUtil.isEmpty(dataSource)) {
            return null;
        }
        Map<String, Node> nodeMap = convertMap();
        List<Node> roots = new ArrayList<>();
        for (Node node : dataSource) {
            if (node.getParentId() == null || !nodeMap.containsKey(node.getParentId())) {
                roots.add(node);
            } else {
                Node parent = nodeMap.get(node.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(node);
            }
        }
        return roots;
    }

    /**
     * 模糊查询树结构(如:查询名称，手机，找到手机相关ID)
     *
     * @param ids
     * @return
     */
    public List<Node> buildTreeByIds(Set<String> ids) {
        if (ToolUtil.isEmpty(dataSource)) {
            return null;
        }
        Set<String> newIds = new HashSet<>();//查找所有节点的父节点
        List<Node> newNode = null;
        if (ToolUtil.isNotEmpty(ids)) {
            for (String id : ids) {
                newIds.addAll(findParentList(id, true).stream().map(Node::getId).collect(Collectors.toSet()));
            }
        }
        if (ToolUtil.isNotEmpty(newIds)) {
            dataSource = dataSource.stream().filter(x -> newIds.contains(x.getId())).collect(Collectors.toList());
            newNode = buildTree();
        }
        return newNode;
    }

    /**
     * 查找父节点
     *
     * @param id
     * @param isYourself
     * @return
     */
    public List<Node> findParentList(String id, Boolean isYourself) {
        if (ToolUtil.isEmpty(dataSource)) {
            return null;
        }
        Map<String, Node> nodeMap = convertMap();
        Node node = nodeMap.get(id);
        if (node == null) {
            return null;
        }
        List<Node> parents = new ArrayList<>();
        String pid = node.getParentId();
        while (ToolUtil.isNotEmpty(pid)) {
            Node parent = nodeMap.get(pid);
            if (parent == null) {
                break;
            }
            parents.add(parent);
            pid = parent.getParentId();
        }
        if (isYourself) {
            parents.add(nodeMap.get(id));
        }
        return parents;
    }


    /**
     * 查询子节点
     *
     * @param id
     * @param isYourself
     * @return
     */
    public List<Node> findChildList(String id, Boolean isYourself) {
        if (ToolUtil.isEmpty(dataSource)) {
            return null;
        }
        List<Node> childList = findChildList(id, this.dataSource, new ArrayList<>());
        if (isYourself) {
            childList.add(this.dataSource.stream().filter(x -> ObjectUtil.equal(id, x.getId())).findFirst().orElse(new Node()));
        }
        return childList;
    }


    private List<Node> findChildList(String id, List<Node> list, List<Node> newList) {
        List<Node> collect = list.stream().filter(x -> ObjectUtil.equal(x.getParentId(), id)).collect(Collectors.toList());
        if (ToolUtil.isNotEmpty(collect)) {
            newList.addAll(collect);
            for (Node item : collect) {
                findChildList(item.getId(), list, newList);
            }
        }
        return newList;
    }


    /**
     * 获取指定注解字段值
     *
     * @param object
     * @param c      注解
     * @return
     */
    private Object getAnnotationValue(Object object, Class c) {
        Object value = null;
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(c)) {
                    f.setAccessible(true);
                    value = f.get(object);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private String toString(Object str) {
        if (ToolUtil.isEmpty(str)) {
            return "";
        } else {
            return str.toString();
        }
    }

    private Object getValue(Object object, String fieldName) {
        Object value = null;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(object);
        } catch (Exception e) {
            log.error("fail to getValue!", e);
        }
        return value;
    }

    private Object getParentValue(Object object, String fieldName) {
        Object value = null;
        try {
            Field field = object.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = field.get(object);
        } catch (Exception e) {
            log.error("fail to getValue!", e);
        }
        return value;
    }
}
