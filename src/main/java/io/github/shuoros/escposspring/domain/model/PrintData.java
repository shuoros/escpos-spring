package io.github.shuoros.escposspring.domain.model;

import io.github.shuoros.escposspring.service.PrinterService;

public interface PrintData {

    void write(final PrinterService printerService);
}
