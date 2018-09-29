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
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The bit torrent meta info data retrieved from ".torrent" file.
 * To get these data, call DownloadHandle.getBtMetaInfoData().
 *
 * @author inCHOK
 * @version Version 1.0
 * @see DownloadHandle#getBtMetaInfo()
 */
public class BtMetaInfoData implements Serializable {
    private List<List<String>> announceList;
    private String comment;
    private Date creationDate;
    private BtFileMode mode;
    private String name;

    BtMetaInfoData(List<List<String>> announceList, String comment, Date creationDate, BtFileMode mode, String name) {
        this.announceList = announceList;
        this.comment = comment;
        this.creationDate = creationDate;
        this.mode = mode;
        this.name = name;
    }

    /**
     * To get the announce URIs of the torrent file.
     * If torrent contains <cite>announce</cite> instead of <cite>announce-list</cite>, <cite>announce</cite> is converted into <cite>announce-list</cite> format.
     *
     * @return Return the list of lists of announce URIs of the torrent file.
     */
    public List<List<String>> getAnnounceList() {
        return this.announceList;
    }

    /**
     * To get the comment of the torrent file.
     * <cite>comment-utf8</cite> is used if available.
     *
     * @return Return the comment of the torrent file.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * To get the create time of the torrent file.
     *
     * @return Return the create time of the torrent file.
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * To get the bit torrent file mode.
     *
     * @return Return the bit torrent file mode
     * @see BtFileMode
     */
    public BtFileMode getMode() {
        return this.mode;
    }

    /**
     * To get the <cite>name</cite> in <cite>info</cite> dictionary.
     * <cite>name-utf8</cite> is used instead of <cite>name</cite> if available.
     *
     * @return Return the <cite>name</cite> in <cite>info</cite> dictionary.
     */
    public String getName() {
        return this.name;
    }

    /**
     * To compare whether the two BtMetaInfoData is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BtMetaInfoData)) return false;
        BtMetaInfoData that = (BtMetaInfoData) o;
        return Objects.equals(getAnnounceList(), that.getAnnounceList()) &&
                Objects.equals(getComment(), that.getComment()) &&
                Objects.equals(getCreationDate(), that.getCreationDate()) &&
                getMode() == that.getMode() &&
                Objects.equals(getName(), that.getName());
    }

    /**
     * To get the hash code of the BtMetaInfoData.
     *
     * @return Return the hash code of the BtMetaInfoData.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getAnnounceList(), getComment(), getCreationDate(), getMode(), getName());
    }

    /**
     * To convert the BtMetaInfoData into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "BtMetaInfoData{" +
                "announceList=" + announceList +
                ", comment='" + comment + '\'' +
                ", creationDate=" + creationDate +
                ", mode=" + mode +
                ", name='" + name + '\'' +
                '}';
    }
}