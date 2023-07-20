package io.github.shuoros.escposspring.web.controller;

import io.github.shuoros.escposspring.domain.model.PrintRequest;
import io.github.shuoros.escposspring.service.PrintBusService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {

    @Resource
    private PrintBusService printBusService;

    @PostMapping("/print")
    public void print(@RequestBody PrintRequest printRequest) {
        printBusService.handleRequest(printRequest);
    }
}
