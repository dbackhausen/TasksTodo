package org.taskstodo.test;

import org.junit.Assert;
import org.junit.Test;
import org.taskstodo.model.Query;
import org.taskstodo.util.UrlUtils;

public class UrlParsingTest {

  @Test
  public void test() {
    Query query = null;
    
    query = UrlUtils.getQueryFromUrlString("https://www.google.de/?gws_rd=ssl#q=taskstodo");
    Assert.assertEquals(query.getEngine(), "Google");
    Assert.assertEquals(query.getQueryString(), "taskstodo");
    
    query = UrlUtils.getQueryFromUrlString("https://www.google.de/?gws_rd=ssl#q=taskstodo+information+retrieval");
    Assert.assertEquals(query.getEngine(), "Google");
    Assert.assertEquals(query.getQueryString(), "taskstodo information retrieval");

    query = UrlUtils.getQueryFromUrlString("https://www.google.de/?gws_rd=ssl#q=taskstodo&start=10");
    Assert.assertEquals(query.getEngine(), "Google");
    Assert.assertEquals(query.getQueryString(), "taskstodo");
    
    query = UrlUtils.getQueryFromUrlString("http://scholar.google.de/scholar?hl=de&q=Matthias+Hemmje&btnG=&lr=");
    Assert.assertEquals(query.getEngine(), "Google Scholar");
    Assert.assertEquals(query.getQueryString(), "Matthias Hemmje");
    
    query = UrlUtils.getQueryFromUrlString("https://de.search.yahoo.com/search;_ylt=AnAfsLDuvH.kMMQZRQFABWwqrK5_?p=taskstodo&toggle=1&cop=mss&ei=UTF-8&fr=yfp-t-204&fp=1");
    Assert.assertEquals(query.getEngine(), "Yahoo");
    Assert.assertEquals(query.getQueryString(), "taskstodo");
    
    query = UrlUtils.getQueryFromUrlString("https://de.search.yahoo.com/search;_ylt=A9mSs2EeVIdUGjEAhjAzCQx.;_ylc=X1MDMjExNDcxODAwMwRfcgMyBGZyA3lmcC10LTIwNARncHJpZANaTFVFTFB1MFN6aWRBWmpPakxMUTJBBG5fcnNsdAMwBG5fc3VnZwMwBG9yaWdpbgNkZS5zZWFyY2gueWFob28uY29tBHBvcwMwBHBxc3RyAwRwcXN0cmwDBHFzdHJsAzE1BHF1ZXJ5A01hdHRoaWFzIEhlbW1qZQR0X3N0bXADMTQxODE1NTE0MQ--?p=Matthias+Hemmje&fr2=sb-top-de.search&fr=yfp-t-204&fp=1");
    Assert.assertEquals(query.getEngine(), "Yahoo");
    Assert.assertEquals(query.getQueryString(), "Matthias Hemmje");
    
    query = UrlUtils.getQueryFromUrlString("https://de.search.yahoo.com/search;_ylt=A9mSs2uFVIdUpXwA.hMzCQx.?p=Matthias+Hemmje&fr=yfp-t-204&fr2=sb-top-de.search&fp=1&xargs=0&b=11");
    Assert.assertEquals(query.getEngine(), "Yahoo");
    Assert.assertEquals(query.getQueryString(), "Matthias Hemmje");

    query = UrlUtils.getQueryFromUrlString("http://www.bing.com/search?q=taskstodo+task+management&go=Senden&qs=n&form=QBRE&pq=taskstodo+task+management&sc=0-10&sp=-1&sk=&cvid=c6d9835ef388471890d0cc4a687092b6");
    Assert.assertEquals(query.getEngine(), "Bing");
    Assert.assertEquals(query.getQueryString(), "taskstodo task management");
    
    query = UrlUtils.getQueryFromUrlString("http://www.bing.com/search?q=taskstodo+task+management&go=Senden&qs=n&pq=taskstodo+task+management&sc=0-10&sp=-1&sk=&cvid=c6d9835ef388471890d0cc4a687092b6&first=11&FORM=PORE");
    Assert.assertEquals(query.getEngine(), "Bing");
    Assert.assertEquals(query.getQueryString(), "taskstodo task management");

    query = UrlUtils.getQueryFromUrlString("http://www.bing.com/images/search?q=taskstodo+task+management&FORM=HDRSC2");
    Assert.assertEquals(query.getEngine(), "Bing");
    Assert.assertEquals(query.getQueryString(), "taskstodo task management");
    
    query = UrlUtils.getQueryFromUrlString("https://www.youtube.com/results?search_query=task+management&spfreload=10");
    Assert.assertEquals(query.getEngine(), "YouTube");
    Assert.assertEquals(query.getQueryString(), "task management");
    
    query = UrlUtils.getQueryFromUrlString("https://www.youtube.com/results?lclk=month&search_query=task+management&filters=month&spfreload=10");
    Assert.assertEquals(query.getEngine(), "YouTube");
    Assert.assertEquals(query.getQueryString(), "task management");
    
    query = UrlUtils.getQueryFromUrlString("http://de.wikipedia.org/wiki/Special:Search?search=Task+Management&go=Go");
    Assert.assertEquals(query.getEngine(), "Wikipedia");
    Assert.assertEquals(query.getQueryString(), "Task Management");
    
    query = UrlUtils.getQueryFromUrlString("http://de.wikipedia.org/wiki/Spezial:Search?ns0=1&search=Task+Management");
    Assert.assertEquals(query.getEngine(), "Wikipedia");
    Assert.assertEquals(query.getQueryString(), "Task Management");
  }
}
