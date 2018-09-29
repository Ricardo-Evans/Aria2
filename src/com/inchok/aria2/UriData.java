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
 * The uri data get from FileData.getUris().
 *
 * @author inCHOK
 * @version Version 1.0
 * @see FileData#getUris()
 */
public class UriData implements Serializable {
    private String uri;
    private UriStatus status;

    UriData(String uri, UriStatus status) {
        this.uri = uri;
        this.status = status;
    }

    /**
     * To get the uri.
     *
     * @return Return the uri.
     */
    public String getUri() {
        return this.uri;
    }

    /**
     * To get the status of the uri.
     *
     * @return Return the status of the uri.
     * @see UriStatus
     */
    public UriStatus getStatus() {
        return this.status;
    }

    /**
     * To compare whether the two UriData is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UriData)) return false;
        UriData uriData = (UriData) o;
        return Objects.equals(getUri(), uriData.getUri()) &&
                getStatus() == uriData.getStatus();
    }

    /**
     * To get the hash code of the UriData.
     *
     * @return Return the hash code of the UriData.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUri(), getStatus());
    }

    /**
     * To convert the UriData into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "UriData{" +
                "uri='" + uri + '\'' +
                ", status=" + status +
                '}';
    }
}
