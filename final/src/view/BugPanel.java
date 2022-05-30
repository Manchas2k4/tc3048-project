package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class BugPanel extends JPanel {
	private static final long serialVersionUID = -8326718778965200334L;
	
	private double initialX, currentX, initialY, currentY, angle;
	private boolean pen;
	private Color color;
	private BufferedImage background, bugImages[], currentImage;
	private Graphics graphics;
	
	public BugPanel(int width, int height) {
		super();
		
		//double width = this.getPreferredSize().getWidth();
		//double height = this.getPreferredSize().getHeight();
		
		setSize(width, height);
		
		currentX = initialX = (width / 2);
		currentY = initialY = (height / 2);
		angle = 0.0;
		pen = true;
		color = Color.black;
		
		background = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
		graphics = background.createGraphics();
		bugImages = new BufferedImage[37];
		for (int i = 0; i < bugImages.length; i++) {
			try {
				bugImages[i] = ImageIO.read(new File("images/bug" + i + ".gif"));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		currentImage = bugImages[0];
		clear();
	}
	
	public double getCurrentX() {
		return currentX;
	}
	
	public double getCurrentY() {
		return currentY;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public boolean getPen() {
		return pen;
	}
	
	public void penUp() {
		pen = false;
	}
	
	public void penDown() {
		pen = true;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void init() {
		if (pen) {
			graphics.setColor(color);
			graphics.drawLine((int) currentX, (int) currentY, (int) initialX, (int) initialY);
		}
		currentX = initialX;
		currentY = initialY;
		System.out.println("init, currentX = " + currentX + " currentY = " + currentY);
	}
	
	public void set(int xx, int yy) {
		if (pen) {
			graphics.setColor(color);
			graphics.drawLine((int) currentX, (int) currentY, xx, yy);
		}
		currentX = xx;
		currentY = yy;
		System.out.println("set, currentX = " + currentX + " currentY = " + currentY);
	}
	
	public void right(int ang) {
		int aux;
		
		angle = angle + ang;
		aux = (((int) angle) % 360) / 10;
		currentImage = bugImages[aux];
		
		System.out.println("right = " + angle +  " currentX = " + currentX + " currentY = " + currentY);
	}
	
	public void left(int ang) {
		int aux;
		
		ang = ang % 360;
		angle = angle - ang;
		while (angle < 0) {
			angle = 360 - angle;
		}
		
		aux = (((int) angle) % 360) / 10;
		currentImage = bugImages[aux];
		System.out.println("left = " + angle +  " currentX = " + currentX + " currentY = " + currentY);
	}
	
	public void move(int dist) {
		double auxX, auxY;
		
		auxX = Math.sin(Math.toRadians(angle)) * dist;
		if (Math.abs(auxX) < 0.5) {
			auxX = 0;
		}
		auxY = Math.cos(Math.toRadians(angle - 180)) * dist;
		if (Math.abs(auxY) < 0.5) {
			auxY = 0;
		}
		if (pen) {
			graphics.setColor(color);
			graphics.drawLine((int) currentX, (int) currentY, (int) (currentX + auxX), (int) (currentY + auxY));
		}
		currentX += auxX;
		currentY += auxY;
		System.out.println("advance, currentX = " + currentX + " currentY = " + currentY);
	}
	
	public void circle(int radius) {
		// TO DO
	}
	
	public void arc(int ang, int radius) {
		//TO DO
	}
	
	public void changeColor(int rr, int gg, int bb) {
		color = new Color(rr % 256, gg % 256, bb % 256);
	}
	
	public void clear() {
		graphics.setColor(Color.white);
		graphics.fillRect(0,  0, getWidth(), getHeight());
		graphics.setColor(color);
		System.out.println("clear, currentX = " + currentX + " currentY = " + currentY);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        /*
         * int x = (getWidth() - currentImage.getWidth()) / 2;
         * int y = (getHeight() - currentImage.getHeight()) / 2;
         */
        int x = ((int) currentX) - (currentImage.getWidth() / 2);
        int y = ((int) currentY) - (currentImage.getHeight() / 2);
        g2d.drawImage(background, 0, 0, this);
        g2d.drawImage(currentImage, x, y, this);
        g2d.dispose();
    }
}
