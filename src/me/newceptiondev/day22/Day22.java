package me.newceptiondev.day22;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Day22 {

  private static final String FILE_NAME = "day22Task1Input";

  public static void main(String[] args) {
    List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

    new Day22(inputs);
  }

  public Day22() {
  }

  private Day22(List<String> inputs) {
    System.out.println("Task 1: " + task1(inputs));
    System.out.println("Task 2: " + task2(inputs));
  }

  /**
   * Task 1
   *
   * @param inputs List of String
   *
   * @return Winning Score
   */
  public int task1(final List<String> inputs) {
    Tuple<CardDeck, CardDeck> decks = parseCardDecks(inputs);

    while(!decks.getX().isDeckEmpty() && !decks.getY().isDeckEmpty()) {
      playRoundForTask1(decks.getX(), decks.getY());
    }

    if(decks.getX().isDeckEmpty()) {
      return calculateScore(decks.getY());
    } else {
      return calculateScore(decks.getX());
    }
  }

  /**
   * Task 2
   *
   * @param inputs List of String
   *
   * @return Winning Score
   */
  public int task2(final List<String> inputs) {
    Tuple<CardDeck, CardDeck> decks = parseCardDecks(inputs);

    return playGame(decks.getX(), decks.getY()).getY();
  }

  /**
   * Parses the CardDecks from a List of String
   *
   * @param inputs List of String
   *
   * @return Tuple with CardDeck of first Player and CardDeck of second Player
   */
  private Tuple<CardDeck, CardDeck> parseCardDecks(List<String> inputs) {
    boolean firstDeck = true;
    Tuple<CardDeck, CardDeck> decks = new Tuple<>(new CardDeck(), new CardDeck());

    for(String input : inputs) {
      if(input.isEmpty()) {
        firstDeck = false;
      } else if(!input.startsWith("Player")) {
        if(firstDeck) {
          decks.getX().addCardToDeck(Integer.parseInt(input));
        } else {
          decks.getY().addCardToDeck(Integer.parseInt(input));
        }
      }
    }

    return decks;
  }

  /**
   * Simulates a Round with the Requirements of Task 1
   *
   * @param deck1 CardDeck of first Player
   * @param deck2 CardDeck of second Player
   */
  private void playRoundForTask1(CardDeck deck1, CardDeck deck2) {
    Integer deck1TopCard = deck1.getFirstCard();
    Integer deck2TopCard = deck2.getFirstCard();

    if(deck1TopCard > deck2TopCard) {
      deck1.addCardToDeck(deck1TopCard);
      deck1.addCardToDeck(deck2TopCard);
    } else {
      deck2.addCardToDeck(deck2TopCard);
      deck2.addCardToDeck(deck1TopCard);
    }
  }

  /**
   * Simulates a Round with the Requirements of Task 2
   *
   * @param deck1 CardDeck of first Player
   * @param deck2 CardDeck of second Player
   *
   * @return Tuple with CardDeck of first Player and CardDeck of second Player
   */
  private Tuple<CardDeck, CardDeck> playRoundForTask2(CardDeck deck1, CardDeck deck2) {

    Integer deck1TopCard = deck1.getFirstCard();
    Integer deck2TopCard = deck2.getFirstCard();

    int winnerOfRound;

    if(deck1.getDeckSize() >= deck1TopCard && deck2.getDeckSize() >= deck2TopCard) {
      winnerOfRound = playGame(deck1.cloneWithSize(deck1TopCard), deck2.cloneWithSize(deck2TopCard)).getX();
    } else {
      winnerOfRound = deck1TopCard > deck2TopCard ? 0 : 1;
    }

    if(winnerOfRound == 0) {
      deck1.addCardToDeck(deck1TopCard);
      deck1.addCardToDeck(deck2TopCard);
    } else {
      deck2.addCardToDeck(deck2TopCard);
      deck2.addCardToDeck(deck1TopCard);
    }

    return new Tuple<>(deck1, deck2);
  }

  /**
   * Calculates the Score of the given CardDeck
   *
   * @param deck CardDeck
   *
   * @return Score
   */
  private int calculateScore(CardDeck deck) {
    int score = 0;
    Integer nextCard = deck.getFirstCard();

    while(nextCard != null) {
      score += nextCard * (deck.getDeckSize() + 1);
      nextCard = deck.getFirstCard();
    }

    return score;
  }

  /**
   * Simulates a Game
   *
   * @param deck1 Deck of first Player
   * @param deck2 Deck of second Player
   *
   * @return Tuple of Winner (0 for first Player, 1 for second Player) and Winning Score
   */
  private Tuple<Integer, Integer> playGame(CardDeck deck1, CardDeck deck2) {
    Set<CardDeck> alreadyPlayedCardDecksPlayer1 = new HashSet<>();
    Set<CardDeck> alreadyPlayedCardDecksPlayer2 = new HashSet<>();
    Tuple<CardDeck, CardDeck> playingDeck = new Tuple<>(deck1, deck2);

    while(!playingDeck.getX().isDeckEmpty() && !playingDeck.getY().isDeckEmpty()) {
      if(wasAlreadyPlayed(playingDeck.getX(), alreadyPlayedCardDecksPlayer1) ||
         wasAlreadyPlayed(playingDeck.getY(), alreadyPlayedCardDecksPlayer2)) {
        return new Tuple<>(0, calculateScore(playingDeck.getX()));
      } else {
        alreadyPlayedCardDecksPlayer1.add(deck1.cloneCardDeck());
        alreadyPlayedCardDecksPlayer2.add(deck2.cloneCardDeck());

        playingDeck = playRoundForTask2(deck1, deck2);
      }
    }

    if(playingDeck.getY().isDeckEmpty()) {
      return new Tuple<>(0, calculateScore(playingDeck.getX()));
    } else {
      return new Tuple<>(1, calculateScore(playingDeck.getY()));
    }
  }

  /**
   * Checks the CardDeck was already played in this Game
   *
   * @param deck               CardDeck
   * @param alreadyPlayedDecks Set of already played CardDecks
   *
   * @return True if CardDeck was already played
   */
  private boolean wasAlreadyPlayed(CardDeck deck, Set<CardDeck> alreadyPlayedDecks) {
    boolean alreadyPlayed = false;

    Iterator<CardDeck> cardDeckIterator = alreadyPlayedDecks.iterator();

    while(cardDeckIterator.hasNext() && !alreadyPlayed) {
      if(deck.equals(cardDeckIterator.next())) {
        alreadyPlayed = true;
      }
    }

    return alreadyPlayed;
  }
}
