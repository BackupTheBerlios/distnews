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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;

import xmlconfig.Configuration;


/**
 * @author popel
 * 
 * Container for all messages
 * Provides the methods to handel the messages
 *
 */

public class MsgList {
    ArrayList	msglist;
    HashList	hashtable;
    
/**
 * Constructor: MsgList
 * 
 * @param	size	initial size of the new MessageList
 * 
 */
    public MsgList (int size, Configuration c) {
        msglist = new ArrayList(size);
        this.hashtable = new HashList();
    }
    
/**
 * Returns named Message from MessageList
 * 
 * @param	count	Message to fetch
 * @return			MessageContainer containing the fetched message
 *  
 */
    public MessageContainer getMessageByNumber(int count) {
        if (count < msglist.size()) return (MessageContainer) msglist.get(count);
        return null;
    }

/**
 * Adds Message from String
 * @param	message	MessageString that should be added
 * 
 */
    public void msgAdd(String message) {
        message = message.replaceAll("\n","");
        MessageContainer mc = new MessageContainer(message);
        if (!this.checkExist(mc) && !this.msgFilter(mc) && (message.length() < 12000)) {
            this.msglist.add(mc);
            this.hashtable.addHash(mc.getHash());
        }
    }
    
/**
 * Adds Message from MessageContainer
 * @param	mc	MessageContainer that represents the message that should be added
 */
    public void msgAdd(MessageContainer mc) {
        if (!this.checkExist(mc) && !this.msgFilter(mc) && (mc.getMessage().length() < 12000)) {
            this.msglist.add(mc);
            this.hashtable.addHash(mc.getHash());
        }
    }
    
/**
 * Removes Message from Messagelist by position
 * @param	i	number of the message that should be removed 
 */
    public void removeMsg(int i) {
        if (i < msglist.size()) {
            this.hashtable.remHash(((MessageContainer) this.msglist.get(i)).getHash());
            this.msglist.remove(i);
        }
    }
    
/**
 * Removes Message from MessageList by hash
 * @param	hash	hash of message to be removed
 */
    public void removeMsg(String hash) {
        for (int i = 0; i < msglist.size(); i++) {
            if(((MessageContainer) msglist.get(i)).getHash().equalsIgnoreCase(hash)) {
                this.hashtable.remHash(((MessageContainer) this.msglist.get(i)).getHash());
                msglist.remove(i);
                i = msglist.size();
            }
        }
    }
    
/**
 * Returns size of MessageList
 * @return			size of MessageList
 * 
 */
    public int size() {
        return this.msglist.size();
    }
    
/**
 * Checks if a message in a MessageContainer exists
 * @param	mc	message to look for
 * @return		?(message exists)
 * 
 */
    public boolean checkExist(MessageContainer mc) {
        for (int i = 0; i < msglist.size(); i++) {
            if (mc.getHash().equals(((MessageContainer) msglist.get(i)).getHash())) return true;
        }
        return false;
    }
    
/**
 * Returns a xml message list
 * @return 		String containing a xml message list
 * 
 */
    public String toString() {
        String sysprop = System.getProperty("file.encoding");
        String a = "<?xml version='1.0' encoding='" + sysprop + "'?><messagelist date=\""+ System.currentTimeMillis() + "\">\n";
        for (int i = 0; i < msglist.size(); i++) {
            a = a.concat("<message count=\""+ i + "\" hash=\"" + ((MessageContainer) msglist.get(i)).getHash() + "\">" + ((MessageContainer) msglist.get(i)).getMessage() + "</message>\n");
        }
        a = a.concat("</messagelist>");
        return a;
    }
    
/**
 * Returns a xml message list with messages named in hashlist string
 * @param hl
 * @return	xml message string containig a message list
 */
    public String toString(String hl) {
        int is = 0;
        String sysprop = System.getProperty("file.encoding");
        String a = "<?xml version='1.0' encoding='" + sysprop + "'?><messagelist date=\""+ System.currentTimeMillis() + "\">\n";
        
        String [] h = hl.split("\n");
        
        for (int i = 0; i < h.length; i++) {
            if(this.hashtable.inHashs(h[i])) {
                a = a.concat("<message count=\""+ is++ + "\" hash=\"" + h[i] + "\">" + ((MessageContainer) this.getMsgByHash(h[i])).getMessage() + "</message>\n");
            }
        }
        a = a.concat("</messagelist>");
        return a;
    }
    
    private MessageContainer getMsgByHash(String hash) {
        for (int i = 0; i < this.msglist.size(); i++) {
            if(((MessageContainer) this.msglist.get(i)).getHash().equals(hash.trim())) return ((MessageContainer) this.msglist.get(i));  
        }
        return null;
    }
    
/**
 * Adds a MessageList to this MessageList
 * @param	ml	MsgList that should be added
 * 
 */
    public void addMsgList(MsgList ml) {
        if (ml != null) {
            for(int i = 0; i < ml.size(); i++) {
                this.msgAdd(ml.getMessageByNumber(i));
            }
        }
    }
   
/**
 * Checks if the size of this MsgList is too big and removes messages from the List.
 * MaxSize = 2000
 *
 */
    private void checkSize () {
        if (msglist.size() > 2000) {
            for(int i = 0; i < 100; i++) {
                msglist.remove(0);
            }
        }
    }

/**
 * Filter Interface for Messages
 * @param	mc	message that should be checked against the filter
 * @return		?(kill message)
 * 
 */
    private boolean msgFilter (MessageContainer mc) {
    		return new MsgFilter("wordfilter", mc, 10).filter();
    }
    
/**
 * Returns the hashes of all messages in the MsgList
 * @return		hashes of all messages in the MsgList
 * 
 */
    public String hashList() {
        return this.hashtable.hashTable();
    }
    
/**
 * Returns a list of all hashes that are new to MsgList.  
 * @param	mlist	list of message hashs  
 * @return			list of new message hashs
 * 
 */
    public String needNewMsgList(String mlist) {
        return this.hashtable.diffHash(mlist.trim().replaceAll(" ", "").replaceAll("\n\n", "\n").split("\n"));
    }
/**
 * Saves MsgList to a file 
 * @param	filename	name of the file to create (all content is deleted)
 *   
 */
    public void saveMsgList(String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(this.toString());
            bw.write("\n");
            bw.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("MsgList --> saveMsgList --> File: " + filename + " not found!");
        }
        catch (IOException e) {
            System.out.println("MsgList --> saveMsgList " + e);
        }
    }
}