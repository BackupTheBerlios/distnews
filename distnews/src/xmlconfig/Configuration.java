/*
 * Copyright 2004 by Kai R?mer
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
    
    /**
     * Defining standat values
     *
     */
    
    public Configuration () {
        this.cfglist = new ArrayList(7);
        
        this.cfglist.add(new Cfg("server_port","7788"));
        this.cfglist.add(new Cfg("gui_port","7799"));
        this.cfglist.add(new Cfg("server_maxclients","10"));
        this.cfglist.add(new Cfg("gui_maxclients","4"));
        this.cfglist.add(new Cfg("client_sleeptime","5000"));
        this.cfglist.add(new Cfg("iplist_maxsize","250"));
        this.cfglist.add(new Cfg("iplist_daemontype","extern"));
        this.cfglist.add(new Cfg("msglist_maxsize","2000"));
        //this.cfglist.add(new Cfg("",""));
        //this.cfglist.add(new Cfg("",""));
        //this.cfglist.add(new Cfg("",""));
    }
    
    /**
     * Adding new configuration values. If they exist, old values are overwritten.
     * @param n	name of value
     * @param v	value
     */
    public void addValue(String n, String v) {
        this.changeValue(n,v);
        System.out.println(n + "\t\t" + v);
    }
    
    /**
     * Returns value of the named item
     * @param n item name
     * @return value of named item
     */
    public String getValue(String n) {
        for (int i = 0; i < this.cfglist.size(); i++) {
            if (((Cfg) this.cfglist.get(i)).name.equals(n)) return ((Cfg) this.cfglist.get(i)).value;
        }
        return "";
    }
    
    /**
     * Findes value and changes it or adds it if not existing 
     * @param n name of item
     * @param v value of item
     */
    public void changeValue(String n, String v) {
        for (int i = 0; i < this.cfglist.size(); i++) {
            if (((Cfg) this.cfglist.get(i)).name.equals(n)) {
                ((Cfg) this.cfglist.get(i)).value = v;
                return;
            }
        }
        this.cfglist.add(new Cfg(n,v));
    }
    
    public int getIntValue(String n) {
        return Integer.valueOf(this.getValue(n)).intValue();
    }
}
