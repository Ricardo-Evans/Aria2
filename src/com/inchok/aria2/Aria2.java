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

public class Aria2 {
    static {
        System.loadLibrary("aria2-native");
    }
    private static Session session=null;
    public static int INIT_OK=0;

    public static int initialize(){
        return Aria2.initializeNative();
    }

    public static int deInitialize(){
        return Aria2.deInitializeNative();
    }

    public static Session sessionNew(final KeyValues options,SessionConfig config){
        if (Aria2.session!=null) return Aria2.session;
        else {
            Aria2.session=new Session(sessionNewNative(options.getKeyValuesNative(),config.getSessionConfigNative()));
            return Aria2.session;
        }
    }

    public int sessionFinal(Session session){
        if (Aria2.session==session) Aria2.session=null;
        return Aria2.sessionFinalNative(session.getSessionNative());
    }

    private static native int initializeNative();
    private static native int deInitializeNative();
    private static native long sessionNewNative(long keyValuesNative,long sessionConfigNative);
    private static native int sessionFinalNative(long sessionNative);
    static native long hexToGidNative(String hexGid);
    static native String gidToHexNative(long gidNative);
    static native boolean isNullNative(long gidNative);
    static native long getGidNative(long gidNative);
    static native void setGidNative(long gidNative,long gid);
    static native long newGidNative(long gid);
}
