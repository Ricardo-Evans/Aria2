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
 * Some additional settings except options.
 *
 * @author inCHOK
 * @version Version 1.0
 * @see Option
 */
public class SessionConfig implements Serializable {
    private boolean keepRunning = false;
    private boolean useSignalHandler = true;
    private DownloadCallback downloadCallback = null;

    /**
     * The default constructor.
     * The default value: keepRunning = false, useSignalHandler = true, downloadCallback = null.
     *
     * @see DownloadCallback
     */
    public SessionConfig() {
    }

    /**
     * The clone constructor.
     *
     * @param config The ordinary SessionConfig.
     */
    public SessionConfig(SessionConfig config) {
        this.setKeepRunning(config.isKeepRunning());
        this.setUseSignalHandler(config.isUseSignalHandler());
        this.setDownloadCallback(config.getDownloadCallback());
    }

    /**
     * The constructor which sets all the member variables.
     *
     * @param keepRunning      Whether keep running.
     * @param useSignalHandler Whether use signal handler.
     * @param downloadCallback The download callback.
     * @see DownloadCallback
     */
    public SessionConfig(boolean keepRunning, boolean useSignalHandler, DownloadCallback downloadCallback) {
        this.keepRunning = keepRunning;
        this.useSignalHandler = useSignalHandler;
        this.downloadCallback = downloadCallback;
    }

    /**
     * To get whether keep running.
     *
     * @return Return whether keep running.
     */
    public boolean isKeepRunning() {
        return this.keepRunning;
    }

    /**
     * To set whether keep running.
     *
     * @param keepRunning Whether keep running.
     */
    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = keepRunning;
    }

    /**
     * To get whether use signal handler.
     *
     * @return Return whether use signal handler.
     */
    public boolean isUseSignalHandler() {
        return this.useSignalHandler;
    }

    /**
     * To set whether use signal handler.
     *
     * @param useSignalHandler Whether use signal handler.
     */
    public void setUseSignalHandler(boolean useSignalHandler) {
        this.useSignalHandler = useSignalHandler;
    }

    /**
     * To get the download callback.
     *
     * @return Return the download callback.
     * @see DownloadCallback
     */
    public DownloadCallback getDownloadCallback() {
        return this.downloadCallback;
    }

    /**
     * To set the download callback.
     *
     * @param downloadCallback The download callback.
     * @see DownloadCallback
     */
    public void setDownloadCallback(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    /**
     * To compare whether the two SessionConfigs is equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionConfig)) return false;
        SessionConfig that = (SessionConfig) o;
        return isKeepRunning() == that.isKeepRunning() &&
                isUseSignalHandler() == that.isUseSignalHandler() &&
                Objects.equals(getDownloadCallback(), that.getDownloadCallback());
    }

    /**
     * To get the hash code of the SessionConfig.
     *
     * @return Return the hash code of the SessionConfig.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isKeepRunning(), isUseSignalHandler(), getDownloadCallback());
    }

    /**
     * To convert the SessionConfig into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "SessionConfig{" +
                "keepRunning=" + keepRunning +
                ", useSignalHandler=" + useSignalHandler +
                ", downloadCallback=" + downloadCallback +
                '}';
    }
}
