/*
 * BinarySearchTreeFile.java v0.10 02/07/08
 *
 * Visualgorithm
 * Copyright (C) Hannier, Pironin, Rigoni (visualgo@googlegroups.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package io.tree;

import model.tree.BinarySearchNode;
import model.tree.BinarySearchTree;
import model.tree.IBinaryNode;

/**
 * This class defines the file input and output operations for binary search
 * trees. It is not designed for inheritance. The format of the file is defined
 * in <tt>TreeFile</tt>.
 * 
 * @author Damien Rigoni
 * @version 0.10 02/07/08
 * @see TreeFile
 */
final class BinarySearchTreeFile extends TreeFile {

    /**
     * Builds the binary search tree file. The format of the file is defined in
     * <tt>TreeFile</tt>.
     */
    BinarySearchTreeFile() {
        super();
    }

    @Override
    protected BinarySearchTree createEmptyBinaryTree() {
        return new BinarySearchTree();
    }

    @Override
    protected BinarySearchTree createBinaryTree(int key) {
        return new BinarySearchTree(key);
    }

    @Override
    protected void setLeftNode(IBinaryNode node, int childNodeNumber) {
        assert(node instanceof BinarySearchNode);

        ((BinarySearchNode) node).setLeft(
                new BinarySearchNode(Integer.parseInt(
                nodeVector.get(childNodeNumber)[KEY_INDEX])));
    }

    @Override
    protected void setRightNode(IBinaryNode node, int childNodeNumber) {
        assert(node instanceof BinarySearchNode);

        ((BinarySearchNode) node).setRight(
                new BinarySearchNode(Integer.parseInt(
                nodeVector.get(childNodeNumber)[KEY_INDEX])));
    }
}
