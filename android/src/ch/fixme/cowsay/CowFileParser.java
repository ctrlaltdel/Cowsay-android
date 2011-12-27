package ch.fixme.cowsay;

import java.io.*;

class CowFileParser {
  public static void main(String[] args) throws Throwable {
    System.out.println("Hello world");

    BufferedReader buf = new BufferedReader(new FileReader("../../../../assets/cows/default.cow"));

    String line;

    while (true) {
      line = buf.readLine();

      if (line == null) {
        break;
      }

      System.out.println(line);
    }
  }

}
