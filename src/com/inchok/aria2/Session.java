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

import java.util.ArrayList;
import java.util.List;

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
            Session.session = new Session(Aria2.newSessionNative(options.getKeyValuesNative(), config.getSessionConfigNative()));
            return Session.session;
        }
    }

    public int finalSession() {
        if (Session.session != null && Session.session != this) Session.session = null;
        return Aria2.finalSessionNative(this.sessionNative);
    }

    public int run(RunMode mode) {
        return Aria2.run(this.sessionNative, mode.ordinal());
    }

    public int addUri(Gid gid, List<String> uris, KeyValues options) {
        return this.addUri(gid, uris, options, -1);
    }

    public int addUri(Gid gid, List<String> uris, KeyValues options, int position) {
        return Aria2.addUriNative(gid.getGidNative(), uris, options.getKeyValuesNative(), position);
    }

    public int addMetaLink(List<Gid> gids, String metaLinkFilePath, KeyValues options) {
        return this.addMetaLink(gids, metaLinkFilePath, options, -1);
    }

    public int addMetaLink(List<Gid> gids, String metaLinkFilePath, KeyValues options, int position) {
        List<Long> gidsNative = new ArrayList<>();
        for (Gid gid : gids) gidsNative.add(gid.getGidNative());
        return Aria2.addMetaLinkNative(gidsNative, metaLinkFilePath, options.getKeyValuesNative(), position);
    }

    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, options, -1);
    }

    public int addTorrent(Gid gid, String torrentFilePath, KeyValues options, int position) {
        return Aria2.addTorrentNative(gid.getGidNative(), torrentFilePath, options.getKeyValuesNative(), position);
    }

    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options) {
        return this.addTorrent(gid, torrentFilePath, webSeedUris, options, -1);
    }

    public int addTorrent(Gid gid, String torrentFilePath, List<String> webSeedUris, KeyValues options, int position) {
        return Aria2.addTorrentNative(gid.getGidNative(), torrentFilePath, webSeedUris, options.getKeyValuesNative(), position);
    }

}
