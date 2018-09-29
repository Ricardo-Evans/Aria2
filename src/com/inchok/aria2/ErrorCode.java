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

public class ErrorCode {
    public static final int UNKNOWN = 1;
    public static final int TIME_OUT = 2;
    public static final int RESOURCE_NOT_FOUND = 3;
    public static final int MAX_RESOURCE_NOT_FOUND = 4;
    public static final int DOWNLOAD_SPEED_TOO_SLOW = 5;
    public static final int NETWORK_ERROR = 6;
    public static final int KILL_BY_SIGNAL = 7;
    public static final int RESUME_NOT_SUPPORTED = 8;
    public static final int DISK_FULL = 9;
    public static final int PIECE_LENGTH_CHANGED = 10;
    public static final int DOWNLOADING_SAME_FILE = 11;
    public static final int DOWNLOADING_SAME_TORRENT = 12;
    public static final int FILE_EXISTS = 13;
    public static final int FILE_RENAME_ERROR = 14;
    public static final int OPEN_EXISTING_FILE_ERROR = 15;
    public static final int CREATE_OR_TRUNCATE_FILE_ERROR = 16;
    public static final int FILE_IO_ERROR = 17;
    public static final int CREATE_DIRECTORY_ERROR = 18;
    public static final int NAME_RESOLUTION_ERROR = 19;
    public static final int METALINK_PARSE_ERROR = 20;
    public static final int FTP_COMMAND_ERROR = 21;
    public static final int HTTP_RESPONSE_HEADER_ERROR = 22;
    public static final int TOO_MANY_REDIRECTS = 23;
    public static final int HTTP_AUTHORIZE_ERROR = 24;
    public static final int BENCODE_PARSE_ERROR = 25;
    public static final int BAD_TORRENT = 26;
    public static final int BAD_MAGNET_URI = 27;
    public static final int BAD_OPTION = 28;
    public static final int REMOTE_REFUSED = 29;
    public static final int PAUSE_RPC_ERROR = 30;
    public static final int RESERVED = 31;
    public static final int CHECKSUM_UNMATCH = 32;
}
