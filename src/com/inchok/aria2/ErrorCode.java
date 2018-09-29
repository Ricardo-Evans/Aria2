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

/**
 * Error code constants.
 * <p>These error codes are returned when you call DownloadHandle.getErrorCode().</p>
 * <p>All of these error codes are <strong>positive</strong>, some negative error codes returned by such as Aria2.initialize() <strong>have no connection</strong> with these error codes.</p>
 *
 * @author inCHOK
 * @version Version 1.0
 * @see DownloadHandle#getErrorCode()
 */
public class ErrorCode {
    /**
     * If an unknown error occurred.
     */
    public static final int UNKNOWN = 1;
    /**
     * If time out occurred.
     */
    public static final int TIME_OUT = 2;
    /**
     * If a resource was not found.
     */
    public static final int RESOURCE_NOT_FOUND = 3;
    //TODO Option.MAX_FILE_NOT_FOUND
    /**
     * If aria2 saw the specified number of "resource not found" error.
     *
     * @see Option
     */
    public static final int MAX_RESOURCE_NOT_FOUND = 4;
    //TODO Option.LOWEST_SPEED_LIMIT
    /**
     * If a download aborted because download speed was too slow.
     *
     * @see Option
     */
    public static final int DOWNLOAD_SPEED_TOO_SLOW = 5;
    /**
     * If network problem occurred.
     */
    public static final int NETWORK_ERROR = 6;
    /**
     * If there were unfinished downloads. This error is only reported if all finished downloads were successful and there were unfinished downloads in a queue when aria2 exited by pressing <cite>Ctrl-C</cite> by an user or sending <cite>TERM</cite> or <cite>INT</cite> signal.
     */
    public static final int KILL_BY_SIGNAL = 7;
    /**
     * If remote server did not support resume when resume was required to complete download.
     */
    public static final int RESUME_NOT_SUPPORTED = 8;
    /**
     * If there was not enough disk space available.
     */
    public static final int DISK_FULL = 9;
    //TODO Option.ALLOW_PIECE_LENGTH_CHANGE
    /**
     * If piece length was different from one in .aria2 control file.
     *
     * @see Option
     */
    public static final int PIECE_LENGTH_CHANGED = 10;
    /**
     * If aria2 was downloading same file at that moment.
     */
    public static final int DOWNLOADING_SAME_FILE = 11;
    /**
     * If aria2 was downloading same info hash torrent at that moment.
     */
    public static final int DOWNLOADING_SAME_TORRENT = 12;
    //TODO Option.ALLOW_OVERWRITING
    /**
     * If file already existed.
     *
     * @see Option
     */
    public static final int FILE_EXISTS = 13;
    //TODO Option.AUTO_FILE_RENAMING
    /**
     * If renaming file failed.
     *
     * @see Option
     */
    public static final int FILE_RENAME_ERROR = 14;
    /**
     * If aria2 could not open existing file.
     */
    public static final int OPEN_EXISTING_FILE_ERROR = 15;
    /**
     * If aria2 could not create new file or truncate existing file.
     */
    public static final int CREATE_OR_TRUNCATE_FILE_ERROR = 16;
    /**
     * If file I/O error occurred.
     */
    public static final int FILE_IO_ERROR = 17;
    /**
     * If aria2 could not create directory.
     */
    public static final int CREATE_DIRECTORY_ERROR = 18;
    /**
     * If name resolution failed.
     */
    public static final int NAME_RESOLUTION_ERROR = 19;
    /**
     * If aria2 could not parse Metalink document.
     */
    public static final int METALINK_PARSE_ERROR = 20;
    /**
     * If FTP command failed.
     */
    public static final int FTP_COMMAND_ERROR = 21;
    /**
     * If HTTP response header was bad or unexpected.
     */
    public static final int HTTP_RESPONSE_HEADER_ERROR = 22;
    /**
     * If too many redirects occurred.
     */
    public static final int TOO_MANY_REDIRECTS = 23;
    /**
     * If HTTP authorization failed.
     */
    public static final int HTTP_AUTHORIZE_ERROR = 24;
    /**
     * If aria2 could not parse bencoded file (usually ".torrent" file).
     */
    public static final int BENCODE_PARSE_ERROR = 25;
    /**
     * If ".torrent" file was corrupted or missing information that aria2 needed.
     */
    public static final int BAD_TORRENT = 26;
    /**
     * If Magnet URI was bad.
     */
    public static final int BAD_MAGNET_URI = 27;
    /**
     * If bad/unrecognized option was given or unexpected option argument was given.
     */
    public static final int BAD_OPTION = 28;
    /**
     * If the remote server was unable to handle the request due to a temporary overloading or maintenance.
     */
    public static final int REMOTE_REFUSED = 29;
    /**
     * If aria2 could not parse JSON-RPC request.
     */
    public static final int PAUSE_RPC_ERROR = 30;
    /**
     * Reserved. Not used.
     */
    public static final int RESERVED = 31;
    /**
     * If checksum validation failed.
     */
    public static final int CHECKSUM_UNMATCH = 32;
}
