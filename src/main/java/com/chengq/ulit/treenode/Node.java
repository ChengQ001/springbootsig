package com.chengq.ulit.treenode;

import lombok.Data;

@Data
public class Node<T> extends BaseNode {
    /**
     * 内容明细
     */
    private T objectInfo;

}

