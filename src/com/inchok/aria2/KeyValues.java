/*
 * Copyright (C) 2018 inCHOK
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact the author by inchoksteve@gmail.com
 */

package com.inchok.aria2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The key-value pair list.
 * This is usually used to pass the options.
 *
 * @author inCHOK
 * @version Version 1.0
 */
public class KeyValues extends ArrayList<Pair> implements Serializable {

    /**
     * To create a empty KeyValues.
     */
    public KeyValues() {
        super();
    }

    /**
     * The clone constructor.
     *
     * @param values The ordinary KeyValues.
     */
    public KeyValues(KeyValues values) {
        super(values);
    }
}