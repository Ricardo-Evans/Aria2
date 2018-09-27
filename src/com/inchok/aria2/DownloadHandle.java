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

public class DownloadHandle {
    private long downloadHandleNative;

    DownloadHandle(long downloadHandleNative) {
        this.downloadHandleNative = downloadHandleNative;
    }

    public DownloadStatus getStatus() {
        return DownloadStatus.DownloadStatus(Aria2.getStatusNative(this.downloadHandleNative));
    }

    public long getTotalLength() {
        return Aria2.getTotalLengthNative(this.downloadHandleNative);
    }

    public long getCompletedLength() {
        return Aria2.getCompletedLengthNative(this.downloadHandleNative);
    }

    public long getUploadLength() {
        return Aria2.getUploadLengthNative(this.downloadHandleNative);
    }

    public String getBitField() {
        return Aria2.getBitFieldNative(this.downloadHandleNative);
    }

    public int getDownloadSpeed() {
        return Aria2.getDownloadSpeedNative(this.downloadHandleNative);
    }

    public int getUploadSpeed() {
        return Aria2.getUploadSpeedNative(this.downloadHandleNative);
    }

    public String getInfoHash() {
        return Aria2.getInfoHashNative(this.downloadHandleNative);
    }

    public long getPieceLength() {
        return Aria2.getPieceLengthNative(this.downloadHandleNative);
    }

    public int getPieceCount() {
        return Aria2.getPieceCountNative(this.downloadHandleNative);
    }

    public int getConnectionCount() {
        return Aria2.getConnectionCountNative(this.downloadHandleNative);
    }

    public int getErrorCode() {
        return Aria2.getErrorCodeNative(this.downloadHandleNative);
    }

    public List<Gid> getFollowedBy() {
        List<Gid> gids = new ArrayList<>();
        for (long gidNative : Aria2.getFollowedByNative(this.downloadHandleNative)) gids.add(new Gid(gidNative));
        return gids;
    }

    public Gid getFollowing() {
        return new Gid(Aria2.getFollowingNative(this.downloadHandleNative));
    }

    public Gid getBelongsTo() {
        return new Gid(Aria2.getBelongsToNative(this.downloadHandleNative));
    }

    public String getDir() {
        return Aria2.getDirNative(this.downloadHandleNative);
    }

    public List<FileData> getFiles() {
        return Aria2.getFilesNative(this.downloadHandleNative);
    }

    public int getFileCount() {
        return Aria2.getFileCountNative(this.downloadHandleNative);
    }

    public FileData getFile(int index) {
        return Aria2.getFileNative(this.downloadHandleNative, index);
    }

    public BtMetaInfoData getBtMetaInfo() {
        return Aria2.getBtMetaInfoNative(this.downloadHandleNative);
    }

    public String getOption(String name) {
        return Aria2.getOptionNative(this.downloadHandleNative, name);
    }

    public KeyValues getOptions() {
        return new KeyValues(Aria2.getOptionsNative(this.downloadHandleNative));
    }

    public void delete() {
        Aria2.deleteDownloadHandleNative(this.downloadHandleNative);
    }

}
