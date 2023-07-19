package io.github.shuoros.escposspring.print;

public interface Printer {
   void open();

   void write(byte[] command);

   void close();
}
