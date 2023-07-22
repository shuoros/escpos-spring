package io.github.shuoros.escposspring.print;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NetworkPrinter.class, name = "network"),
        @JsonSubTypes.Type(value = SerialPrinter.class, name = "serial")
})
public interface Printer {
    void open();

    void write(byte[] command);

    void close();
}
