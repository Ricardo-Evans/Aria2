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

/**
 * The static class which operates the global operations.
 * Before you do anything with Aria2, you must call Aria2.initialize() first to initialize. After you finish your task, you need to call Aria2.deInitialize() to release the static global resources.
 *
 * @author inCHOK
 * @version Version 1.0
 * @see Aria2#initialize()
 * @see Aria2#deInitialize()
 */
public class Aria2 {
    static {
        System.loadLibrary("aria2-native");
    }

    /**
     * The response code when any operation succeed.
     */
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_REMAINING = 1;

    private Aria2() {
    }

    /**
     * Initialize the global data.
     * Call this static method <strong>only once</strong> before any other operations of Aria2.
     *
     * @return Return Aria2.RESPONSE_OK if succeed, or any other negative error code.
     * @see Aria2#RESPONSE_OK
     */
    public static int initialize() {
        return Aria2.initializeNative();
    }

    /**
     * Release all the global data.
     * Call this static method <strong>only once</strong> after all the tasks of Aria2 are finished.
     *
     * @return Return Aria2.RESPONSE_OK if succeed, or any other negative error code.
     * @see Aria2#RESPONSE_OK
     */
    public static int deInitialize() {
        return Aria2.deInitializeNative();
    }

    private static native int initializeNative();

    private static native int deInitializeNative();

    static native long newSessionNative(long keyValuesNative, SessionConfig sessionConfig);

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

    static native DownloadStatus getStatusNative(long downloadHandleNative);

    static native long getTotalLengthNative(long downloadHandleNative);

    static native long getCompletedLengthNative(long downloadHandleNative);

    static native long getUploadedLengthNative(long downloadHandleNative);

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

    static native List<FileData> getFilesDataNative(long downloadNative);

    static native int getFileCountNative(long downloadNative);

    static native FileData getFileNative(long downloadNative, int index);

    static native BtMetaInfoData getBtMetaInfoNative(long downloadNative);

    static native String getOptionNative(long downloadHandleNative, String name);

    static native long getOptionsNative(long downloadHandleNative);

    static native void deleteDownloadHandleNative(long downloadHandleNative);

    static native int runNative(long sessionNative, int runMode);

    static native int addUriNative(long sessionNative, long gidNative, List<String> uris, long optionsNative, int position);

    static native int addMetaLinkNative(long sessionNative, List<Long> gidsNative, String metaLinkFilePath, long optionsNative, int position);

    static native int addTorrentNative(long sessionNative, long gidNative, String torrentFilePath, long optionsNative, int position);

    static native int addTorrentNative(long sessionNative, long gidNative, String torrentFilePath, List<String> webSeedUris, long optionsNative, int position);

    static native List<Long> getActiveDownloadNative(long sessionNative);

    static native int removeDownloadNative(long sessionNative, long gidNative, boolean force);

    static native int pauseDownloadNative(long sessionNative, long gidNative, boolean force);

    static native int unpauseDownloadNative(long sessionNative, long gidNative);

    static native int changeOptionNative(long sessionNative, long gidNative, long optionsNative);

    static native String getGlobalOptionNative(long sessionNative, String name);

    static native long getGlobalOptionsNative(long sessionNative);

    static native int changeGlobalOptionNative(long sessionNative, long optionsNative);

    static native GlobalStat getGlobalStatNative(long sessionNative);

    static native int changePositionNative(long sessionNative, long gidNative, int pos, int mode);

    static native int shutdownNative(long sessionNative, boolean force);

    static native long getDownloadHandleNative(long sessionNative, long gidNative);
}
