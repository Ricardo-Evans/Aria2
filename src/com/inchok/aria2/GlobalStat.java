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
 * The global statistics of Aria2.
 * To get this stat, use Session.getGlobalStat().
 *
 * @author inCHOK
 * @version Version 1.0
 * @see Session#getGlobalStat()
 */
public class GlobalStat implements Serializable {
    private int downloadSpeed;
    private int uploadSpeed;
    private int numActive;
    private int numWaiting;
    private int numStopped;

    GlobalStat(int downloadSpeed, int uploadSpeed, int numActive, int numWaiting, int numStopped) {
        this.downloadSpeed = downloadSpeed;
        this.uploadSpeed = uploadSpeed;
        this.numActive = numActive;
        this.numWaiting = numWaiting;
        this.numStopped = numStopped;
    }

    /**
     * To get the global download speed in bytes/sec.
     *
     * @return Return the global download speed in bytes/sec.
     */
    public int getDownloadSpeed() {
        return this.downloadSpeed;
    }

    /**
     * To get the global upload speed in bytes/sec.
     *
     * @return Return the global upload speed in bytes/sec.
     */
    public int getUploadSpeed() {
        return this.uploadSpeed;
    }

    /**
     * To get the count of active downloads.
     *
     * @return Return the count of active downloads.
     */
    public int getActive() {
        return this.numActive;
    }

    /**
     * To get the count of waiting downloads.
     *
     * @return Return the count of waiting downloads.
     */
    public int getWaiting() {
        return this.numWaiting;
    }

    /**
     * To get the count of stopped downloads.
     *
     * @return Return the count of stopped downloads.
     */
    public int getStopped() {
        return this.numStopped;
    }

    /**
     * To compare whether the two GlobalStat is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GlobalStat)) return false;
        GlobalStat that = (GlobalStat) o;
        return getDownloadSpeed() == that.getDownloadSpeed() &&
                getUploadSpeed() == that.getUploadSpeed() &&
                getActive() == that.getActive() &&
                getWaiting() == that.getWaiting() &&
                getStopped() == that.getStopped();
    }

    /**
     * To get the hash code of the GlobalStat.
     *
     * @return Return the hash code of the GlobalStat.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDownloadSpeed(), getUploadSpeed(), getActive(), getWaiting(), getStopped());
    }

    /**
     * To convert the GlobalStat into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "GlobalStat{" +
                "downloadSpeed=" + getDownloadSpeed() +
                ", uploadSpeed=" + getUploadSpeed() +
                ", numActive=" + getActive() +
                ", numWaiting=" + getWaiting() +
                ", numStopped=" + getStopped() +
                '}';
    }
}
