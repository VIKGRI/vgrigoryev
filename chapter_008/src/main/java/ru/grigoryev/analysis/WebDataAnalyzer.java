package ru.grigoryev.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides methods for parsing, reading
 * from web site SQL.ru. Writes information
 * about job vacancies for Java developers
 * to the specified database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 28.10.2017
 */
public class WebDataAnalyzer extends TimerTask {
    /**
     * URL to read information from.
     */
    private String url;
    /**
     * Contains time in millis of last job posting added
     */
    private Timestamp lastUpdate;
    /**
     * Database manager.
     */
    private DBManager dbmanager;
    /**
     * Timer to schedule program launchings.
     */
    private Timer timer;
    /**
     * The number of program launchings per day.
     */
    private int periodicityPerDay;
    /**
     * Used for logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(WebDataAnalyzer.class);

    /**
     * Constructor.
     *
     * @throws IOException  thrown when file reading problems occur
     * @throws SQLException thrown when problems related to executing
     *                      database sql statements occur
     */
    public WebDataAnalyzer(String url) throws IOException, SQLException {
        if (url != null) {
            this.url = url;
        } else {
            throw new IOException("URL is not correct");
        }
        this.dbmanager = new DBManager();
        this.timer = new Timer();
        this.periodicityPerDay = 1;
    }

    @Override
    public void run() {
        try {
            dbmanager.connectDataBase();
            lastUpdate = dbmanager.updateLastMessageTime();
            if (lastUpdate == null) {
                lastUpdate = new Timestamp(System.currentTimeMillis());
                Calendar firstDayOfYear = Calendar.getInstance();
                firstDayOfYear.set(2017, Calendar.JANUARY, 1, 0, 0, 1);
                lastUpdate = new Timestamp(firstDayOfYear.getTimeInMillis());
            }
            this.readWebPage(this.url);
            dbmanager.commitTransaction();
            System.out.println("Web site is read, database is updated");
        } catch (IOException | SQLException e) {
            dbmanager.rollbackTransaction(LOG);
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                dbmanager.closeConnection();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Parses and reads web page specified
     * by url and stores information in the
     * database.
     *
     * @throws IOException thrown if problems with file reading occur
     */
    private void readWebPage(String url) throws IOException {
        // gets document which contains parsed Web page
        Document document = Jsoup.connect(url).get();
        // gets second table in the page
        Element table = document.select("table").get(2);
        // gets table rows
        Elements rows = table.select("tr");
        // checks whether it's needed to load the next page
        boolean processNextPage = true;

        //Process each row
        for (int i = 1; i < rows.size(); i++) {
            // gets row by index
            Element row = rows.get(i);
            // gets columns
            Elements columns = row.select("td");
            String topic = columns.get(1).text();
            if (!topic.startsWith("Важно:") && topic.toLowerCase().contains("java")) {
                //Gets date of row
                Calendar jobPostingDate = this.parseDate(columns.get(5).text());
                // We don't want to process job postings which have already been processed
                if (jobPostingDate.getTimeInMillis() < this.lastUpdate.getTime()) {
                    processNextPage = false;
                    break;
                }
                try {

                    // Column with author info. It may contain reference to information about author.
                    if (columns.get(2).children().size() > 0) {
                        dbmanager.insertAuthor(columns.get(2).child(0).text(), columns.get(2).child(0).attr("href"));
                    } else {
                        dbmanager.insertAuthor(columns.get(2).text(), "");
                    }
                    // Column with topic info. It contains reference to vacancy description.
                    if (columns.get(1).children().size() > 0) {
                        dbmanager.insertVacancy(columns.get(1).child(0).text(), columns.get(1).child(0).attr("href"),
                                columns.get(2).child(0).text(), new Timestamp(jobPostingDate.getTimeInMillis()));
                    } else {
                        dbmanager.insertVacancy(topic, "",
                                columns.get(2).child(0).text(), new Timestamp(jobPostingDate.getTimeInMillis()));
                    }
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }

        // Deletes vacancies with JavaScript only
        try {
            dbmanager.filterVacancies("deleteJavaScript.sql");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        // If we load all page we will load next page
        if (processNextPage) {
            this.readWebPage(this.getNextPageUrl(document));
        }
    }

    /**
     * The number of program launchings per day.
     *
     * @param launchingsPerDay number of program launchings per day
     */
    public void setPeriodicity(int launchingsPerDay) {
        if (launchingsPerDay > 0) {
            this.periodicityPerDay = launchingsPerDay;
        }
    }

    /**
     * Launches system which provides reading from web site
     * and writing results to the database with a period of time
     * counted from specified periodicity
     */
    public void launchSystem() {
        long interval = 24 * 60 * 60 * 1000 / this.periodicityPerDay;
        timer.schedule(this, 3000, interval);
    }

    /**
     * Parses string which contains job posting date.
     *
     * @param jobPostingDate string which contains job posting date
     * @return job posting date as Calendar object
     */
    private Calendar parseDate(String jobPostingDate) {
        String[] date = jobPostingDate.split(" ");
        int year;
        int month;
        int day;
        int hours;
        int minutes;
        int seconds;
        /*If the date is represented like 26 окт 17, 23:21*/
        if (date.length > 2) {
            day = Integer.parseInt(date[0].trim());
            switch (date[1].trim()) {
                case "янв":
                    month = Calendar.JANUARY;
                    break;
                case "фев":
                    month = Calendar.FEBRUARY;
                    break;
                case "мар":
                    month = Calendar.MARCH;
                    break;
                case "апр":
                    month = Calendar.APRIL;
                    break;
                case "май":
                    month = Calendar.MAY;
                    break;
                case "июн":
                    month = Calendar.JUNE;
                    break;
                case "июл":
                    month = Calendar.JULY;
                    break;
                case "авг":
                    month = Calendar.AUGUST;
                    break;
                case "сен":
                    month = Calendar.SEPTEMBER;
                    break;
                case "окт":
                    month = Calendar.OCTOBER;
                    break;
                case "ноя":
                    month = Calendar.NOVEMBER;
                    break;
                case "дек":
                    month = Calendar.DECEMBER;
                    break;
                default:
                    throw new RuntimeException("Invalid month");
            }
            // Sets year
            year = 2000 + Integer.parseInt(date[2].trim().substring(0, 2));
            // Sets time
            String[] time = date[3].trim().split(":");
            hours = Integer.parseInt(time[0]);
            minutes = Integer.parseInt(time[1]);
            seconds = 0;
        } else { /*If the date is represented like сегодня, 11:19*/
            Calendar today = Calendar.getInstance();
            if (date[0].trim().startsWith("сегодня")) {
                today.setTimeInMillis(System.currentTimeMillis());
                year = today.get(Calendar.YEAR);
                month = today.get(Calendar.MONTH);
                day = today.get(Calendar.DAY_OF_MONTH);
            } else { /*If the date is represented like вчера, 11:19*/
                today.setTimeInMillis(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
                year = today.get(Calendar.YEAR);
                month = today.get(Calendar.MONTH);
                day = today.get(Calendar.DAY_OF_MONTH);
            }
            String[] time = date[1].trim().split(":");
            hours = Integer.parseInt(time[0]);
            minutes = Integer.parseInt(time[1]);
            seconds = 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hours, minutes, seconds);
        return calendar;
    }

    /**
     * Gets url of the next page of the given web site.
     *
     * @param document document got via parsing previous page by JSoup
     * @return url of next page
     */
    private String getNextPageUrl(Document document) {
        Element table2 = document.select("table").get(3);
        Element td = table2.select("td").get(0);
        Element page = table2.select("b").first();
        return td.children().get(page.elementSiblingIndex() + 1).attr("href");
    }
}
