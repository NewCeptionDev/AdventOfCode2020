package me.newceptiondev.day25;

import me.newceptiondev.util.FileUtil;

import java.util.List;

public class Day25 {

  private static final String FILE_NAME = "day25Task1Input";
  private static final int SUBJECT_NUMBER = 7;
  private static final int DIVIDER = 20201227;

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day25(inputs);
  }

  public Day25() {
  }

  private Day25(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Encryption Key
   */
  public long task1(final List<String> inputs) {

    final long cardPublicKey = Long.parseLong(inputs.get(0));
    final long doorPublicKey = Long.parseLong(inputs.get(1));

    int cardLoopSize = findLoopSize(cardPublicKey);

    return calculateEncryptionKey(doorPublicKey, cardLoopSize);
  }

  /**
   * Finds the Loop Size for the given Public Key
   *
   * @param publicKey Public Key
   *
   * @return Loop Size
   */
  private int findLoopSize(final long publicKey) {
    int loopSize = 0;
    long value = 1;

    while(value != publicKey) {
      value *= SUBJECT_NUMBER;
      value %= DIVIDER;
      loopSize++;
    }

    return loopSize;
  }

  /**
   * Calculates the Encryption Key with the given public Key as a Subject Number and the given Loop Size
   *
   * @param publicKey Public Key used as Subject Number
   * @param loopSize  Loop Size
   *
   * @return Encryption Key
   */
  private long calculateEncryptionKey(final long publicKey, final int loopSize) {
    long value = 1;

    for(int i = 0; i < loopSize; i++) {
      value *= publicKey;
      value %= DIVIDER;
    }

    return value;
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return
   */
  public int task2(final List<String> inputs) {
    return 0;
  }
}
