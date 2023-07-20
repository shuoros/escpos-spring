package io.github.shuoros.escposspring.controller;

import io.github.shuoros.escposspring.domain.model.PrintRequest;
import io.github.shuoros.escposspring.domain.model.TemplateData;
import io.github.shuoros.escposspring.print.NetworkPrinter;
import io.github.shuoros.escposspring.web.controller.PrinterController;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PrinterControllerTests {

    @Resource
    private PrinterController printerController;

    @Test
    void testPrint() {
        // Arrange
        final PrintRequest printRequest = new PrintRequest();
        printRequest.setPrinters(List.of(new NetworkPrinter("192.168.1.62", 9100)));
        printRequest.setPrintDataList(
                List.of(
                        TemplateData.builder()
                                .template("full-receipt-persian.html")
                                .width(320)
                                .variables(
                                        List.of(
                                                TemplateData.Variable.builder()
                                                        .key("key")
                                                        .value("value")
                                                        .build()
                                        )
                                )
                                .build()
                )
        );
        printRequest.setNotification(true);

        // Act
        printerController.print(printRequest);
    }
}
