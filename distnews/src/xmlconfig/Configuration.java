/*
 * Copyright 2004 by Kai Ršmer
 * mailto: kai.roemer@gmx.de
 * 
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

 *
 * Created on 23.12.2004
 *
 */
package xmlconfig;

import java.util.ArrayList;

/**
 * @author popel
 * 
 */
public class Configuration {
    ArrayList cfglist;
    
    public Configuration () {
        this.cfglist = new ArrayList(7);
    }
    
    public void addValue(String n, String v) {
        this.cfglist.add(new Cfg(n,v));
    }
    
    public String getValue(String n) {
        for (int i = 0; i < this.cfglist.size(); i++) {
            if (((Cfg) this.cfglist.get(i)).name.equals(n)) return ((Cfg) this.cfglist.get(i)).value;
        }
        return null;
    }
    
    public void changeValue(String n, String v) {
        for (int i = 0; i < this.cfglist.size(); i++) {
            if (((Cfg) this.cfglist.get(i)).name.equals(n)) {
                ((Cfg) this.cfglist.get(i)).value = v;
                return;
            }
        }
        this.cfglist.add(new Cfg(n,v));
    }
}
