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

import java.util.List;
import java.util.Objects;

/**
 * The data of files to download.
 * To get these data, use DownloadHandle.getFilesData() or DownloadHandle.getFileData(int).
 *
 * @author inCHOK
 * @version Version 1.0
 * @see DownloadHandle#getFilesData()
 * @see DownloadHandle#getFileData(int)
 */
public class FileData {
    private int index;
    private String path;
    private long length;
    private long completedLength;
    private boolean selected;
    private List<UriData> uris;

    FileData(int index, String path, long length, long completedLength, boolean selected, List<UriData> uris) {
        this.index = index;
        this.path = path;
        this.length = length;
        this.completedLength = completedLength;
        this.selected = selected;
        this.uris = uris;
    }

    /**
     * To get the <strong>1-based</strong> index of the file in the download.
     * <p>This index is used to get the file by calling DownloadHandle.getFileData(int).</p>
     * <p>This index is the same order with the files in multi-files torrent.</p>
     *
     * @return Return the <strong>1-based</strong> index of the file in the download.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * To get the local path of the file.
     *
     * @return Return the local path of the file.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * To get the file size in bytes.
     * This is not the current size of the local file.
     *
     * @return Return the file size in bytes.
     */
    public long getLength() {
        return this.length;
    }

    /**
     * To get the completed length of this file in bytes.
     * Please note that it is possible that sum of completedLength is less than the return value of <cite>DownloadHandle.getCompletedLength()</cite>. This is because the completedLength only calculates completed pieces. On the other hand, <cite>DownloadHandle.getCompletedLength()</cite> takes into account of partially completed piece.
     *
     * @return Return the completed length of this file in bytes.
     * @see DownloadHandle#getCompletedLength()
     */
    public long getCompletedLength() {
        return this.completedLength;
    }

    //TODO Option.SELECT_FILE

    /**
     * To get whether this file is selected by <cite>select-file</cite> option.
     * If option <cite>select-file</cite> is not specified or this is single torrent or no torrent download, this value is always true.
     *
     * @return Return whether this file is selected by <cite>select-file</cite> option.
     * @see Option
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * To get the list of uris for this file.
     *
     * @return Return the list of uris for this file.
     */
    public List<UriData> getUris() {
        return this.uris;
    }

    /**
     * To compare whether the two FileData is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileData)) return false;
        FileData fileData = (FileData) o;
        return getIndex() == fileData.getIndex() &&
                getLength() == fileData.getLength() &&
                getCompletedLength() == fileData.getCompletedLength() &&
                isSelected() == fileData.isSelected() &&
                Objects.equals(getPath(), fileData.getPath()) &&
                Objects.equals(getUris(), fileData.getUris());
    }

    /**
     * To get the hash code of the FileData.
     *
     * @return Return the hash code of the FileData.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getPath(), getLength(), getCompletedLength(), isSelected(), getUris());
    }

    /**
     * To convert the FileData into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "FileData{" +
                "index=" + index +
                ", path='" + path + '\'' +
                ", length=" + length +
                ", completedLength=" + completedLength +
                ", selected=" + selected +
                ", uris=" + uris +
                '}';
    }
}
