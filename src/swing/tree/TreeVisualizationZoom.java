/*
 * TreeVisualizationZoom.java v1.00 16/06/08
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Zoom pane for all visualization panels.
 * 
 * @author Julien Hannier
 * @author Pierre Pironin
 * @author Damien Rigoni
 * @version 1.00 16/06/08
 */
class TreeVisualizationZoom extends JPanel {

    private static final long serialVersionUID = 1L;

    private Dimension visualizationArea;

    private static final int zoomMin = 0;

    private static final int zoomMax = 3;

    /**
     * Builds a pane including a zoom of the tree visualization
     * thanks to JScrollPane.
     * 
     * @param treeVisualization the tree visualization
     */
    TreeVisualizationZoom(final TreeVisualization treeVisualization) {
        super(new BorderLayout(4, 4));
        JScrollPane scrollPane = new JScrollPane(treeVisualization);

        scrollPane.addMouseWheelListener(new MouseWheelListener() {

            int zoomValue = zoomMin;

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
                    if (e.getWheelRotation() == -1) {
                        if (zoomValue > zoomMin) {
                            treeVisualization.changeSize(--zoomValue);
                            visualizationArea = treeVisualization.getSizeOfDrawingArea();
                            treeVisualization.setSize(visualizationArea);
                            treeVisualization.updatePositions();
                            treeVisualization.revalidate();
                            treeVisualization.repaint();
                        }
                    } else {
                        if (zoomValue < zoomMax) {
                            treeVisualization.changeSize(++zoomValue);
                            visualizationArea = treeVisualization.getSizeOfDrawingArea();
                            treeVisualization.setSize(visualizationArea);
                            treeVisualization.updatePositions();
                            treeVisualization.revalidate();
                            treeVisualization.repaint();
                        }
                    }
                }
            }
        });
        scrollPane.setPreferredSize(new Dimension(treeVisualization.getWidth(),
                treeVisualization.getHeight()));
        add(scrollPane, BorderLayout.CENTER);
    }
}
