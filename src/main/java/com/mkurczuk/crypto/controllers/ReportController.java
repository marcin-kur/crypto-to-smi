package com.mkurczuk.crypto.controllers;

import com.mkurczuk.crypto.services.report.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ReportController {
    private final ReportGeneratorConfigGetter reportGeneratorConfigGetter;
    private final ReportGenerator reportGenerator;
    private final ReportDTOFactory reportDTOFactory;

    @RequestMapping(value = "/api/report", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<ReportDTO> getReports(
            @RequestParam("startDate") Optional<String> startDate,
            @RequestParam("endDate") Optional<String> endDate,
            @RequestParam("crypto") Optional<String> cryptocurrency,
            @RequestParam("stockIndex") Optional<String> stockMarketIndex,
            @RequestParam("interval") Optional<Integer> interval
    ) {
        log.info("Request Parameters: ", startDate, endDate, cryptocurrency, stockMarketIndex, interval);
        List<Report> reports = reportGenerator.generateReports(reportGeneratorConfigGetter.get(
                startDate,
                endDate,
                cryptocurrency,
                stockMarketIndex,
                interval
        ));
        return reportDTOFactory.createDTO(reports);
    }
}
