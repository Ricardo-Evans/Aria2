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

import java.util.Objects;

public class GlobalStat {
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

    public int getDownloadSpeed() {
        return this.downloadSpeed;
    }

    public int getUploadSpeed() {
        return this.uploadSpeed;
    }

    public int getActive() {
        return this.numActive;
    }

    public int getWaiting() {
        return this.numWaiting;
    }

    public int getStopped() {
        return this.numStopped;
    }

    void setDownloadSpeed(int downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    void setUploadSpeed(int uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    void setActive(int numActive) {
        this.numActive = numActive;
    }

    void setWaiting(int numWaiting) {
        this.numWaiting = numWaiting;
    }

    void setStopped(int numStopped) {
        this.numStopped = numStopped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GlobalStat)) return false;
        GlobalStat that = (GlobalStat) o;
        return getDownloadSpeed() == that.getDownloadSpeed() &&
                getUploadSpeed() == that.getUploadSpeed() &&
                numActive == that.numActive &&
                numWaiting == that.numWaiting &&
                numStopped == that.numStopped;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDownloadSpeed(), getUploadSpeed(), numActive, numWaiting, numStopped);
    }

    @Override
    public String toString() {
        return "GlobalStat{" +
                "downloadSpeed=" + downloadSpeed +
                ", uploadSpeed=" + uploadSpeed +
                ", numActive=" + numActive +
                ", numWaiting=" + numWaiting +
                ", numStopped=" + numStopped +
                '}';
    }
}
