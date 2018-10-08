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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The session of Aria2, you need this object through out the download process.
 * <p>To create a new session, use Session.newSession(KeyValues, SessionConfig), and when all the tasks you want Aria2 to do are finished, don't forget to call Session.finalSession() to release all the resources.</p>
 * <p>Notice that it's suggested to create <strong>only one</strong> Session per process due to the heavy use of static resources. If there is already a Session(Exists after you call Session.newSession(KeyValues, SessionConfig) and before the next call of Session.finalSession().), it will return it without change the global options and SessionConfig.</p>
 * <p>Session is not safe for concurrent accesses from multiple threads, </p>
 *
 * @author inCHOK
 * @version Version 1.0
 * @see Session#newSession(KeyValues, SessionConfig)
 * @see Session#finalSession()
 */
public class Session {
    private long sessionNative;
    private static Session session;

    private Session(long sessionNative) {
        this.sessionNative = sessionNative;
    }

    long getSessionNative() {
        return this.sessionNative;
    }

    /**
     * The static method to create a new Session.
     * Don't forget to call Session.finalSession() after all the tasks of Aria2 are done. There will be no more than one Session exists per process.
     *
     * @param options The global options of this new Session.
     * @param config  The SessionConfig of this new Session.
     * @return Return the new Session.
     * @see SessionConfig
     * @see Session#finalSession()
     */
    public static Session newSession(final KeyValues options, SessionConfig config) {
        if (Session.session == null)
            Session.session = new Session(Aria2.newSessionNative(options, config));
        return Session.session;
    }

    /**
     * Destroy a Session and saving the configurations.
     * This method should be called after all the tasks of Aria2 are done, and you should guarantee this.
     *
     * @return Return Aria2.RESPONSE_OK if no error occurs or the last error code.
     * @see Aria2#RESPONSE_OK
     * @see ErrorCode
     */
    public int finalSession() {
        if (Session.session != null) Session.session = null;
        return Aria2.finalSessionNative(this.sessionNative);
    }

    /**
     * Performs event polling and actions for them.
     * <p>If the mode is DEFAULT , this function returns when no downloads are left to be processed. In this case, this function returns Aria2.RESPONSE_OK.</p>
     * <p>If the mode is ONCE , this function returns after one event polling. In the current implementation, event polling timeouts in 1 second. This function also returns on each timeout. On return, when no downloads are left to be processed, this function returns Aria2.RESPONSE_OK. Otherwise, returns Aria2.RESPONSE_REMAINING, indicating that the caller must call this function one or more time to complete downloads.</p>
     * <p>In either case, this method will return negative error code on error.</p>
     *
     * @param mode The run mode.
     * @return Return Aria2.RESPONSE_OK if no download is left, otherwise return Aria2.RESPONSE_REMAINING. Return negative error code on error.
     * @see RunMode
     * @see Aria2#RESPONSE_OK
     * @see Aria2#RESPONSE_REMAINING
     */
    public int run(RunMode mode) {
        return Aria2.runNative(this.sessionNative, mode.ordinal());
    }

    /**
     * Same to Session.addUri(Gid, List, KeyValues, int) with the position set to -1.
     *
     * @see Session#addUri(Gid, List, KeyValues, int)
     */
    public int addUri(Gid gid, List<String> uris, KeyValues options) {
        return this.addUri(gid, uris, options, -1);
    }

    /**
     * Adds new HTTP(S)/FTP/BitTorrent Magnet URI.
     * On successful return, if the gid is not null, the Gid of added download will be assigned to the gid. The uris includes URI to be downloaded. For Bit Torrent Magnet URI, the uris must have only one element and it should be Bit Torrent Magnet URI. URIs in the uris must point to the same file. If you mix other URls which point to another file, Aria2 does not complain but download may fail. The options is an array of a pair of option name and value. If unknown options are included in options, they are simply ignored. If the position is not negative integer, the new download is inserted at position in the waiting queue. If the position is negative or the position is larger than the size of the queue, it is appended at the end of the queue.
     *
     * @param gid      The gid you want to assign to the new download, use null if you want to use auto-generated gid.
     * @param uris     The uris point to the download target.
     * @param options  The options of this download.
     * @param position The position you want to insert the new download.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see Aria2#RESPONSE_OK
     */
    public int addUri(@Nullable Gid gid, List<String> uris, KeyValues options, int position) {
        return Aria2.addUriNative(this.sessionNative, gid == null ? -1 : gid.getGid(), uris, options, position);
    }

    /**
     * The same as Session.addMetaLink(List, String, KeyValues, int) with the position set to -1.
     *
     * @see Session#addMetaLink(List, String, KeyValues, int)
     */
    public int addMetaLink(List<Gid> gids, String metalinkFilePath, KeyValues options) {
        return this.addMetaLink(gids, metalinkFilePath, options, -1);
    }

    /**
     * Adds Metalink download.
     * The path to Metalink file is specified by the metalinkFilePath. On successful return, if the gids is not null, the Gids of added downloads are appended to the gids. The options is an array of a pair of option name and value. If unknown options are included in options, they are simply ignored. If the position is not negative integer, the new download is inserted at position in the waiting queue. If the position is negative or the position is larger than the size of the queue, it is appended at the end of the queue.
     *
     * @param gids             The Gid list you want the new Gids to append to.
     * @param metalinkFilePath The metalink file path to be downloaded.
     * @param options          The options of this download.
     * @param position         The position you want to insert the new download.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see Aria2#RESPONSE_OK
     */
    public int addMetaLink(@NotNull List<Gid> gids, String metalinkFilePath, KeyValues options, int position) {
        List<Long> gidsNative = new ArrayList<>();
        for (Gid gid : gids) gidsNative.add(gid.getGid());
        return Aria2.addMetaLinkNative(this.sessionNative, gidsNative, metalinkFilePath, options, position);
    }

    /**
     * The same as Session.addTorrent(Gid, String, KeyValues, int) with the position set to -1.
     *
     * @see Session#addTorrent(Gid, String, KeyValues, int)
     */
    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, options, -1);
    }

    /**
     * The same as Session.addTorrent(Gid, String, List, KeyValues, int) for no web seed need.
     *
     * @see Session#addTorrent(Gid, String, List, KeyValues, int)
     */
    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options, int position) {
        return Aria2.addTorrentNative(this.sessionNative, gid == null ? -1 : gid.getGid(), torrentFilePath, options, position);
    }

    /**
     * The same as Session.addTorrent(Gid, String, List, KeyValues, int) with the position set to -1.
     *
     * @see Session#addTorrent(Gid, String, List, KeyValues, int)
     */
    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, webSeedUris, options, -1);
    }

    /**
     * Adds Bit Torrent download.
     * On successful return, if the gid is not null, the Gid of added download will be assigned to the gid. The path to ".torrent" file is specified by the torrentFilePath. Bit Torrent Magnet URI cannot be used with this function. Use Session.addUri(Gid, List, KeyValues, int) instead. The webSeedUris contains URIs used for web-seeding. For single file torrents, URI can be a complete URI pointing to the resource or if URI ends with '/', name in torrent file is added. For multi-file torrents, name and path in torrent are added to form a URI for each file. The options is an array of a pair of option name and value. If unknown options are included in options, they are simply ignored. If the position is not negative integer, the new download is inserted at position in the waiting queue. If the position is negative or the position is larger than the size of the queue, it is appended at the end of the queue.
     *
     * @param gid             The gid you want to assign to the new download, use null if you want to use auto-generated gid.
     * @param torrentFilePath The path of the torrent file to be downloaded.
     * @param webSeedUris     The web seed uris.
     * @param options         The options of this download.
     * @param position        The position you want to insert the new download.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see Session#addUri(Gid, List, KeyValues, int)
     * @see Aria2#RESPONSE_OK
     */
    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options, int position) {
        return Aria2.addTorrentNative(this.sessionNative, gid == null ? -1 : gid.getGid(), torrentFilePath, webSeedUris, options, position);
    }

    /**
     * To get the active downloads.
     *
     * @return Return the list of gids of the active downloads.
     * @see Gid
     */
    public List<Gid> getActiveDownload() {
        List<Gid> gids = new ArrayList<>();
        for (long gidNative : Aria2.getActiveDownloadNative(this.sessionNative)) gids.add(new Gid(gidNative));
        return gids;
    }

    /**
     * The same as Session.removeDownload(Gid, boolean) with the force set to false.
     *
     * @see Session#removeDownload(Gid, boolean)
     */
    public int removeDownload(Gid gid) {
        return this.removeDownload(gid, false);
    }

    /**
     * Remove the specific download.
     * If the specified download is in progress, it is stopped at first. The status of the removed download becomes DownloadStatus.REMOVED. If the force is true, removal will take place without any action which takes time such as contacting Bit Torrent tracker.
     *
     * @param gid   The gid of the download you want to remove.
     * @param force Whether you want to force to remove.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see DownloadStatus
     * @see Aria2#RESPONSE_OK
     */
    public int removeDownload(Gid gid, boolean force) {
        return Aria2.removeDownloadNative(this.sessionNative, gid.getGid(), force);
    }

    /**
     * The same as Session.pauseDownload(Gid, boolean) with the force set to false.
     *
     * @see Session#pauseDownload(Gid, boolean)
     */
    public int pauseDownload(Gid gid) {
        return this.pauseDownload(gid, false);
    }

    /**
     * Pauses the specific download.
     * <p>The status of paused download becomes DownloadStatus.PAUSED. If the download is active, the download is placed on the first position of waiting queue. As long as the status is DownloadStatus.PAUSED, the download will not start. To change status to DownloadStatus.WAITING, use Session.unpauseDownload(Gid). If the force is true, pause will take place without any action which takes time such as contacting Bit Torrent tracker.</p>
     * <p>Please note that, to make pause work, the application must set option keepRunning of SessionConfig to true. Otherwise, the behavior is undefined.</p>
     *
     * @param gid   The gid of the download you want to pause.
     * @param force Whether you want to force to pause.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see Session#unpauseDownload(Gid)
     * @see SessionConfig#setKeepRunning(boolean)
     * @see DownloadStatus
     * @see Aria2#RESPONSE_OK
     */
    public int pauseDownload(Gid gid, boolean force) {
        return Aria2.pauseDownloadNative(this.sessionNative, gid.getGid(), force);
    }

    /**
     * Unpause the specific download.
     * Changes the status of the download denoted by the gid from DownloadStatus.PAUSED to DownloadStatus.WAITING. This makes the download eligible to restart.
     *
     * @param gid The gid of the download you want to unpause.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Gid
     * @see DownloadStatus
     * @see Aria2#RESPONSE_OK
     */
    public int unpauseDownload(Gid gid) {
        return Aria2.unpauseDownloadNative(this.sessionNative, gid.getGid());
    }

    /**
     * Apply options in the options to the download denoted by the gid dynamically.
     * <p>The following options can be changed for downloads in DownloadStatus.Active status: <cite>bt-max-peers</cite>, <cite>bt-request-peer-speed-limit</cite>, <cite>bt-remove-unselected-file</cite>, <cite>force-save</cite>, <cite>max-download-limit</cite> and <cite>max-upload-limit</cite>.</p>
     * <p>For downloads in DownloadStatus.WAITING or DownloadStatus.PAUSED status, in addition to the above options, options listed in <a href="https://aria2.github.io/manual/en/html/aria2c.html#input-file">Input File</a> subsection are available, except for following options: <cite>dry-run</cite>, <cite>metalink-base-uri</cite>, <cite>parameterized-uri</cite>, <cite>pause</cite>, <cite>piece-length</cite> and <cite>rpc-save-upload-metadata</cite> option.</p>
     * <p>For the options which are not applicable or unknown, they are just ignored.</p>
     *
     * @param gid     The gid of the download which you want to change options.
     * @param options The new options.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Option
     * @see DownloadStatus
     * @see Gid
     * @see Aria2#RESPONSE_OK
     */
    public int changeOption(Gid gid, KeyValues options) {
        return Aria2.changeOptionNative(this.sessionNative, gid.getGid(), options);
    }

    /**
     * To get global option denoted by the name.
     *
     * @param name The name of the global option.
     * @return Return the value of the global option, or empty string if such a option does not exist.
     * @see Option
     */
    public String getGlobalOption(String name) {
        return Aria2.getGlobalOptionNative(this.sessionNative, name);
    }

    /**
     * To get the global options.
     * Note that this method does not return options which have no default value and have not been set by Session.newSession(KeyValues, SessionConfig), configuration files or API functions.
     *
     * @return Return the global options.
     * @see Session#newSession(KeyValues, SessionConfig)
     * @see Option
     */
    public KeyValues getGlobalOptions() {
        return Aria2.getGlobalOptionsNative(this.sessionNative);
    }

    /**
     * Apply global options in the options dynamically.
     * <p>The following options are available: <cite>download-result</cite>, <cite>log</cite>, <cite>log-level</cite>, <cite>max-concurrent-downloads</cite>, <cite>max-download-result</cite>, <cite>max-overall-download-limit</cite>, <cite>max-overall-upload-limit</cite>, <cite>save-cookies</cite>, <cite>save-session</cite> and <cite>server-stat-of</cite>.</p>
     * <p>In addition to them, options listed in <a href="https://aria2.github.io/manual/en/html/aria2c.html#input-file">Input File</a> subsection are available, except for following options: <cite>checksum</cite>, <cite>index-out</cite>, <cite>out</cite>, <cite>pause</cite> and <cite>select-file</cite>.</p>
     * <p>For the options which are not applicable or unknown, they are just ignored.</p>
     *
     * @param options The new global options.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Option
     * @see Aria2#RESPONSE_OK
     */
    public int changeGlobalOption(KeyValues options) {
        return Aria2.changeGlobalOptionNative(this.sessionNative, options);
    }

    /**
     * To get the global statistics.
     *
     * @return Return the global statistics.
     * @see GlobalStat
     */
    public GlobalStat getGlobalStat() {
        return Aria2.getGlobalStatNative(this.sessionNative);
    }

    /**
     * Changes the position of the download denoted by the gid.
     * <p>If it is in DownloadStatus.WAITING or DownloadStatus.PAUSED state. If the mode is OffsetMode.SET, it moves the download to a position pos relative to the beginning of the queue. If the mode is OffsetMode.CUR, it moves the download to a position pos relative to the current position. If the mode is OffsetMode.END, it moves the download to a position pos relative to the end of the queue. If the destination position is less than 0 or beyond the end of the queue, it moves the download to the beginning or the end of the queue respectively.</p>
     * <p>For example, if the download having GID gid is placed in position 3, changePosition(gid, -1, OffsetMode.CUR) will change its position to 2. Additional call changePosition(gid, 0, OffsetMode.CUR) will change its position to 0 (the beginning of the queue).</p>
     *
     * @param gid  The gid of thee download you want to change position.
     * @param pos  The destination position.
     * @param mode The offset mode.
     * @return Return the final destination position if succeed, or negative error code.
     * @see Gid
     * @see OffsetMode
     */
    public int changePosition(Gid gid, int pos, OffsetMode mode) {
        return Aria2.changePositionNative(this.sessionNative, gid.getGid(), pos, mode.ordinal());
    }

    /**
     * The same as Session#shutdown(boolean) with the force set to false.
     *
     * @see Session#shutdown(boolean)
     */
    public int shutdown() {
        return this.shutdown(false);
    }

    /**
     * Schedules shutdown.
     * If the force is true, shutdown will take place without any action which takes time such as contacting BitTorrent tracker. After this call, the application must keep calling run() function until it returns Aria2.RESPONSE_OK.
     *
     * @param force Whether force to shutdown.
     * @return Return Aria2.RESPONSE_OK if succeed, or negative error code.
     * @see Aria2#RESPONSE_OK
     */
    public int shutdown(boolean force) {
        return Aria2.shutdownNative(this.sessionNative, force);
    }

    /**
     * To get the handle for the download denoted by the gid.
     * The caller can retrieve various information of the download via returned handle's member methods. The lifetime of the returned handle is before the next call of Session.run(RunMode) or Session.finalSession(). The caller must call DownloadHandle.delete() before that. This function returns null if no download denoted by the gid is present. It is the responsibility of the caller to call DownloadHandle.delete() to delete handle object.
     *
     * @param gid The gid of the download you want to get the download handle.
     * @return Return the handle for the download denoted by the gid.
     * @see DownloadHandle
     * @see Session#run(RunMode)
     * @see Session#finalSession()
     * @see DownloadHandle#delete()
     */
    public DownloadHandle getDownloadHandle(Gid gid) {
        return new DownloadHandle(Aria2.getDownloadHandleNative(this.sessionNative, gid.getGid()));
    }

    /**
     * To compare whether the two Sessions are equal.
     *
     * @param o The object to be compared with.
     * @return Return true if the two objects are equal, otherwise return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getSessionNative() == session.getSessionNative();
    }

    /**
     * To get the hash code of the Session.
     *
     * @return Return the hash code of the Session.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSessionNative());
    }

    /**
     * To convert the Session into a String.
     *
     * @return Return the String result.
     */
    @Override
    public String toString() {
        return "Session{" +
                "sessionNative=" + sessionNative +
                '}';
    }
}
