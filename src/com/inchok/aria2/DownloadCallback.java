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
 * The download event listener of Aria2.
 * To set the download event listener, use SessionConfig.setDownloadEvent(DownloadCallback).
 *
 * @author inCHOK
 * @version Version 1.0
 * @see SessionConfig#SessionConfig(boolean, boolean, DownloadCallback)
 * @see SessionConfig#setDownloadCallback(DownloadCallback)
 */
public interface DownloadCallback {
    /**
     * This callback method is invoked when a download event occurs.
     * Implement this method to handle the download event.
     *
     * @param session The Aria2 session where the download event occurs.
     * @param event   The kind of download event.
     * @param gid     The gid refers to the download which this event is fired on.
     * @return The return value is ignored by now, but to keep compatibility, the implement of this method should return Aria2.RESPONSE_OK if succeed.
     * @see Session
     * @see DownloadEvent
     * @see Gid
     * @see Aria2#RESPONSE_OK
     */
    int onDownloadEvent(Session session, DownloadEvent event, Gid gid);
}
