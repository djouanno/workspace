package com.esir.sr.sweetsnake.game.map;

import com.esir.sr.sweetsnake.api.IElement;

public class Board {

	private IElement[][]                            gameMap;
	private int height;
	private int width;
	
	public Board(final int height, final int width) {
		gameMap = new IElement[height][width];
		setHeight(height);
		setWidth(width);
	}
	
	public void set(final IElement element) {
		gameMap[element.getX()][element.getY()] = element;
	}
	
	public void remove(final IElement element) {
		gameMap[element.getX()][element.getY()] = null;
	}
	
	public IElement get(final int x, final int y) {
		return gameMap[x][y];
	}
	
	public IElement getById(final String id) {
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[i].length; j++) {
				if(get(i, j).getId() == id)
					return get(i, j);
			}
		}
		return null;
	}
	
	public boolean hasElement(final int x, final int y) {
		return get(x, y) != null;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
