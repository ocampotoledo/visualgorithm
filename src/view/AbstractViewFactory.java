/*
 * AbstractViewFactory.java v1.00 16/06/08
 *
 * Visualgorithm
 * Copyright (C) Hannier, Pironin, Rigoni (bx1gl@googlegroups.com)
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

package view;

import swing.SwingViewFactory;
import model.DataStructureType;
import model.Model;
import model.DataStructure;
import controller.PrincipalController;
import controller.TabController;

/**
 * Abstract factory of swing views.
 * 
 * @author Julien Hannier
 * @author Pierre Pironin
 * @author Damien Rigoni
 * @version 1.00 16/06/08
 */
public abstract class AbstractViewFactory {

    /**
     * Creates the factory.
     * 
     * @return the factory
     */
    public static AbstractViewFactory getFactory() {
        return new SwingViewFactory();
    }
    
    /**
     * Creates the principal view of the software.
     * 
     * @return the principal view
     */
    public abstract IModelView createGraphicUserInterface(
            Model model, PrincipalController controller);

    /**
     * Creates the tab view.
     * 
     * @return the tab view
     */
    public abstract IDataStructureView createTabPage(
            DataStructure dataStructure, DataStructureType type,
            TabController controller);
}
