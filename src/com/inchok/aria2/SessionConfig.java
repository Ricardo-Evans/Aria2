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

public class SessionConfig implements Serializable {
    private boolean keepRunning = false;
    private boolean useSignalHandler = true;
    private DownloadCallback downloadCallback = null;

    public SessionConfig() {
    }

    public SessionConfig(boolean keepRunning, boolean useSignalHandler, DownloadCallback downloadCallback) {
        this.keepRunning = keepRunning;
        this.useSignalHandler = useSignalHandler;
        this.downloadCallback = downloadCallback;
    }

    public boolean isKeepRunning() {
        return this.keepRunning;
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    public boolean isUseSignalHandler() {
        return this.useSignalHandler;
    }

    public void setUseSignalHandler(boolean useSignalHandler) {
        this.useSignalHandler = useSignalHandler;
    }

    public DownloadCallback getDownloadCallback() {
        return this.downloadCallback;
    }

    public void setDownloadCallback(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionConfig)) return false;
        SessionConfig that = (SessionConfig) o;
        return isKeepRunning() == that.isKeepRunning() &&
                isUseSignalHandler() == that.isUseSignalHandler() &&
                Objects.equals(getDownloadCallback(), that.getDownloadCallback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isKeepRunning(), isUseSignalHandler(), getDownloadCallback());
    }

    @Override
    public String toString() {
        return "SessionConfig{" +
                "keepRunning=" + keepRunning +
                ", useSignalHandler=" + useSignalHandler +
                ", downloadCallback=" + downloadCallback +
                '}';
    }
}
