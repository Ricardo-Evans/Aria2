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
 * The status of downloads.
 * To get the status of a download, use DownloadHandle.getStatus().
 *
 * @author inCHOK
 * @version Version 1.0
 * @see DownloadHandle#getStatus()
 */
public enum DownloadStatus {
    /**
     * Indicating the download is downloading/seeding.
     */
    ACTIVE,
    /**
     * Indicating the download is in the queue, but not started yet.
     */
    WAITING,
    /**
     * Indicating the download is paused.
     */
    PAUSED,
    /**
     * Indicating the download is stopped or completed.
     */
    COMPLETE,
    /**
     * Indicating the download is stopped because of error.
     */
    ERROR,
    /**
     * Indicating the download is removed by user's discretion.
     */
    REMOVED
}
