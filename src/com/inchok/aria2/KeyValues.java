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

import java.util.Objects;

/**
 * The key-value pair list.
 * This is usually used to pass the options.
 *
 * @author inCHOK
 * @version Version 1.0
 */
public class KeyValues {
    private long keyValuesNative;

    long getKeyValuesNative() {
        return this.keyValuesNative;
    }

    KeyValues(long keyValuesNative) {
        this.keyValuesNative = keyValuesNative;
    }

    /**
     * To create a default KeyValues.
     */
    public KeyValues() {
        this.keyValuesNative = Aria2.newKeyValuesNative();
    }

    /**
     * To set the value of the specific key.
     *
     * @param key   The specific key.
     * @param value The value to be set.
     */
    public void set(String key, String value) {
        Aria2.setKeyValuesNative(this.keyValuesNative, key, value);
    }

    /**
     * To get the value of the specific key.
     *
     * @param key The specific key.
     * @return Return the value of the specific key.
     */
    public String get(String key) {
        return Aria2.getKeyValuesNative(this.keyValuesNative, key);
    }

    /**
     * To compare whether the two KeyValues is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyValues)) return false;
        KeyValues keyValues = (KeyValues) o;
        return getKeyValuesNative() == keyValues.getKeyValuesNative();
    }

    /**
     * To get the hash code of the KeyValues.
     *
     * @return Return the hash code of the KeyValues.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getKeyValuesNative());
    }

    /**
     * To convert the KeyValues into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "KeyValues{" +
                "keyValuesNative=" + keyValuesNative +
                '}';
    }
}
