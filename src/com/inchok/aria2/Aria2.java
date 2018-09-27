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

import java.util.List;

public class Aria2 {
    static {
        System.loadLibrary("aria2-native");
    }

    public static final int RESPONSE_OK = 0;

    public static int initialize() {
        return Aria2.initializeNative();
    }

    public static int deInitialize() {
        return Aria2.deInitializeNative();
    }

    private static native int initializeNative();

    private static native int deInitializeNative();

    static native long newSessionNative(long keyValuesNative, long sessionConfigNative);

    static native int finalSessionNative(long sessionNative);

    static native long hexToGidNative(String hexGid);

    static native String gidToHexNative(long gidNative);

    static native boolean isNullNative(long gidNative);

    static native long getGidNative(long gidNative);

    static native void setGidNative(long gidNative, long gid);

    static native long newGidNative(long gid);

    static native long newKeyValuesNative();

    static native void setKeyValuesNative(long keyValuesNative, String key, String value);

    static native String getKeyValuesNative(long keyValuesNative, String key);

    static native long newSessionConfigNative();

    static native int getStatusNative(long downloadHandleNative);

    static native long getTotalLengthNative(long downloadHandleNative);

    static native long getCompletedLengthNative(long downloadHandleNative);

    static native long getUploadLengthNative(long downloadHandleNative);

    static native String getBitFieldNative(long downloadHandleNative);

    static native int getDownloadSpeedNative(long downloadHandleNative);

    static native int getUploadSpeedNative(long downloadHandleNative);

    static native String getInfoHashNative(long downloadHandleNative);

    static native long getPieceLengthNative(long downloadHandleNative);

    static native int getPieceCountNative(long downloadHandleNative);

    static native int getConnectionCountNative(long downloadHandleNative);

    static native int getErrorCodeNative(long downloadHandleNative);

    static native List<Long> getFollowedByNative(long downloadHandleNative);

    static native long getFollowingNative(long downloadHandleNative);

    static native long getBelongsToNative(long downloadHandleNative);

    static native String getDirNative(long downloadHandleNative);

    static native List<FileData> getFilesNative(long downloadNative);

    static native int getFileCountNative(long downloadNative);

    static native FileData getFileNative(long downloadNative, int index);

    static native BtMetaInfoData getBtMetaInfoNative(long downloadNative);

    static native String getOptionNative(long downloadHandleNative, String name);

    static native long getOptionsNative(long downloadHandleNative);

    static native void deleteDownloadHandleNative(long downloadHandleNative);

    static native int run(long sessionNative, int runMode);

    static native int addUriNative(long gidNative, List<String> uris, long optionsNative, int position);

    static native int addMetaLinkNative(List<Long> gidsNative, String metaLinkFilePath, long optionsNative, int position);

    static native int addTorrentNative(long gidNative, String torrentFilePath, long optionsNative, int position);

    static native int addTorrentNative(long gidNative, String torrentFilePath, List<String> webSeedUris, long optionsNative, int position);
}
