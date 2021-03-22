package data.lotto.scraping.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LottoScrapingServiceTest {

    private static final String LOTTO_CRAWLING_BASE_URL = "https://superkts.com/lotto/list/";
    private static final String LOTTO_CRAWLING_QUERY_PARAM = "?pg=";
    private static final Integer ROUND_COUNT_PER_PAGE = 10;

    @Test
    void 크롤링_당첨번호_페이지접근_테스트() throws IOException {
        //given
        int pageNum = 1;
        Connection connection = Jsoup.connect(LOTTO_CRAWLING_BASE_URL +
                LOTTO_CRAWLING_QUERY_PARAM + pageNum);

        //when
        Document html = connection.get();
        Elements historyTable = html.select("tr");

        //then
        assertEquals(11, historyTable.size());
    }

    @Test
    void 회차범위에_따라_크롤링해야할_페이지계산_테스트(){
        //given
        int roundRange = 25;

        //when
        int pageNum = roundRange % ROUND_COUNT_PER_PAGE == 0 ?
                roundRange / ROUND_COUNT_PER_PAGE :
                roundRange / ROUND_COUNT_PER_PAGE + 1;

        //then
        assertEquals(3, pageNum);
    }

    @Test
    void 크롤링_당첨번호_회차개수제한_테스트() throws IOException {
        //given
        int pageNum = 1;
        int roundRange = 5; // 최근 5회차
        Connection connection = Jsoup.connect(LOTTO_CRAWLING_BASE_URL +
                LOTTO_CRAWLING_QUERY_PARAM + pageNum);

        //when
        Document html = connection.get();
        Elements historyTableRow = html.select("tr");
        List<Element> historyElements = historyTableRow.subList(1, roundRange+1);

        for (Element historyElement: historyElements) {
            Elements tds = historyElement.select("td");

            String roundNumber = tds.get(0).text();
            Elements winningNumbers = tds.get(1).select("span");

            winningNumbers.stream()
                    .limit(6)
                    .forEach(winningNumber -> System.out.println(roundNumber + "회차의 당첨번호 : " +winningNumber.text()));
        }

        //then
        assertEquals("954", historyElements.get(0).select("td").get(0).text());
        assertEquals(8, historyElements.get(0).select("td").get(1).select("span").size());
    }

    @Test
    void 로또_보너스번호_크롤링_테스트() throws IOException {
        //given
        int pageNum = 1;
        Connection connection = Jsoup.connect(LOTTO_CRAWLING_BASE_URL +
                LOTTO_CRAWLING_QUERY_PARAM + pageNum);

        //when
        Document html = connection.get();
        Elements historyTableRow = html.select("tr");
        Element latestHistoryElements = historyTableRow.get(1);

        Elements tds = latestHistoryElements.select("td");
        Elements winningNumbers = tds.get(1).select("span");
        Integer bonusNumber = Integer.valueOf(winningNumbers.get(winningNumbers.size()-1).text());

        assertEquals(32, bonusNumber);
    }
}
