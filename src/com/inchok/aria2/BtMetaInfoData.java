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

import java.util.Date;
import java.util.List;

public class BtMetaInfoData {
    private List<List<String>> announceList;
    private String comment;
    private Date creationDate;
    private BtFileMode mode;
    private String name;

    BtMetaInfoData(List<List<String>> announceList, String comment, Date creationDate, BtFileMode mode, String name) {
        this.announceList = announceList;
        this.comment = comment;
        this.creationDate = creationDate;
        this.mode = mode;
        this.name = name;
    }

    public List<List<String>> getAnnounceList() {
        return this.announceList;
    }

    public String getComment() {
        return this.comment;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public BtFileMode getMode() {
        return this.mode;
    }

    public String getName() {
        return this.name;
    }

}