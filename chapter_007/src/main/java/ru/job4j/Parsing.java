package ru.job4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Entries;
import ru.job4j.model.Entry;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Parsing {
    private static final Logger LOG = LoggerFactory.getLogger(Parsing.class);
    private final Locale locale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
    private final DateFormat df = new SimpleDateFormat("dd MMM yy, kk:mm", locale);
    private List<String> ignoreDate  = Arrays.asList("сегодня", "вчера", "22 янв 16, 10:56", "11 фев 09, 00:03");
    private List<String> findValue = Arrays.asList("java");
    private List<String> ignoreValue = Arrays.asList("javascript", "java script");

    public Entries execute() {
        Date dateBefore = getDateBefore();
        LinkedList<Entry> entries = new LinkedList<>();
        LOG.info("старт парсинга");
        for (int i = 1; i < 700; i++) {
            try {
                Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i)
                        .data("query", "Java")
                        .userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(20000)
                        .post();

                Elements masthead = doc.getElementsByClass("forumTable");
                for (Element element : masthead) {
                    for (Element tr : element.select("tr")) {
                        Elements row = tr.getElementsByClass("postslisttopic");
                        if (checkRow(row, tr, dateBefore)) {
                            if (checkHead(row.eachText().get(0).toLowerCase())) {
                                entries.add(createEntry(tr));
                            }
                        }
                        if (!checkDate(tr, dateBefore)) {
                            return new Entries(entries);
                        }
                    }
                }
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return new Entries(entries);
    }

    private boolean checkDate(Element tr, Date dateBefore) {
        Date date = new Date();
        try {
            List<String> list = tr.select("td").eachText();
            if (list.size() >= 5) {
                String target = list.get(4);
                if (!ignoreDate.stream().anyMatch(target::contains)) {
                    date = df.parse(target);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return dateBefore.before(date);
    }

    private boolean checkRow(Elements row, Element tr, Date searchDate) {
        return !row.isEmpty();
    }

    private Date getDateBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTime();
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    private boolean checkHead(final String head) {
        return findValue.stream().anyMatch(head::contains) && !ignoreValue.stream().anyMatch(head::contains);
    }

    private Entry createEntry(Element node) throws IOException {
        Elements row = node.getElementsByClass("postslisttopic");
        String name = row.eachText().get(0);
        String link = row.select("a").get(0).attr("href");
        int id = Integer.parseInt(link.substring(25, 32));
        String text = getText(link);
        return new Entry(id, name, text, link);
    }

    private String getText(String link) throws IOException {
        Document doc = Jsoup.connect(link)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(20000)
                .post();
        return doc.getElementsByClass("msgBody").eachText().get(1);
    }
}
