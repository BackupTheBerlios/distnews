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
 * Created on 11.12.2004
 *
 */
package Test;

import java.util.*;

/**
 * @author popel
 * 
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ArrayList al = new ArrayList(2);
        al.add("a238jksdf83");
        al.add("348kldf843");
        al.add("d89ktznod");
        
        System.out.println(al.toString());
        System.out.println("-----------");
        al.remove("d89ktznod");
        System.out.println(al.toString());
    }
}
