
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

/**
 * The type of the download event used in DownloadCallback.onDownloadEvent(Session, DownloadEvent, Gid).
 *
 * @author inCHOK
 * @version Version 1.0
 * @see DownloadCallback#onDownloadEvent(Session, DownloadEvent, Gid)
 */
public enum DownloadEvent {
    /**
     * A download has started.
     */
    ON_DOWNLOAD_START,
    /**
     * A download has paused.
     */
    ON_DOWNLOAD_PAUSE,
    /**
     * A download has stopped.
     */
    ON_DOWNLOAD_STOP,
    /**
     * A download has completed.
     */
    ON_DOWNLOAD_COMPLETE,
    /**
     * A download has met some error.
     */
    ON_DOWNLOAD_ERROR,
    /**
     * A bt download has completed.(But may still continue to seeding.)
     */
    ON_BT_DOWNLOAD_COMPLETE
}
