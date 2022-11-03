package model;

import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class SimplePlayer implements Player 
{
	private String id;
	private String name;
	private int point;
	private int bet;
	private int result;
	
	public SimplePlayer(String id, String name, int point) 
	{
		this.id = id;
		this.name = name;
		this.point = point;
	}

	@Override
	public String getPlayerName() 
	{
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setPlayerName(String playerName) 
	{
		// TODO Auto-generated method stub
		this.name = playerName;
	}

	@Override
	public int getPoints() 
	{
		// TODO Auto-generated method stub
		return point;
	}

	@Override
	public void setPoints(int points) 
	{
		// TODO Auto-generated method stub
		this.point = points;
	}

	@Override
	public String getPlayerId() 
	{
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean setBet(int bet) 
	{
		// TODO Auto-generated method stub
		if (point < bet)
			return false;
		this.bet = bet;
		return true;
	}

	@Override
	public int getBet() 
	{
		// TODO Auto-generated method stub
		return bet;
	}

	@Override
	public void resetBet() 
	{
		// TODO Auto-generated method stub
		bet = 0;
	}

	@Override
	public int getResult() 
	{
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public void setResult(int result) 
	{
		// TODO Auto-generated method stub
		this.result = result;
	}

	@Override
	public boolean equals(Player player) 
	{
		// TODO Auto-generated method stub
		return this.id.equals(player.getPlayerId());
	}
	
	@Override 
	public boolean equals(Object player)
	{
		if (player == null || player.getClass() != getClass())
			return false;
		
		return this.id.equals(((Player)player).getPlayerId());
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hashCode = 1;
		
		hashCode = prime * hashCode + ((id == null) ? 0 : id.hashCode());
		
		return hashCode;
	}
	
	@Override
	public int compareTo(Player player) 
	{
		// TODO Auto-generated method stub
		return this.getPlayerId().compareTo(player.getPlayerId());
	}
	
	@Override
	public String toString()
	{
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d\n", 
				getPlayerId(), getPlayerName(), getBet(), getPoints(), getResult());
	}
}
