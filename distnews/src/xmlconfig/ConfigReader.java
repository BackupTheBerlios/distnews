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


 * Created on 10.12.2004
 *
 */

package xmlconfig;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Still in development
 * will be implemented in distnews-0.3
 * @author popel
 *
 */

/*
 * FIXME newlines in der xmldatei werden komisch behandelt: als eigene knoten
 * 
 */

public class ConfigReader {
    Configuration cfg;
	
	public ConfigReader (String filename, Configuration c) throws Exception {
		
	    this.cfg = c;
	    
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db  = dbf.newDocumentBuilder();
		Document doc = db.parse(filename);
		
		doc.normalize();
		
		Node n = (Node) doc;
		
		this.recursiveTest(n);
	} 
	
	private void recursiveTest(Node n) {
	    int a = 0;

	    NodeList nl = n.getChildNodes();
	    for (int i = 0; i < nl.getLength(); i++) {
	        if(nl.item(i).getNodeName().equals("entry")) {
	            System.out.println(nl.item(i).getAttributes().getNamedItem("name").getNodeValue() + "\t" + nl.item(i).getAttributes().getNamedItem("value").getNodeValue());
	            cfg.addValue(nl.item(i).getAttributes().getNamedItem("name").getNodeValue(), nl.item(i).getAttributes().getNamedItem("value").getNodeValue());
	        }
	        if(nl.item(i).hasAttributes() || nl.item(i).hasChildNodes()) {
		        recursiveTest(nl.item(i));
	        }
	    }
	}
}