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

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author popel
 *
 */

/*
 * FIXME newlines in der xmldatei werden komisch behandelt: als eigene knoten
 * 
 */

public class ConfigReader {
	ArrayList cl = new ArrayList(0);
	int b = 0;
	
	public ConfigReader (String filename) throws Exception {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db  = dbf.newDocumentBuilder();
		Document doc = db.parse(filename);
		
		doc.normalize();
		
		Node n = (Node) doc;
		
		System.out.println("rek\tfor\ttext");
		
		this.recursiveTest(n);
	} 
	
	/*public String getValue(String a) {
		for(int i = 0; i < cl.size(); i++) {
			if(((Cfg) cl.get(i)).name.trim().equals(a.trim())) {
				return ((Cfg) cl.get(i)).value.trim();
			}
		}
		return null;
	}*/
	
	/*public String toString() {
		String a = "";
		for(int i = 0; i < cl.size(); i++) {
			a += ((Cfg) cl.get(i)).name.trim() + " --> " +  ((Cfg) cl.get(i)).value.trim() + "\n";
		}
		return a;
	}*/
	
	private void recursiveTest(Node n) {
	    int a = 0;

	    NodeList nl = n.getChildNodes();
	    for (int i = 0; i < nl.getLength(); i++) {
	        if(nl.item(i).hasAttributes() || nl.item(i).hasChildNodes()) {
	            b= i;
		        System.out.println(b + "\t" + a + "\t>" + nl.item(i).toString().trim().replaceAll("\\n","").replaceAll("\t","") + "<");
		        
		        recursiveTest(nl.item(i));

	        }
	    }
	}
}