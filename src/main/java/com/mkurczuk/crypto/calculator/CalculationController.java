package com.mkurczuk.crypto.calculator;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CalculationController {

    @RequestMapping(value = "transactions", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    @ResponseBody
    public List<CalculationResultDTO> getAllInJsonOrXml(
            @RequestParam("startDate") Optional<String> startDate,
            @RequestParam("endDate") Optional<String> endDate,
            @RequestParam("crypto") Optional<String> cryptocurrency,
            @RequestParam("stockIndex") Optional<String> stockMarketIndex,
            @RequestParam("interval") Optional<Integer> interval
    ) {
        return null;
    }
}
