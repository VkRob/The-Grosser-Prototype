package grosser.prototype.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import grosser.prototype.entity.EntityMachine;
import grosser.prototype.entity.EntityWorker;
import grosser.prototype.map.Map;
import grosser.prototype.map.Tile;
import grosser.prototype.map.TilePosition;

/**
 * Manages rendering for basically everything. Whenever you create a new
 * renderable object, write its rendering code here. then when you need to
 * render the object just call the method in this class
 * 
 * @author Robert
 *
 */
public class Render {

	private Graphics2D g;

	// Draw Debug Outlines?
	public boolean debug = true;

	// Camera position
	private int cameraX = 0;
	private int cameraY = 0;

	public Render(Graphics2D g) {
		this.g = g;
	}

	/**
	 * Clears the screen
	 * 
	 * @param color
	 * @param width
	 * @param height
	 */
	public void clearScreen(Color color, int width, int height) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}

	/**
	 * grosser.prototype.render a gui panel
	 * 
	 * @param gui
	 */
	public void renderGui(GuiPanel gui) {
		for (GuiElement e : gui.getElements()) {
			if (e instanceof GuiText) {
				renderGuiText((GuiText) e);
			} else {
				renderGui(e);
			}
		}
	}

	/**
	 * grosser.prototype.render a gui element
	 * 
	 * @param gui
	 */
	public void renderGui(GuiElement gui) {
		if (debug) {
			g.drawRect(gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight());
		}
		if (gui.getImage() != null)
			g.drawImage(gui.getImage(), gui.getX(), gui.getY(), gui.getWidth(), gui.getHeight(), null);
	}

	/**
	 * grosser.prototype.render a gui text element
	 * 
	 * @param gui
	 */
	public void renderGuiText(GuiText gui) {
		g.setColor(gui.getColor());
		g.setFont(new Font(gui.getFont(), Font.PLAIN, gui.getFontSize()));
		g.drawString(gui.getText(), gui.getX(), gui.getY());
	}

	/**
	 * render the tiles in the world from the map object
	 * 
	 * @param map: map to render
	 */
	public void renderMap(Map map) {
		for (java.util.Map.Entry<TilePosition, Tile> entry : map.getTiles().entrySet()) {
			g.setColor(Color.WHITE);
			g.drawImage(Tile.tileset[entry.getValue().getID()], this.getRenderX(entry.getKey().getX() * (Tile.SIZE)),
					this.getRenderY(entry.getKey().getY() * (Tile.SIZE)), Tile.SIZE, Tile.SIZE, null);
		}
	}

	/**
	 * @param worker: EntityWorker to render
	 */
	public void renderWorker(EntityWorker worker) {
		g.drawImage(worker.getImg(), this.getRenderX(worker.getX()), this.getRenderY(worker.getY()), worker.getWidth(),
				worker.getHeight(), null);
	}

    /**
     * @param machine: EntityMachine to render
     */


    public void renderMachine(EntityMachine machine) {
        g.drawImage(machine.getImg(), this.getRenderX(machine.getX()), this.getRenderY(machine.getY()), machine.getWidth(),
                machine.getHeight(), null);
    }


	// Converts a position in the world to a position on screen (basically a
	// camera transform applier thing)

	private int getRenderX(int worldX) {
		return worldX + cameraX;
	}

	// Converts a position in the world to a position on screen (basically a
	// camera transform applier thing)
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
