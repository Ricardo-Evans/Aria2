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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {
    private long sessionNative;
    private static Session session = null;

    private Session(long sessionNative) {
        this.sessionNative = sessionNative;
    }

    long getSessionNative() {
        return this.sessionNative;
    }

    public static Session newSession(final KeyValues options, SessionConfig config) {
        if (Session.session != null) return Session.session;
        else {
            Session.session = new Session(Aria2.newSessionNative(options.getKeyValuesNative(), config));
            return Session.session;
        }
    }

    public int finalSession() {
        if (Session.session != null && Session.session != this) Session.session = null;
        return Aria2.finalSessionNative(this.sessionNative);
    }

    public int run(RunMode mode) {
        return Aria2.runNative(this.sessionNative, mode.ordinal());
    }

    public int addUri(Gid gid, List<String> uris, KeyValues options) {
        return this.addUri(gid, uris, options, -1);
    }

    public int addUri(Gid gid, List<String> uris, KeyValues options, int position) {
        return Aria2.addUriNative(this.sessionNative, gid.getGidNative(), uris, options.getKeyValuesNative(), position);
    }

    public int addMetaLink(List<Gid> gids, String metaLinkFilePath, KeyValues options) {
        return this.addMetaLink(gids, metaLinkFilePath, options, -1);
    }

    public int addMetaLink(List<Gid> gids, String metaLinkFilePath, KeyValues options, int position) {
        List<Long> gidsNative = new ArrayList<>();
        for (Gid gid : gids) gidsNative.add(gid.getGidNative());
        return Aria2.addMetaLinkNative(this.sessionNative, gidsNative, metaLinkFilePath, options.getKeyValuesNative(), position);
    }

    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, options, -1);
    }

    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options, int position) {
        return Aria2.addTorrentNative(this.sessionNative, gid.getGidNative(), torrentFilePath, options.getKeyValuesNative(), position);
    }

    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, webSeedUris, options, -1);
    }

    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options, int position) {
        return Aria2.addTorrentNative(this.sessionNative, gid.getGidNative(), torrentFilePath, webSeedUris, options.getKeyValuesNative(), position);
    }

    public List<Gid> getActiveDownload() {
        List<Gid> gids = new ArrayList<>();
        for (long gidNative : Aria2.getActiveDownloadNative(this.sessionNative)) gids.add(new Gid(gidNative));
        return gids;
    }

    public int removeDownload(Gid gid) {
        return this.removeDownload(gid, false);
    }

    public int removeDownload(Gid gid, boolean force) {
        return Aria2.removeDownloadNative(this.sessionNative, gid.getGidNative(), force);
    }

    public int pauseDownload(Gid gid) {
        return this.pauseDownload(gid, false);
    }

    public int pauseDownload(Gid gid, boolean force) {
        return Aria2.pauseDownloadNative(this.sessionNative, gid.getGidNative(), force);
    }

    public int unpauseDownload(Gid gid) {
        return Aria2.unpauseDownloadNative(this.sessionNative, gid.getGidNative());
    }

    public int changeOption(Gid gid, KeyValues options) {
        return Aria2.changeOptionNative(this.sessionNative, gid.getGidNative(), options.getKeyValuesNative());
    }

    public String getGlobalOption(String name) {
        return Aria2.getGlobalOptionNative(this.sessionNative, name);
    }

    public KeyValues getGlobalOptions() {
        return new KeyValues(Aria2.getGlobalOptionsNative(this.sessionNative));
    }

    public int changeGlobalOption(KeyValues options) {
        return Aria2.changeGlobalOptionNative(this.sessionNative, options.getKeyValuesNative());
    }

    public GlobalStat getGlobalStat() {
        return Aria2.getGlobalStatNative(this.sessionNative);
    }

    public int changePosition(Gid gid, int pos, OffsetMode mode) {
        return Aria2.changePositionNative(this.sessionNative, gid.getGidNative(), pos, mode.ordinal());
    }

    public int shutdown() {
        return this.shutdown(false);
    }

    public int shutdown(boolean force) {
        return Aria2.shutdownNative(this.sessionNative, force);
    }

    public DownloadHandle getDownloadHandle(Gid gid) {
        return new DownloadHandle(Aria2.getDownloadHandleNative(this.sessionNative, gid.getGidNative()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getSessionNative() == session.getSessionNative();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionNative());
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionNative=" + sessionNative +
                '}';
    }
}
