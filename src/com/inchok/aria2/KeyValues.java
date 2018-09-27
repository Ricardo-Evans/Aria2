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
 * Contact the author by aixin1729907139@gmail.com
 */

package com.inchok.aria2;

import javafx.util.Pair;

import java.util.List;

public class KeyValues {
    private long keyValuesNative;

    long getKeyValuesNative() {
        return this.keyValuesNative;
    }

    KeyValues(long keyValuesNative) {
        this.keyValuesNative = keyValuesNative;
    }

    public KeyValues() {
        this.keyValuesNative = Aria2.newKeyValuesNative();
    }

    public void set(String key, String value) {
        Aria2.setKeyValuesNative(this.keyValuesNative, key, value);
    }

    public String get(String key) {
        return Aria2.getKeyValuesNative(this.keyValuesNative, key);
    }

}
