/*
 * TreeVisualization.java v1.00 16/06/08
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

package swing.tree;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import controller.BinaryTreeTabController;
import model.tree.IBinaryNode;
import model.tree.RedBlackNode;
import swing.tree.GraphicNode.GraphicNodeColor;

/**
 * Visualization of all types of binary trees.
 * 
 * @author Julien Hannier
 * @author Pierre Pironin
 * @author Damien Rigoni
 * @version 1.00 16/06/08
 */
class TreeVisualization extends JPanel {

    private static final long serialVersionUID = 1L;

    private BinaryTreeTabController controller;

    private List<GraphicNode> graphicNodes;

    private int sizeOfNodes;

    private int heightBetweenNodes;

    private int widthBetweenBrotherNodes;

    private int widthBetweenNodes;

    private int yPositionRootNode;
    
    private int indexOfSelectedNode;

    private int xPositionOfSelectedNode;

    private int yPositionOfSelectedNode;

    private GraphicNodeColor colorOfSelectedNode;

    private boolean justCalculate, deleteMode;

    /**
     * Builds the tree visualization.
     * 
     * @param c the controller
     * @param width the width of the panel
     * @param height the height of the panel
     */
    TreeVisualization(BinaryTreeTabController c, int width, int height) {
        controller = c;
        graphicNodes = new ArrayList<GraphicNode>();
        sizeOfNodes = 30;
        heightBetweenNodes = 35;
        widthBetweenBrotherNodes = 20;
        widthBetweenNodes = 15;
        yPositionRootNode = 10 + sizeOfNodes / 2;
        justCalculate = false;
        deleteMode = false;

        setSize(width, height);
        setBackground(Color.WHITE);
        addListeners();
    }

    private void addListeners() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (deleteMode) {
                    int nodeIndex = indexOfSelectedNode(e.getX(), e.getY());
                    if (nodeIndex > -1) {
                        GraphicNode node = graphicNodes.get(nodeIndex);
                        controller.deleteNode(node.getNodeKey());
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                deleteMode = false;
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (!deleteMode) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        int mouseX = e.getX();
                        int mouseY = e.getY();
                        int nodeIndex = indexOfSelectedNode(mouseX, mouseY);
                        if (nodeIndex != -1) {
                            GraphicNode node = getGraphicNode(nodeIndex);
                            indexOfSelectedNode = nodeIndex;
                            xPositionOfSelectedNode = node.getXPosition();
                            yPositionOfSelectedNode = node.getYPosition();
                            colorOfSelectedNode = node.getNodeColor();
                            changeGraphicNodeColor(nodeIndex, GraphicNodeColor.BLUE);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (indexOfSelectedNode != -1) {
                    changeGraphicNodeColor(indexOfSelectedNode,
                        colorOfSelectedNode);
                    if (indexOfSelectedNode > 0) {
                        moveGraphicNode(indexOfSelectedNode,
                            xPositionOfSelectedNode, yPositionOfSelectedNode);
                        xPositionOfSelectedNode = -1;
                        yPositionOfSelectedNode = -1;
                    }
                    indexOfSelectedNode = -1;
                    colorOfSelectedNode = null;
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
                    if (indexOfSelectedNode > 0) {
                        moveGraphicNode(indexOfSelectedNode, e.getX(), e.getY());
                    }
                }
            }
        });
    }

    /**
     * Launches the deleteMode.
     */
    void launchDeleteMode() {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        deleteMode = true;
    }

    private void changeGraphicNodeColor(int nodeIndex, GraphicNodeColor color) {
        GraphicNode node = getGraphicNode(nodeIndex);
        node.changeColor(color);
        repaint(node.getXPosition() - sizeOfNodes / 2 - 1, node.getYPosition()
                - sizeOfNodes / 2 - 1, sizeOfNodes + 2, sizeOfNodes + 2);
    }

    private void moveGraphicNode(int nodeIndex, int x, int y) {
        justCalculate = true;
        GraphicNode movedNode = getGraphicNode(nodeIndex);
        int xMovedNode = movedNode.getXPosition();
        int yMovedNode = movedNode.getYPosition();

        movedNode.changeNodePosition(x, y);
        moveGraphicSubNodes(nodeIndex, x - xMovedNode, y - yMovedNode);
        repaint();
    }

    private void moveGraphicSubNodes(int nodeIndex, int shiftX, int shiftY) {
        if (2 * nodeIndex + 1 < graphicNodes.size()) {
            GraphicNode left = getGraphicNode(2 * nodeIndex + 1);
            if (left != null) {
                moveGraphicSubNodes(2 * nodeIndex + 1, shiftX, shiftY);
                left.changeNodePosition(left.getXPosition() + shiftX, left.getYPosition()
                        + shiftY);
            }
        }
        if (2 * nodeIndex + 2 < graphicNodes.size()) {
            GraphicNode right = getGraphicNode(2 * nodeIndex + 2);
            if (right != null) {
                moveGraphicSubNodes(2 * nodeIndex + 2, shiftX, shiftY);
                right.changeNodePosition(right.getXPosition() + shiftX, right.getYPosition()
                        + shiftY);
            }
        }
    }
    
    /**
     * Changes the size of the graphic tree. Size factor must be between 0 and 3
     * included.
     * 
     * @param sizeFactor the size factor
     */
    void changeSize(int sizeFactor) {
        sizeOfNodes = 30 + sizeFactor * 15;
        heightBetweenNodes = 35 + sizeFactor * 15;
        widthBetweenBrotherNodes = 20 + sizeFactor * 15;
        widthBetweenNodes = 15 + sizeFactor * 15;
        yPositionRootNode = 10 + sizeOfNodes / 2;
        for (GraphicNode node : graphicNodes) {
            if (node != null) {
                node.changeNodeSize(sizeOfNodes);
            }
        }
    }

    private int indexOfSelectedNode(int x, int y) {
        int index = -1;
        for (int i = 0; i < graphicNodes.size(); i++) {
            GraphicNode node = graphicNodes.get(i);
            if (node != null) {
                if ((x < node.getXPosition() + node.getNodeSize() / 2)
                        && (x > node.getXPosition() - node.getNodeSize() / 2)
                        && (y < node.getYPosition() + node.getNodeSize() / 2)
                        && (y > node.getYPosition() - node.getNodeSize() / 2)) {
                    index = i;
                }
            }
        }
        return index;
    }

    private GraphicNode getGraphicNode(int index) {
        return graphicNodes.get(index);
    }
    
    private int xPositionOfMinNode() {
        int i = 0, j = 0;
        while ((i < graphicNodes.size()) && (graphicNodes.get(i) != null)) {
            j = i;
            i = 2 * i + 1;
        }
        return graphicNodes.get(j).getXPosition();
    }

    private int xPositionOfMaxNode() {
        int i = 0, j = 0;
        while ((i < graphicNodes.size()) && (graphicNodes.get(i) != null)) {
            j = i;
            i = 2 * i + 2;
        }
        return graphicNodes.get(j).getXPosition();
    }

    /**
     * Returns the dimension of the drawing area.
     * 
     * @return the dimension of the drawing area
     */
    Dimension getSizeOfDrawingArea() {
        int widthSize = 0, heightSize = 0;
        int length = graphicNodes.size();
        if (length > 0) {
            int height = length == 1 ? 0 : (int) Math.round(Math
                    .sqrt((length + 1) / 2));
            widthSize = xPositionOfMaxNode() - xPositionOfMinNode()
                    + sizeOfNodes + 40;
            heightSize = height * heightBetweenNodes + yPositionRootNode
                    + sizeOfNodes / 2 + 10;
        }
        return new Dimension(widthSize, heightSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!justCalculate) {
            updatePositions();
        } else {
            justCalculate = false;
        }
        drawEdges(g);
        for (GraphicNode node : graphicNodes) {
            if (node != null) {
                node.paint(g);
            }
        }
    }

    private void drawEdges(Graphics g) {
        int i = 0;
        while ((2 * i + 1) < graphicNodes.size()) {
            GraphicNode father = graphicNodes.get(i);
            if (father != null) {
                GraphicNode left = graphicNodes.get(2 * i + 1);
                if (left != null) {
                    g.drawLine(father.getXPosition(), father.getYPosition(),
                        left.getXPosition(), left.getYPosition());
                }
                if ((2 * i + 2) < graphicNodes.size()) {
                    GraphicNode right = graphicNodes.get(2 * i + 2);
                    if (right != null) {
                        g.drawLine(father.getXPosition(),
                            father.getYPosition(), right.getXPosition(), right
                                    .getYPosition());
                    }
                }
            }
            i++;
        }
    }

    private void updatePositions() {
        int length = graphicNodes.size();
        if (length > 0) {
            int height = length == 1 ? 0 : (int) Math.round(Math
                    .sqrt((length + 1) / 2));
            int width = 1;
            int indexStop = 1;
            int index = 0;
            int y, x = getWidth() / 2;
            int positionDifference = calculateNodePositionDifference(height);

            for (int i = 0; i <= height; i++) {
                while (index < indexStop) {
                    GraphicNode node = graphicNodes.get(index);
                    if (node != null) {
                        y = yPositionRootNode + i * heightBetweenNodes;
                        node.changeNodePosition(x, y);
                    }
                    if (i < height) {
                        if ((i > 0) && (index < indexStop - 1)) {
                            x += positionDifference;
                        }
                    } else {
                        if ((index % 2) == 0) {
                            x += widthBetweenNodes + sizeOfNodes;
                        } else {
                            x += widthBetweenBrotherNodes + sizeOfNodes;
                        }
                    }
                    index++;
                }
                x -= (width - 1) * positionDifference;
                if (i < height - 1) {
                    if (i > 0) {
                        positionDifference = positionDifference / 2;
                    }
                    x -= positionDifference / 2;
                } else {
                    x -= widthBetweenBrotherNodes / 2 + sizeOfNodes / 2;
                }
                width *= 2;
                indexStop += width;
            }
        }
    }

    /**
     * Calculates the position of the nodes.
     * 
     * @param array the array
     */
    <N extends IBinaryNode<N>> void calculatePositions(List<N> array) {
        justCalculate = true;
        graphicNodes.clear();
        int length = array.size();
        if (length > 0) {
            int height = length == 1 ? 0 : (int) Math.round(Math
                    .sqrt((length + 1) / 2));
            int width = 1;
            int indexStop = 1;
            int index = 0;
            int key, y, x = getWidth() / 2;
            GraphicNodeColor color;
            int positionDifference = calculateNodePositionDifference(height);

            for (int i = 0; i <= height; i++) {
                while (index < indexStop) {
                    IBinaryNode<?> node = array.get(index);
                    if (node != null) {
                        key = node.getKey();
                        y = yPositionRootNode + i * heightBetweenNodes;
                        if (node instanceof RedBlackNode) {
                            color = ((RedBlackNode) node).isRed() ? GraphicNodeColor.RED
                                    : GraphicNodeColor.BLACK;
                        } else {
                            color = GraphicNodeColor.YELLOW;
                        }
                        graphicNodes.add(new GraphicNode(key, x, y,
                                sizeOfNodes, color));
                    } else {
                        graphicNodes.add(null);
                    }
                    if (i < height) {
                        if ((i > 0) && (index < indexStop - 1)) {
                            x += positionDifference;
                        }
                    } else {
                        if ((index % 2) == 0) {
                            x += widthBetweenNodes + sizeOfNodes;
                        } else {
                            x += widthBetweenBrotherNodes + sizeOfNodes;
                        }
                    }
                    index++;
                }
                x -= (width - 1) * positionDifference;
                if (i < height - 1) {
                    if (i > 0) {
                        positionDifference = positionDifference / 2;
                    }
                    x -= positionDifference / 2;
                } else {
                    x -= widthBetweenBrotherNodes / 2 + sizeOfNodes / 2;
                }
                width *= 2;
                indexStop += width;
            }
            repaint();
        }
    }

    private int calculateNodePositionDifference(int height) {
        if ((height == 0) || (height == 1)) {
            return 0;
        } else if (height == 2) {
            return widthBetweenBrotherNodes + widthBetweenNodes + 2
                    * sizeOfNodes;
        } else {
            return 2 * calculateNodePositionDifference(height - 1);
        }
    }
}
