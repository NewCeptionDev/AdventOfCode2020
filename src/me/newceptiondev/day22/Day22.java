package me.newceptiondev.day22;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 {

    public static final String fileName = "day22Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day22(inputs);
    }

    public Day22() {
    }

    public Day22(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return
     */
    public int task1(final List<String> inputs) {
        Tuple<CardDeck, CardDeck> decks = parseCardDecks(inputs);

        while (!decks.getX().isDeckEmpty() && !decks.getY().isDeckEmpty()) {
            playRoundForTask1(decks.getX(), decks.getY());
        }

        if (decks.getX().isDeckEmpty()) {
            return calculateScore(decks.getY());
        } else {
            return calculateScore(decks.getX());
        }
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return
     */
    public int task2(final List<String> inputs) {
        Tuple<CardDeck, CardDeck> decks = parseCardDecks(inputs);

        return playGame(decks.getX(), decks.getY()).getY();
    }

    private Tuple<CardDeck, CardDeck> parseCardDecks(List<String> inputs) {
        boolean secondDeck = false;
        Tuple<CardDeck, CardDeck> decks = new Tuple<>(new CardDeck(), new CardDeck());

        for (String input : inputs) {
            if (input.equals("")) {
                secondDeck = true;
            } else if (!input.startsWith("Player")) {
                if (!secondDeck) {
                    decks.getX().addCardToDeck(Integer.parseInt(input));
                } else {
                    decks.getY().addCardToDeck(Integer.parseInt(input));
                }
            }
        }

        return decks;
    }

    private void playRoundForTask1(CardDeck deck1, CardDeck deck2) {
        Integer deck1TopCard = deck1.getFirstCard();
        Integer deck2TopCard = deck2.getFirstCard();

        if (deck1TopCard > deck2TopCard) {
            deck1.addCardToDeck(deck1TopCard);
            deck1.addCardToDeck(deck2TopCard);
        } else {
            deck2.addCardToDeck(deck2TopCard);
            deck2.addCardToDeck(deck1TopCard);
        }
    }

    private Tuple<CardDeck, CardDeck> playRoundForTask2(CardDeck deck1, CardDeck deck2) {

        Integer deck1TopCard = deck1.getFirstCard();
        Integer deck2TopCard = deck2.getFirstCard();

        int winnerOfRound;

        if (deck1.getDeckSize() >= deck1TopCard && deck2.getDeckSize() >= deck2TopCard) {
            winnerOfRound = playGame(deck1.cloneWithSize(deck1TopCard), deck2.cloneWithSize(deck2TopCard)).getX();
        } else {
            winnerOfRound = deck1TopCard > deck2TopCard ? 0 : 1;
        }

        if (winnerOfRound == 0) {
            deck1.addCardToDeck(deck1TopCard);
            deck1.addCardToDeck(deck2TopCard);
        } else {
            deck2.addCardToDeck(deck2TopCard);
            deck2.addCardToDeck(deck1TopCard);
        }

        return new Tuple<>(deck1, deck2);
    }

    private int calculateScore(CardDeck deck) {
        int score = 0;
        Integer nextCard;

        while ((nextCard = deck.getFirstCard()) != null) {
            score += nextCard * (deck.getDeckSize() + 1);
        }

        return score;
    }

    private Tuple<Integer, Integer> playGame(CardDeck deck1, CardDeck deck2) {
        Set<CardDeck> alreadyPlayedCardDecksPlayer1 = new HashSet<>();
        Set<CardDeck> alreadyPlayedCardDecksPlayer2 = new HashSet<>();
        Tuple<CardDeck, CardDeck> playingDeck = new Tuple<>(deck1, deck2);

        while (!playingDeck.getX().isDeckEmpty() && !playingDeck.getY().isDeckEmpty()) {
            if (wasAlreadyPlayed(playingDeck.getX(), alreadyPlayedCardDecksPlayer1)
                    || wasAlreadyPlayed(playingDeck.getY(), alreadyPlayedCardDecksPlayer2)) {
                return new Tuple<>(0, calculateScore(playingDeck.getX()));
            } else {
                alreadyPlayedCardDecksPlayer1.add(deck1.clone());
                alreadyPlayedCardDecksPlayer2.add(deck2.clone());

                playingDeck = playRoundForTask2(deck1, deck2);
            }
        }

        if(playingDeck.getY().isDeckEmpty()) {
            return new Tuple<>(0, calculateScore(playingDeck.getX()));
        } else {
            return new Tuple<>(1, calculateScore(playingDeck.getY()));
        }
    }

    private boolean wasAlreadyPlayed(CardDeck deck, Set<CardDeck> alreadyPlayedDecks) {
        boolean alreadyPlayed = false;

        for (CardDeck playedDeck : alreadyPlayedDecks) {
            if (deck.equals(playedDeck)) {
                alreadyPlayed = true;
                break;
            }
        }

        return alreadyPlayed;
    }
}
