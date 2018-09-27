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

import java.net.URI;

public class UriData {
    private URI uri;
    private UriStatus status;

    UriData(URI uri,UriStatus status){
        this.uri=uri;
        this.status=status;
    }

    public URI getUri() {
        return this.uri;
    }

    void setUri(URI uri) {
        this.uri = uri;
    }

    public UriStatus getStatus() {
        return this.status;
    }

    void setStatus(UriStatus status) {
        this.status = status;
    }
}
