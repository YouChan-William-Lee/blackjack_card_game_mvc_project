package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard 
{
	private Suit suit;
	private Value value;
	
	public PlayingCardImpl(Suit suit, Value value) 
	{
		this.suit = suit;
		this.value = value;
	}
	
	@Override
	public Suit getSuit() 
	{
		// TODO Auto-generated method stub
		return suit;
	}

	@Override
	public Value getValue() 
	{
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public int getScore() 
	{
		// TODO Auto-generated method stub
		switch (value)
		{
			case EIGHT: return 8;
			case NINE: return 9;
			case ACE: return 11;
			default: return 10; //TEN, JACK, QUEEN, KING
		}
	}

	@Override
	public String toString()
	{
		return String.format("Suit: %s, Value: %s, Score: %d", formatting(getSuit().toString()),
				formatting(getValue().toString()), getScore());
	}
	
	@Override
	public boolean equals(PlayingCard card) 
	{
		// TODO Auto-generated method stub
		return this.suit == card.getSuit() && this.value == card.getValue();
	}
	
	@Override
	public boolean equals(Object card)
	{
		if (card == null || card.getClass() != getClass())
			return false;
		
		return this.suit == ((PlayingCard)card).getSuit() && this.value == ((PlayingCard)card).getValue();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hashCode = 1;
		
		hashCode = prime * hashCode + suit.hashCode();
		hashCode = prime * hashCode + value.hashCode();
		
		return hashCode;
	} 
	
	private String formatting(String value) // Human readable String ex) DIAMONDS -> Diamonds 
	{
		return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
	}
}
