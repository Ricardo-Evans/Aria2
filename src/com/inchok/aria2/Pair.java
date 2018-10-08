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
import java.util.Objects;

/**
 * The key-value pair used in KeyValues.(Both key and value are String.)
 *
 * @author inCHOK
 * @version Version 1.0
 * @see KeyValues
 */
public class Pair implements Serializable {
    private String key;
    private String value;

    /**
     * The constructor to create a Pair.
     *
     * @param key   The key.
     * @param value The value.
     */
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * To get the key.
     *
     * @return Return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * To set the key.
     *
     * @param key The key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * To get the value.
     *
     * @return Return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * To set the value.
     *
     * @param value The value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * To compare whether the two Pair are equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return Objects.equals(getKey(), pair.getKey()) &&
                Objects.equals(getValue(), pair.getValue());
    }

    /**
     * To get the hash code of the Pair.
     *
     * @return Return the hash code of the Pair.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }

    /**
     * To convert the Pair into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "Pair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
