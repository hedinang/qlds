package vn.byt.qlds.core.base;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    public T value;
    public List<Tree> children =  new ArrayList<>();

    public Tree(T value) {
        this.value = value;
    }
}
