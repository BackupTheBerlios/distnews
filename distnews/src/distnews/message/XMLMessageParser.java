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


 * Created on Dec 6, 2004
 *
 */
package distnews.message;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import xmlconfig.Configuration;

/**
 * @author popel
 *
 */
public class XMLMessageParser {
    String xmlsource ="";
    MsgList ml;

/**
 *  Constructor
 * @param	a	message to be parsed
 */
    public XMLMessageParser(String a, Configuration c) {
        a = a.replaceAll("\n","");
        a = a.replaceAll("\t","");
        a = a.replaceAll("\r","");
        a = a.replaceAll("\f","");
        
        while (a.matches(".*  .*")) {
            a = a.replaceAll("  "," ");
        }
        
        
        this.xmlsource = a;
        
        // System.out.println(a);
        
        this.ml		= new MsgList(0, c);
    }
    
/**
 * Reads String and parses it to a MsgList
 * @return		MsgList created out of the Input String
 * @throws		Exception
 */
    public MsgList returnMsgList() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db  = dbf.newDocumentBuilder();
            //System.out.println("123");
            Document doc = db.parse(new InputSource(new StringReader(xmlsource)));
            
            
            ArrayList al = new ArrayList(3);
            Node node = (Node) doc;
            
            //System.out.println("XMLMESSAGEPARESER: GOT MESSAGE");
            for (Node c = node.getFirstChild().getFirstChild();c != null; c = c.getNextSibling()) {
                
                MessageContainer mc = new MessageContainer();
                String a = "";
                if(c.getFirstChild().getNodeType() == 3) {
                    mc.setMessage(c.getFirstChild().getNodeValue());
            	} else if (c.getFirstChild().getNodeType() == 1){
            	    for (Node n = c.getFirstChild(); n != null; n = n.getNextSibling()) {
            	        a = a + n.toString();
            	    }
            	    mc.setMessage(a);
            	} else {
                    System.out.println("XMLMESSAGEPARSER: NodeTypeError");
                }
                
                if (c.getAttributes().getNamedItem("hash").getNodeValue().equals(mc.getHash()) || c.getAttributes().getNamedItem("hash").getNodeValue().equals("X")) {
                    ml.msgAdd(mc);
                } else System.out.println("message not consistant : new -> " + c.getAttributes().getNamedItem("hash").getNodeValue() + "  -  old -> " + mc.getHash() + " "+ mc.getMessage());
            }
            return ml;
        } catch (Exception e) {
            System.out.println("XMLMESSAGEPARSER: " + e);
        }
        System.out.println("XMLMESSAGPARSER: lost messages");
        return null;
    }
}
