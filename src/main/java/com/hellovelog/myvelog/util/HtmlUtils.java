package com.hellovelog.myvelog.util;



import org.jsoup.Jsoup;

public class HtmlUtils { //html 태그 이스케이프 유틸
    public static String stripHtml(String html) {
        return Jsoup.parse(html).text();
    }
}
