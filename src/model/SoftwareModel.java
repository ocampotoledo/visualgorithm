/*
 * SoftwareModel.java v0.10 16/06/08
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

package model;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * This class defines all the methods in order to modify the software model. The
 * software model is composed by data structure models. There is a data
 * structure model for each data structure in the software. This class is not
 * designed for inheritance.
 * 
 * @author Julien Hannier
 * @version 0.10 16/06/08
 * @see ISoftwareModel
 */
public final class SoftwareModel implements ISoftwareModel {

    private List<IDataStructureModel> dataStructureModels;

    /**
     * Builds the software model. The software model is a list of data structure
     * models.
     */
    public SoftwareModel() {
        dataStructureModels = new ArrayList<IDataStructureModel>();
    }

    @Override
    public IDataStructureModel getDataStructureModel(int index)
            throws IndexOutOfBoundsException {
        if (index >= dataStructureModels.size()) {
            throw new IndexOutOfBoundsException("You have given an index out" +
                " of bounds");
        }
        return dataStructureModels.get(index);
    }

    @Override
    public void addModelListener(EventListener listener) throws IllegalArgumentException {
    }

    @Override
    public void removeModelListener(EventListener listener) throws IllegalArgumentException {
    }

    @Override
    public void addDataStructureModel(IDataStructureModel dataStructureModel) {
        dataStructureModels.add(dataStructureModel);
    }

    @Override
    public void deleteDataStructureModel(int index) throws IndexOutOfBoundsException {
        if (index >= dataStructureModels.size()) {
            throw new IndexOutOfBoundsException("You have given an index out" +
                " of bounds");
        }
        dataStructureModels.remove(index);
    }

    @Override
    public void removeAllDataStructureModels() {
        dataStructureModels.clear();
    }
}
