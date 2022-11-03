package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine
{
	private List<Player> playerList;
	private Collection<GameEngineCallback> callbackList;
	private Deque<PlayingCard> deck;
	
	public GameEngineImpl() 
	{
		playerList = new ArrayList<Player>();
		callbackList = new ArrayList<GameEngineCallback>();
		deck = getShuffledHalfDeck();
	}
	
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		if (delay < 0 || delay > 1000)
			throw new IllegalArgumentException();
		
		while (true) 
		{
			PlayingCard card = deck.pop(); // Pops an element from the stack represented by this deque (ref from API)
			
			if (player.getResult() + card.getScore() > GameEngine.BUST_LEVEL) 
			{
				if (player.getResult() != GameEngine.BUST_LEVEL) 
					for (GameEngineCallback callback : callbackList) 
						callback.bustCard(player, card, this);
				break;
			} 
			else 
				for (GameEngineCallback callback : callbackList) 
					callback.nextCard(player, card, this);
			
			try 
			{
				Thread.sleep(delay);
			} 
			catch (InterruptedException e) // Constructs an InterruptedException with the specified detail message (ref from API)
			{
				// TODO Auto-generated catch block
				e.printStackTrace(); // Prints this throwable and its backtrace to the standard error stream (ref from API)
			}
			
			player.setResult(player.getResult() + card.getScore());
		}
		
		for (GameEngineCallback callback : callbackList) 
			callback.result(player, player.getResult(), this);
	}

	@Override
	public void dealHouse(int delay) throws IllegalArgumentException 
	{
		// TODO Auto-generated method stub
		if (delay < 0)
			throw new IllegalArgumentException();
		
		int result = 0;
		
		while (true) 
		{
			PlayingCard card = deck.pop(); // Pops an element from the stack represented by this deque (ref from API)
			
			if (result + card.getScore() > GameEngine.BUST_LEVEL) 
			{
				if (result != GameEngine.BUST_LEVEL) 
					for (GameEngineCallback callback : callbackList)
						callback.houseBustCard(card, this);
				
				break;
			} 
			else 
				for (GameEngineCallback callback : callbackList) 
					callback.nextHouseCard(card, this);
			
			try 
			{ 
				Thread.sleep(delay);
			} 
			catch (InterruptedException e) // Constructs an InterruptedException with the specified detail message (ref from API)
			{
				// TODO Auto-generated catch block
				e.printStackTrace(); // Prints this throwable and its backtrace to the standard error stream (ref from API)
			}
			
			result += card.getScore();
		}
		
		for (Player player : playerList) 
			applyWinLoss(player, result);
		
		for (GameEngineCallback callback : callbackList)
			callback.houseResult(result, this);
		
		for (Player player : playerList)
			player.resetBet();
	}

	@Override
	public void applyWinLoss(Player player, int houseResult) 
	{
		// TODO Auto-generated method stub
		int point = player.getPoints();
		int bet = player.getBet();
		
		if (player.getResult() > houseResult) 
			player.setPoints(point + bet);
		else if (player.getResult() == houseResult)
			player.setPoints(point);
		else
			player.setPoints(point - bet);
	}

	@Override
	public void addPlayer(Player player) 
	{
		// TODO Auto-generated method stub
		if (getPlayer(player.getPlayerId()) != null)
			removePlayer(player);
		
		playerList.add(player);
	}

	@Override
	public Player getPlayer(String id) 
	{
		// TODO Auto-generated method stub
		for (Player player : playerList) 
			if (player.getPlayerId().equals(id))
				return player;
		
		return null;
	}

	@Override
	public boolean removePlayer(Player player) 
	{
		// TODO Auto-generated method stub
		for (Player playerTemp : playerList)
			if (playerTemp.equals(player)) 
			{
				playerList.remove(playerTemp);
				
				return true;
			}
		
		return false;
	}

	@Override
	public boolean placeBet(Player player, int bet) 
	{
		// TODO Auto-generated method stub
		return player.setBet(bet);
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		// TODO Auto-generated method stub
		callbackList.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		// TODO Auto-generated method stub
		if (callbackList.contains(gameEngineCallback)) 
		{
			callbackList.remove(gameEngineCallback);
			
			return true;
		}
		
		return false;
	}

	@Override
	public Collection<Player> getAllPlayers()
	{	
		// TODO Auto-generated method stub
		Collections.sort(playerList);
		
		return playerList;
	}

	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() 
	{
		// TODO Auto-generated method stub
		
		List<PlayingCard> CardList = new ArrayList<PlayingCard>(PlayingCard.DECK_SIZE);
		
		for (Suit s : Suit.values())
			for (Value v : Value.values())
				CardList.add(new PlayingCardImpl(s, v));

		Collections.shuffle(CardList); // Randomly permutes the specified list using a default source of randomness (ref from API)
		
		Deque<PlayingCard> deque = new ArrayDeque<PlayingCard>(CardList);
		
		return deque;
	}
}