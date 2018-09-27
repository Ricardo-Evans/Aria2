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

import java.util.List;

public class FileData {
    private int index;
    private String path;
    private long length;
    private long completedLength;
    private boolean selected;
    private List<UriData> uris;

    FileData(int index, String path, long length, long completedLength, boolean selected, List<UriData> uris) {
        this.index = index;
        this.path = path;
        this.length = length;
        this.completedLength = completedLength;
        this.selected = selected;
        this.uris = uris;
    }

    public int getIndex() {
        return this.index;
    }

    public String getPath() {
        return this.path;
    }

    public long getLength() {
        return this.length;
    }

    public long getCompletedLength() {
        return this.completedLength;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public List<UriData> getUris() {
        return this.uris;
    }
}
