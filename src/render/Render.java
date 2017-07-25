package render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.EntityMachine;
import entity.EntityWorker;
import map.Map;
import map.Tile;

public class Render {
	private Graphics2D g;
	public boolean debug = true;

	private int cameraX = 0;
	private int cameraY = 0;

	public Render(Graphics2D g, BufferedImage img) {
		this.g = g;
	}

	public void clearScreen(Color c, int width, int height) {
		g.setColor(c);
		g.fillRect(0, 0, width, height);
	}

	public void renderGui(GuiPanel gui) {
		for (GuiElement e : gui.getElements()) {
			if (e instanceof GuiText) {
				renderGuiText((GuiText) e);
			} else {
				renderGui(e);
			}
		}
	}

	public void renderGui(GuiElement gui) {
		if (debug) {
			g.drawRect(gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight());
		}
		if (gui.getImage() != null)
			g.drawImage(gui.getImage(), gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight(), null);
	}

	public void renderGuiText(GuiText gui) {
		g.setColor(gui.getColor());
		g.setFont(new Font(gui.getFont(), Font.PLAIN, gui.getFontSize()));
		g.drawString(gui.getText(), gui.getX(), gui.getY());
	}

	public void renderMap(Map map) {
		for (Tile t : map.getTiles()) {
			g.setColor(Color.WHITE);
			g.drawImage(Tile.tileset[t.getID()], this.getRenderX(t.getX() * (Tile.SIZE)),
					this.getRenderY(t.getY() * (Tile.SIZE)), Tile.SIZE, Tile.SIZE, null);
		}
	}

	public void renderWorker(EntityWorker worker) {
		g.drawImage(worker.getImg(), this.getRenderX(worker.getX()), this.getRenderY(worker.getY()), worker.getWidth(),
				worker.getHeight(), null);
	}

    public void renderMachine(EntityMachine machine) {
        g.drawImage(machine.getImg(), this.getRenderX(machine.getX()), this.getRenderY(machine.getY()), machine.getWidth(),
                machine.getHeight(), null);
    }

	private int getRenderX(int worldX) {
		return worldX + cameraX;
	}

	private int getRenderY(int worldY) {
		return worldY + cameraY;
	}

	public int getCameraX() {
		return cameraX;
	}

	public void setCameraX(int cameraX) {
		this.cameraX = cameraX;
	}

	public int getCameraY() {
		return cameraY;
	}

	public void setCameraY(int cameraY) {
		this.cameraY = cameraY;
	}

}
