package com.datastructure.spatial.trees;

public class IntervalTrees {

    public static void main(String[] args) {

        IntervalTreeImpl intervalTree = new IntervalTreeImpl();
        intervalTree.put(17, 19, 12);
        intervalTree.put(5, 8, 19);
        intervalTree.put(21, 24, 4);
        intervalTree.put(4, 8, 21);
        intervalTree.put(15, 18, 54);
        intervalTree.put(7, 10, 31);
        intervalTree.put(16, 22, 63);

        System.out.println(intervalTree.intersects(3, 5));
    }
}

interface IIntervalTree {

    void put(int start, int end, int value);

    int get(int start, int end);

    boolean delete(int start, int end);

    String intersects(int start, int end);
}

class IntervalTreeImpl implements IIntervalTree {

    Node root;

    @Override
    public void put(int start, int end, int value) {

        // The first node.
        if (root == null) {
            root = new Node();
            root.start = start;
            root.end = end;
            root.value = value;
            return;
        }

        recursePut(start, end, value, root, null);
    }

    @Override
    public int get(int start, int end) {
        return 0;
    }

    @Override
    public boolean delete(int start, int end) {
        return false;
    }

    @Override
    public String intersects(int start, int end) {
        Node node = root;

        while (node != null) {
            if (checkOverlap(start, end, node)) {
                return "start : " + node.start + ", end : " + node.end;
            }
            if (node.left == null || start > node.left.max) {
                node = node.right;
                continue;
            }
            node = node.left;
        }

        return "No intersection!!";
    }

    private boolean checkPointOverlap(int start, int end, Node node) {
        return (end >= node.start && end <= node.end)
                || (start >= node.start && end <= node.end)
                || (start >= node.start && start <= node.end);
    }

    private boolean checkOverlap(int start, int end, Node node) {
        return (end > node.start && end <= node.end)
                || (start >= node.start && end <= node.end)
                || (start >= node.start && start < node.end);
    }

    private int recursePut(int start, int end, int value, Node node, Node parent) {
        if (node == null) {
            node = new Node();
            node.start = start;
            node.end = end;
            node.value = value;
            node.max = end;
            if (parent.left == null) parent.left = node;
            else parent.right = node;

            return node.max;
        }

        int max;

        if (start < node.start) {
            max = recursePut(start, end, value, node.left, node);
        } else {
            max = recursePut(start, end, value, node.right, node);
        }

        node.max = Math.max(max, node.max);

        return max;
    }
}

class Node {

    Node left, right;
    int start, end, max, value;

    public Node() {
    }
}
