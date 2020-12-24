package me.newceptiondev.day22;

import java.util.ArrayDeque;
import java.util.Queue;

public class CardDeck {

  private final Queue<Integer> deck = new ArrayDeque<>();

  public CardDeck cloneWithSize(int size) {
    CardDeck clone = new CardDeck();
    int currentSize = 0;

    for(Integer card : getDeck()) {
      if(currentSize < size) {
        clone.addCardToDeck(card);
        currentSize++;
      }
    }

    return clone;
  }

  public void addCardToDeck(Integer card) {
    getDeck().add(card);
  }

  private Queue<Integer> getDeck() {
    return deck;
  }

  public boolean equals(CardDeck other) {
    Queue<Integer> deckClone = cloneCardDeck().getDeck();
    Queue<Integer> otherClone = other.cloneCardDeck().getDeck();

    boolean equals = true;

    while(!deckClone.isEmpty() && equals) {
      equals = deckClone.poll().equals(otherClone.poll());
    }

    return equals && otherClone.isEmpty();
  }

  public CardDeck cloneCardDeck() {
    CardDeck clone = new CardDeck();

    for(Integer card : getDeck()) {
      clone.addCardToDeck(card);
    }

    return clone;
  }

  public Integer getFirstCard() {
    return getDeck().poll();
  }

  public boolean isDeckEmpty() {
    return getDeck().isEmpty();
  }

  public int getDeckSize() {
    return getDeck().size();
  }
}
