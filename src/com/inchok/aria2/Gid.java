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
 * The unique identifier of each download.
 * In fact, gid is a 64-bits number.
 *
 * @author inCHOK
 * @version Version 1.0
 */
public class Gid implements Serializable {
    private long gid;

    /**
     * This constructor to create a custom made gid.
     * If you prefer to use auto-generated gids instead of custom made gids, you just to pass <cite>null</cite> when you add a new download.
     *
     * @param gid The real identifier, you have the responsibility to make sure it's unique.
     */
    public Gid(long gid) {
        this.gid = gid;
    }

    /**
     * To get the textual representation hex of the gid.
     *
     * @return Return textual representation hex of the gid.
     */
    public String toHex() {
        return Aria2.gidToHexNative(this.gid);
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
        return Aria2.isNullNative(this.gid);
    }

    /**
     * To get the real gid(A 64-bit number).
     *
     * @return Return the real gid(A 64-bit number).
     */
    public long getGid() {
        return this.gid;
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
        return getGid() == gid.getGid();
    }

    /**
     * To get the hash code of the Gid.
     *
     * @return Return the hash code of the Gid.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getGid());
    }

    /**
     * To convert the Gid into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "Gid{" +
                "gid=" + this.getGid() +
                '}';
    }
}
