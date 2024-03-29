/*
 * IBinarySearchNode.java v0.10 19/05/08
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

package model.tree;

/**
 * This interface contains all the methods in order to use binary search nodes.
 * It must be implemented by classes or interfaces defining binary search nodes
 * or any other type of binary search node like AVLNode, etc.
 * 
 * @author Damien Rigoni
 * @version 0.10 19/05/08
 * @see IBinaryNode
 */
public interface IBinarySearchNode extends IBinaryNode {

    @Override
    public IBinarySearchNode getRight();

    @Override
    public IBinarySearchNode getLeft();

    @Override
    public IBinarySearchNode getFather();
}
