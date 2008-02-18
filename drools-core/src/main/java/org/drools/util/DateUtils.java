/*
 * Copyright 2007 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created on Dec 6, 2007
 */
package org.drools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author etirelli
 *
 */
public class DateUtils {
    
    private static final long       serialVersionUID    = 400L;
    private static final String     DEFAULT_FORMAT_MASK = "dd-MMM-yyyy";
    private static final String     DATE_FORMAT_MASK    = getDateFormatMask();
    private static ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat( DATE_FORMAT_MASK );
        };
    };
    
    
    /** Use the simple date formatter to read the date from a string */
    public static Date parseDate(final String input) {
        try {
            return df.get().parse( input );
        } catch ( final ParseException e ) {
            throw new IllegalArgumentException( "Invalid date input format: [" + input + "] it should follow: [" + DATE_FORMAT_MASK + "]" );
        }
    }

    /** Converts the right hand side date as appropriate */
    public static Date getRightDate(final Object object2) {
        if ( object2 == null ) {
            return null;
        }
        if ( object2 instanceof String ) {
            return parseDate( (String) object2 );
        } else if ( object2 instanceof Date ) {
            return (Date) object2;
        } else {
            throw new IllegalArgumentException( "Unable to convert " + object2.getClass() + " to a Date." );
        }
    }

    /** Check for the system property override, if it exists */
    public static String getDateFormatMask() {
        String fmt = System.getProperty( "drools.dateformat" );
        if ( fmt == null ) {
            fmt = DEFAULT_FORMAT_MASK;
        }
        return fmt;
    }
}
