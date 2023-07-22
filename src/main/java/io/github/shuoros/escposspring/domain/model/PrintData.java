package io.github.shuoros.escposspring.domain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.shuoros.escposspring.service.PrinterService;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TemplateData.class, name = "template")
})
public interface PrintData {

    void write(final PrinterService printerService);
}
