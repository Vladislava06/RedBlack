class RedBlackTree {
    private Node root;

    private static class Node {
        int key;
        Node parent;
        Node left;
        Node right;
        boolean isRed;

        Node(int key) {
            this.key = key;
            this.isRed = true;
        }
    }

    public void insert(int key) {
        Node newNode = new Node(key);

        if (root == null) {
            newNode.isRed = false; // Корень всегда черный
            root = newNode;
        } else {
            Node current = root;
            Node parent;

            while (true) {
                parent = current;

                if (key < current.key) {
                    current = current.left;
                    if (current == null) {
                        parent.left = newNode;
                        newNode.parent = parent;
                        break;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parent.right = newNode;
                        newNode.parent = parent;
                        break;
                    }
                }
            }

            balanceAfterInsert(newNode);
        }
    }

    private void balanceAfterInsert(Node node) {
        Node parent = null;
        Node grandParent = null;

        while (node != root && isRed(node) && isRed(node.parent)) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (isRed(uncle)) {
                    // Смена цветов
                    uncle.isRed = false;
                    parent.isRed = false;
                    grandParent.isRed = true;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        // Левый малый поворот
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Правый малый поворот
                    rotateRight(grandParent);
                    swapColors(parent, grandParent);
                    node = parent;
                }
            } else {
                Node uncle = grandParent.left;

                if (isRed(uncle)) {
                    // Смена цветов
                    uncle.isRed = false;
                    parent.isRed = false;
                    grandParent.isRed = true;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        // Правый малый поворот
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Левый малый поворот
                    rotateLeft(grandParent);
                    swapColors(parent, grandParent);
                    node = parent;
                }
            }
        }

        root.isRed = false;
    }

    private boolean isRed(Node node) {
        return node != null && node.isRed;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    private void swapColors(Node node1, Node node2) {
        boolean temp = node1.isRed;
        node1.isRed = node2.isRed;
        node2.isRed = temp;
    }
}