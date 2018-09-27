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

public class Gid {
    private long gidNative;

    Gid(long gidNative){
        this.gidNative=gidNative;
    }

    public static Gid newGid(long gid){
        return new Gid(Aria2.newGidNative(gid));
    }

    public String toHex(){
        return Aria2.gidToHexNative(this.gidNative);
    }

    public static Gid toGid(String hexGid){
        return new Gid(Aria2.hexToGidNative(hexGid));
    }

    public boolean isNull(){
        return Aria2.isNullNative(this.gidNative);
    }

    long getGidNative() {
        return this.gidNative;
    }

    void setGidNative(long gidNative) {
        this.gidNative = gidNative;
    }

    public long getGid(){
        return Aria2.getGidNative(this.gidNative);
    }

    public void setGid(long gid){
        Aria2.setGidNative(this.gidNative,gid);
    }

}
