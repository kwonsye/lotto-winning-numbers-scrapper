package data.lotto.scraping.controller;

import data.lotto.scraping.dto.ResLottoWinningNumbersHistory;
import data.lotto.scraping.service.LottoScrapingService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "로또 당첨 번호 scrapping API")
@RequiredArgsConstructor
@RestController
public class LottoScrapingController {

    private final LottoScrapingService lottoScrapingService;

    @ApiOperation(value = "로또 당첨 번호 history list 조회",
            notes = "최근부터 회차 range 이전까지의 로또 당첨 번호 history list. 최근 순 정렬.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/api/lotto/history/winning-numbers")
    public ResponseEntity<List<ResLottoWinningNumbersHistory>> getLottoWinningNumbersHistory(
            @ApiParam(value = "최근으로부터 조회할 회차 범위", required = true, example = "20")
            @RequestParam(value = "range") int roundRange) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(lottoScrapingService.getLottoWinningNumbersHistory(roundRange));
    }
}
