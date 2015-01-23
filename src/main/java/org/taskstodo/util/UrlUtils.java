package org.taskstodo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.taskstodo.model.Query;

public class UrlUtils {
  public static final String PATTERN_GOOGLE = "(http|https)://www\\.google\\.(de|com)/\\?(.*)#q=([^&]+).*";
  public static final String PATTERN_GOOGLE_SCHOLAR = "(http|https)://scholar\\.google\\.de/scholar\\?(.*)&q=([^&]+).*";
  //public static final String PATTERN_GOOGLE_PATENTS = "(http|https)://scholar\\.google\\.de/scholar\\?(.*)&q=([^&]+).*";
  public static final String PATTERN_YAHOO = "(http|https)://de\\.search\\.yahoo\\.com/search(.*)\\?p=([^&]+).*";
  public static final String PATTERN_BING = "(http|https)://www\\.bing\\.com(/images|/videos|/maps/news)?/search\\?q=([^&]+).*";
  public static final String PATTERN_YOUTUBE = "(http|https)://www\\.youtube\\.com/results\\?(.*)?search_query=([^&]+).*"; 
  public static final String PATTERN_WIKIPEDIA = "(http|https)://(www|de)\\.wikipedia\\.org/(.*)(\\?search=|\\&search=)([^&]+).*";
    
  public static Query getQueryFromUrlString(String url) {
    Pattern pattern = Pattern.compile(PATTERN_GOOGLE);
    Matcher matcher = pattern.matcher(url);
    Query query = null;
        
    if (matcher.matches()) {
      String q = StringUtils.replace(matcher.group(4), "+", " ");
      query = new Query();
      query.setQueryString(q);
      query.setEngine("Google");
      query.setUrl(url);
    } else {
      pattern = Pattern.compile(PATTERN_GOOGLE_SCHOLAR);
      matcher = pattern.matcher(url);
     
      if (matcher.matches()) {
        String q = StringUtils.replace(matcher.group(3), "+", " ");
        query = new Query();
        query.setQueryString(q);
        query.setEngine("Google Scholar");
        query.setUrl(url);
      } else {
        pattern = Pattern.compile(PATTERN_YAHOO);
        matcher = pattern.matcher(url);
       
        if (matcher.matches()) {
          String q = StringUtils.replace(matcher.group(3), "+", " ");
          query = new Query();
          query.setQueryString(q);
          query.setEngine("Yahoo");
          query.setUrl(url);
        } else {
          pattern = Pattern.compile(PATTERN_BING);
          matcher = pattern.matcher(url);
         
          if (matcher.matches()) {
            String q = StringUtils.replace(matcher.group(3), "+", " ");
            query = new Query();
            query.setQueryString(q);
            query.setEngine("Bing");
            query.setUrl(url);
          } else {
            pattern = Pattern.compile(PATTERN_YOUTUBE);
            matcher = pattern.matcher(url);
           
            if (matcher.matches()) {
              String q = StringUtils.replace(matcher.group(3), "+", " ");
              query = new Query();
              query.setQueryString(q);
              query.setEngine("YouTube");
              query.setUrl(url);
            } else {
              pattern = Pattern.compile(PATTERN_WIKIPEDIA);
              matcher = pattern.matcher(url);
             
              if (matcher.matches()) {
                String q = StringUtils.replace(matcher.group(5), "+", " ");
                query = new Query();
                query.setQueryString(q);
                query.setEngine("Wikipedia");
                query.setUrl(url);
              }
            }
          }
        }
      }
    }
    
    return query;
  }
}
