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
 * Container for a message
 * @author popel
 *
 */

public class MessageContainer {
    private String msg = "";
    private String hash = "";
    
/**
 * Construct a message with content from String a
 * @param	a	body of the message
 * 
 */
    public MessageContainer (String a) {
        msg = a;
        hash = this.md5();
    }
   
/**
 * empty message container
 *
 */
    public MessageContainer () {
    }
    
/**
 * Set the message in the MessageContainer
 * @param	a	new message body
 * 
 */
    public void setMessage(String a) {
        msg = a;
        hash = this.md5();
    }
    
/**
 * Resturns body of the message
 * @return		body of the message
 */
    public String getMessage() {
        return msg;
    }
    
/**
 * Returns Hash of the message
 * @return		hash of the message
 */
    public String getHash() {
        return hash;
    }
    
/**
 * Returns hash of the message
 * @return		hash of the message
 */
    private String md5() {
        MD5 a = new MD5();
        return a.md5(msg);
    }
}
