package com.chengq.ulit.treenode;

import lombok.Data;

import java.util.List;

@Data
public class BaseNode {

    /**
     * ID
     */
    private String id;

    /**
     * 文本
     */
    private String text;

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 子节点内容
     */
    private List<Node> children;

    /**
     * 是否禁用
     */
    private Boolean optional;
}
