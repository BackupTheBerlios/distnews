/*
 * Copyright 2004 by Kai Römer
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
 * Created on 11.12.2004
 *
 */
package network.server;

/**
 * @author popel
 * 
 */
public class Monitor {
    private int max;
    private int monitor;
    
    public Monitor(int m) {
        this.max = m;
        this.monitor = 0;
    }
    
    public void monitorAdd() {
        this.monitor++;
    }
    
    public void monitorRem() {
        this.monitor--;
    }
    
    public boolean isProteced() {
        return (monitor >= max);
    }
    public boolean isFree() {
        return (monitor < max);
    }
}
