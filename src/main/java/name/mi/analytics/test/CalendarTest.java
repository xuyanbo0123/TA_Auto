package name.mi.analytics.test;

import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;

import java.net.*;
import java.io.*;

public class CalendarTest {
    public static void main(String[] args) throws Exception {
        CalendarService myService = new CalendarService("exampleCo-exampleApp-1.0");
        myService.setUserCredentials("admin@trendanalytical.com", "FLK876#$IUfkjs#$i8LKFJdf09&*&^");

        URL feedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
        CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);

        System.out.println("Your calendars:");
        System.out.println();

        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
            CalendarEntry entry = resultFeed.getEntries().get(i);
            System.out.println("\t" + entry.getTitle().getPlainText());
        }
    }
}
