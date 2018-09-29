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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The handle to get the detail information of a specific download.
 * <p>To get the download handle, use Session.getDownloadHandle(Gid).</p>
 * <p>The lifetime of a DownloadHandle is before the next call of Session.run() or Session.sessionFinal(), you must call DownloadHandle.delete() before that.</p>
 * <p>It's <strong>responsibility</strong> for you to call DownloadHandle.delete() to delete the download handle.</p>
 *
 * @author inCHOK
 * @version Version 1.0
 * @see Session#getDownloadHandle(Gid)
 * @see DownloadHandle#delete()
 */
public class DownloadHandle {
    private long downloadHandleNative;

    DownloadHandle(long downloadHandleNative) {
        this.downloadHandleNative = downloadHandleNative;
    }

    /**
     * To get the status of this download.
     *
     * @return Return the status of this download.
     * @see DownloadStatus
     */
    public DownloadStatus getStatus() {
        return Aria2.getStatusNative(this.downloadHandleNative);
    }

    /**
     * To get the total length of this download in bytes.
     *
     * @return Return the total length of this download in bytes.
     */
    public long getTotalLength() {
        return Aria2.getTotalLengthNative(this.downloadHandleNative);
    }

    /**
     * To get the completed length of this download in bytes.
     *
     * @return Return the completed length of this download in bytes.
     */
    public long getCompletedLength() {
        return Aria2.getCompletedLengthNative(this.downloadHandleNative);
    }

    /**
     * To get the uploaded length of this download in bytes.
     *
     * @return Return the uploaded length of this download in bytes.
     */
    public long getUploadedLength() {
        return Aria2.getUploadedLengthNative(this.downloadHandleNative);
    }

    /**
     * To get the download progress of this download in bit-string.
     * <p>The highest bit corresponds to piece index 0. The set bit indicates the piece is available and the unset bit indicates the piece is missing. The spare bits at the end are set to zero.</p>
     * <p>If the download has not started yet, return empty string.</p>
     *
     * @return Return the bit-string indicating the download progress of this download.
     */
    public String getBitField() {
        return Aria2.getBitFieldNative(this.downloadHandleNative);
    }

    /**
     * To get the download speed of this download measured in bytes/sec.
     *
     * @return Return the download speed of this download measured in bytes/sec.
     */
    public int getDownloadSpeed() {
        return Aria2.getDownloadSpeedNative(this.downloadHandleNative);
    }

    /**
     * To get the upload speed of this download measured in bytes/sec.
     *
     * @return Return the upload speed of this download measured in bytes/sec.
     */
    public int getUploadSpeed() {
        return Aria2.getUploadSpeedNative(this.downloadHandleNative);
    }

    /**
     * To get the info hash of this download.
     * Return a 20 bytes info hash if bit torrent transfer is invoked, otherwise return empty string.
     *
     * @return Return the info hash of this download.
     */
    public String getInfoHash() {
        return Aria2.getInfoHashNative(this.downloadHandleNative);
    }

    /**
     * To get the piece length of this download in bytes.
     *
     * @return Return the piece length of this download in bytes.
     */
    public long getPieceLength() {
        return Aria2.getPieceLengthNative(this.downloadHandleNative);
    }

    /**
     * To get the count of the pieces of this download.
     *
     * @return Return the count of the pieces of this download.
     */
    public int getPieceCount() {
        return Aria2.getPieceCountNative(this.downloadHandleNative);
    }

    /**
     * To get the count of the connections(peers/servers) of this download.
     *
     * @return Return the count of the connections(peers/servers) of this download.
     */
    public int getConnectionCount() {
        return Aria2.getConnectionCountNative(this.downloadHandleNative);
    }

    /**
     * To get the last error code occurred in this download.
     * This value has its meaning only for stopped/completed downloads.
     *
     * @return Return the last error code occurred in this download.
     * @see ErrorCode
     */
    public int getErrorCode() {
        return Aria2.getErrorCodeNative(this.downloadHandleNative);
    }

    /**
     * To get the list of gids which are generated by the consequence of this download.
     * <p>This value is useful to track auto generated downloads. If there is no such downloads, this method will return empty list.</p>
     * <p>For example, when Aria2 downloaded metalink files, it generates downloads described in them.(If option <cite>follow-metalink</cite>=true.)</p>
     * <p>This is a reverse method of DownloadHandle.getFollowing().</p>
     *
     * @return Return the list of gids which are generated by the consequence of this download.
     * @see DownloadHandle#getFollowing()
     * @see Gid
     */
    public List<Gid> getFollowedBy() {
        List<Gid> gids = new ArrayList<>();
        for (long gidNative : Aria2.getFollowedByNative(this.downloadHandleNative)) gids.add(new Gid(gidNative));
        return gids;
    }

    /**
     * To get the gid of the download which generates this download.
     * This is a reverse method of DownloadHandle.getFollowedBy().
     *
     * @return Return the gid of the download which generates this download.
     * @see DownloadHandle#getFollowedBy()
     * @see Gid
     */
    public Gid getFollowing() {
        return new Gid(Aria2.getFollowingNative(this.downloadHandleNative));
    }

    /**
     * To get the gid of the parent download.
     * <p>Some downloads are a part of another download. That is the parent download.</p>
     * <p>For example, if a file in metalink has bit torrent resource, the download of ".torrent" is a part of that file.</p>
     * <p>If this download doesn't have a parent download, a invalid gid is returned.(Gid.isNull()=true)</p>
     *
     * @return Return the gid of the parent download.
     * @see Gid
     */
    public Gid getBelongsTo() {
        return new Gid(Aria2.getBelongsToNative(this.downloadHandleNative));
    }

    /**
     * To get the directory to save files of this download.
     *
     * @return Return the directory to save files of this download.
     */
    public String getDir() {
        return Aria2.getDirNative(this.downloadHandleNative);
    }

    /**
     * To get the list of files data this download contains.
     *
     * @return Return the list of files data this download contains.
     * @see FileData
     */
    public List<FileData> getFilesData() {
        return Aria2.getFilesDataNative(this.downloadHandleNative);
    }

    /**
     * To get the count of files this download contains.
     *
     * @return Return the count of files this download contains.
     */
    public int getFileCount() {
        return Aria2.getFileCountNative(this.downloadHandleNative);
    }

    /**
     * To get the file data at the specific index.
     * This method is undefined when the index is out of bound.
     *
     * @param index The specific index.(<strong>This index is 1-based.</strong>)
     * @return Return the file data at the specific index.
     */
    public FileData getFileData(int index) {
        return Aria2.getFileNative(this.downloadHandleNative, index);
    }

    /**
     * To get the information retrieved from ".torrent" file.
     * This method is meaningful only when bit torrent transfer is invoked in this download and this download is not stopped/completed.
     *
     * @return Return the information retrieved from ".torrent" file.
     */
    public BtMetaInfoData getBtMetaInfo() {
        return Aria2.getBtMetaInfoNative(this.downloadHandleNative);
    }

    /**
     * To get the option of this download of the specific name.
     *
     * @param name The option name.
     * @return Return the option of this download of the specific name.
     * @see Option
     */
    public String getOption(String name) {
        return Aria2.getOptionNative(this.downloadHandleNative, name);
    }

    /**
     * To get all the options of this download.
     *
     * @return Return all the options of this download.
     * @see KeyValues
     * @see Option
     */
    public KeyValues getOptions() {
        return new KeyValues(Aria2.getOptionsNative(this.downloadHandleNative));
    }

    /**
     * To delete this download handle.
     * <p>The lifetime of a DownloadHandle is before the next call of Session.run() or Session.sessionFinal(), you must call this method before that.</p>
     * <p>It's <strong>responsibility</strong> for you to call this method to delete the download handle.</p>
     */
    public void delete() {
        Aria2.deleteDownloadHandleNative(this.downloadHandleNative);
    }

    /**
     * To compare whether two DownloadHandle is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DownloadHandle)) return false;
        DownloadHandle that = (DownloadHandle) o;
        return downloadHandleNative == that.downloadHandleNative;
    }

    /**
     * To get the hash code of the DownloadHandle.
     *
     * @return Return the hash code of the DownloadHandle.
     */
    @Override
    public int hashCode() {
        return Objects.hash(downloadHandleNative);
    }

    /**
     * To convert the DownloadHandle into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "DownloadHandle{" +
                "downloadHandleNative=" + downloadHandleNative +
                '}';
    }
}
