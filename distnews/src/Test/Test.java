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

import xmlconfig.*;
import distnews.message.*;

/**
 * @author popel
 * 
 */
public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println("Just for development test!");
        String a = "<?xml version='1.0' encoding='UTF-8'?><messagelist date=\"1104340989698\"><message count=\"0\" hash=\"7C03AEB8031097D35CAF3416A1826139\">asdfsd</message></messagelist>";
        System.out.println(a);
        System.out.println();
        
        System.out.println((new XMLMessageParser(a, (new ConfigReader("config.xml",new Configuration())).getConfig())).returnMsgList().toString());
    }
}
