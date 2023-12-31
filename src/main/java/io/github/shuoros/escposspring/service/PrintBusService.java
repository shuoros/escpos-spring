package io.github.shuoros.escposspring.service;

import io.github.shuoros.escposspring.domain.model.PrintData;
import io.github.shuoros.escposspring.domain.model.PrintRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrintBusService {

    public void handleRequest(PrintRequest printRequest) {
        final List<PrinterService> printerServices = printRequest.getPrinters().stream().map(PrinterService::new).toList();

        for (PrinterService printerService : printerServices) {
            for (PrintData printData : printRequest.getPrintDataList()) {
                printData.write(printerService);
            }
            printerService.cutFull();
            printerService.beep();
            printerService.close();
        }
    }
}
