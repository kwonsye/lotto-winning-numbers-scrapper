package data.lotto.scraping.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"roundNumber", "winningNumbers", "bonusNumber"})
public class ResLottoWinningNumbersHistory {

    @ApiModelProperty(value = "로또 회차 번호")
    private Integer roundNumber;
    @ApiModelProperty(value = "로또 당첨 번호 리스트")
    private List<Integer> winningNumbers;
    @ApiModelProperty(value = "로또 보너스 번호")
    private Integer bonusNumber;
}
