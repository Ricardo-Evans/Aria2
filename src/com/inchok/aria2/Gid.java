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
 * The unique identifier of each download.
 * <p>In fact, gid is a 64-bits number.</p>
 * <p>To create a custom gid, use static method Gid.toGid(String).</p>
 *
 * @author inCHOK
 * @version Version 1.0
 */
public class Gid {
    private long gidNative;
    private boolean isNewed = false;

    Gid(long gidNative) {
        this.gidNative = gidNative;
    }

    /**
     * To get the textual representation hex of the gid.
     *
     * @return Return textual representation hex of the gid.
     */
    public String toHex() {
        return Aria2.gidToHexNative(this.gidNative);
    }

    /**
     * To get the gid converted from the textual representation hex.
     *
     * @param hexGid The textual representation hex.
     * @return Return the gid converted from the textual representation hex.
     */
    public static Gid toGid(String hexGid) {
        return new Gid(Aria2.hexToGidNative(hexGid));
    }

    /**
     * To check whether this gid is invalid.
     *
     * @return Return whether this gid is invalid.
     */
    public boolean isNull() {
        return Aria2.isNullNative(this.gidNative);
    }

    long getGidNative() {
        return this.gidNative;
    }

    void setGidNative(long gidNative) {
        this.gidNative = gidNative;
    }

    /**
     * To get the real gid(A 64-bit number).
     *
     * @return Return the real gid(A 64-bit number).
     */
    public long getGid() {
        return Aria2.getGidNative(this.gidNative);
    }

    /**
     * To set the real gid(A 64-bit number).
     *
     * @param gid The real gid(A 64-bit number).
     */
    public void setGid(long gid) {
        Aria2.setGidNative(this.gidNative, gid);
    }

    /**
     * To compare whether the two Gids are equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gid)) return false;
        Gid gid = (Gid) o;
        return getGidNative() == gid.getGidNative();
    }

    /**
     * To get the hash code of the Gid.
     *
     * @return Return the hash code of the Gid.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getGidNative());
    }

    /**
     * To convert the Gid into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "Gid{" +
                "gidNative=" + gidNative +
                '}';
    }
}
