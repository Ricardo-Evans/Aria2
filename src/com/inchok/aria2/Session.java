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

public class Session {
    private long sessionNative;
    private static Session session = null;

    Session(long sessionNative) {
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
}
