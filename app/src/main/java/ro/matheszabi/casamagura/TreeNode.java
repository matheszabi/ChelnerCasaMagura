package ro.matheszabi.casamagura;


import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> {

    private T data;
    private TreeNode<T> parent;


    private List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public TreeNode<T> getParent(){
        return parent;
    }


    public String toString(){
        return "TreeNode: data = "+ (data == null ? "null" : data.toString() )+ ", child count: "+(children== null? "0": ""+children.size());
    }


    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }
}