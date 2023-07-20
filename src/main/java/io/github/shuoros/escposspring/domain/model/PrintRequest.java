package io.github.shuoros.escposspring.domain.model;

import io.github.shuoros.escposspring.print.Printer;
import lombok.Data;

import java.util.List;

@Data
public class PrintRequest {

    private List<PrintData> printDataList;
    private List<Printer> printers;
    private Boolean notification;
}
