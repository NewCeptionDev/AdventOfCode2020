package me.newceptiondev.day22;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class CardDeck {

    private final Queue<Integer> deck = new ArrayDeque<>();

    public void addCardToDeck(Integer card) {
        deck.add(card);
    }

    public Integer getFirstCard() {
        return deck.poll();
    }

    public boolean isDeckEmpty(){
        return deck.isEmpty();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public CardDeck clone() {
        CardDeck clone = new CardDeck();

        for(Integer card : deck) {
            clone.addCardToDeck(card);
        }

        return clone;
    }

    public CardDeck cloneWithSize(int size) {
        CardDeck clone = new CardDeck();
        int currentSize = 0;

        for(Integer card : deck) {
            if(currentSize < size) {
                clone.addCardToDeck(card);
                currentSize++;
            }
        }

        return clone;
    }

     public boolean equals(CardDeck other) {
        Queue<Integer> deckClone = this.clone().deck;
        Queue<Integer> otherClone = other.clone().deck;

        boolean equals = true;

        while(deckClone.size() > 0 && equals) {
            equals = deckClone.poll().equals(otherClone.poll());
        }

        return equals && otherClone.size() == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(deck);
    }

    @Override public String toString() {
        return "CardDeck{" + "deck=" + deck + '}';
    }
}
