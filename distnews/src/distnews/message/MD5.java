/*
 *	Copyright 2003, 2004 Kai Roemer
 *

	This file is part of distnews.

    distnews is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    any later version.

    distnews is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with distnews; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 * Created on Dec 5, 2004
 *
 */
package distnews.message;

/**
 * @author popel
 *
 */

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class MD5  {
    private String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1,3));
        }
        return sb.toString();
    }
    
/**
 *  Returns the MD5 of a String
 * @param	message	String to be hashed
 * @return			hashing result
 * 
 */
    public String md5 (String message) { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            return hex (md.digest(message.getBytes(System.getProperty("file.encoding")))); 
        } catch (NoSuchAlgorithmException e) { 
            System.out.println("MD5: " + e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("MD5: " + e);
        } 
        return null;
    }
}