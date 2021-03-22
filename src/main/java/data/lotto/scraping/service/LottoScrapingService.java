package data.lotto.scraping.service;

import data.lotto.scraping.dto.ResLottoWinningNumbersHistory;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class LottoScrapingService {

    private static final String LOTTO_CRAWLING_BASE_URL = "https://superkts.com/lotto/list/";
    private static final String LOTTO_CRAWLING_QUERY_PARAM = "?pg=";
    private static final Integer ROUND_COUNT_PER_PAGE = 10;

    public List<ResLottoWinningNumbersHistory> getLottoWinningNumbersHistory(int roundRange) {

        int pageCount = getTotalPageCountToCrawl(roundRange);

        List<ResLottoWinningNumbersHistory> historyList =
                IntStream.range(1, pageCount + 1)
                        .mapToObj((pageNum) -> {
                            int roundCountToCrawl = isLastPage(pageNum, pageCount) ?
                                    getLastPageRoundCount(roundRange) : ROUND_COUNT_PER_PAGE;

                            return doCrawlingSinglePage(pageNum, roundCountToCrawl);
                        })
                        .flatMap(List::stream)
                        .collect(Collectors.toList());

        return historyList;
    }

    @SneakyThrows
    private List<ResLottoWinningNumbersHistory> doCrawlingSinglePage(Integer pageNum, Integer roundCountToCrawl) {

        List<ResLottoWinningNumbersHistory> historyList = new ArrayList<>();

        Connection connection = Jsoup.connect(LOTTO_CRAWLING_BASE_URL +
                LOTTO_CRAWLING_QUERY_PARAM + pageNum);
        Document html = connection.get();

        Elements historyTableRows = html.select("tr");
        List<Element> historyElements = historyTableRows.subList(1, roundCountToCrawl + 1);

        for (Element historyElement : historyElements) {
            Elements tds = historyElement.select("td");

            Integer roundNumber = Integer.valueOf(tds.get(0).text());
            Elements winningNumbers = tds.get(1).select("span");

            List<Integer> winningNumberList = winningNumbers.stream()
                    .limit(6)
                    .map(winningNumber -> Integer.valueOf(winningNumber.text()))
                    .collect(Collectors.toList());

            int lastIndex = winningNumbers.size()-1;
            Integer bonusNumber = Integer.valueOf(winningNumbers.get(lastIndex).text());

            ResLottoWinningNumbersHistory history = ResLottoWinningNumbersHistory.builder()
                    .roundNumber(roundNumber)
                    .winningNumbers(winningNumberList)
                    .bonusNumber(bonusNumber)
                    .build();

            historyList.add(history);
        }
        return historyList;
    }

    private int getLastPageRoundCount(int roundRange) {
        int roundCountToCrawl = roundRange % ROUND_COUNT_PER_PAGE;
        return roundCountToCrawl == 0 ? ROUND_COUNT_PER_PAGE : roundCountToCrawl;
    }

    private int getTotalPageCountToCrawl(int roundRange) {
        return roundRange % ROUND_COUNT_PER_PAGE == 0 ?
                roundRange / ROUND_COUNT_PER_PAGE :
                roundRange / ROUND_COUNT_PER_PAGE + 1;
    }

    private boolean isLastPage(int pageNum, int pageCount) {
        return pageNum == pageCount;
    }

}
