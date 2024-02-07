import java.awt.*;

public class MyColor extends Frame {
	public static void main(String[] args) {
		new MyColor();
	}
	public MyColor() {
		setBounds(300, 200, 255, 255);
		setVisible(true);
	}
	public void paint(Graphics g) {
		for(int x = 0;x <= 255;x++) {
			for(int y = 0;y <= 0;y++) {
				//g.setColor(new Color(x, y, 255));
				g.setColor(Color.BLACK);
				g.drawRect(x, y, 20, 20);
				System.out.println("DRAW");
			}
		}
	}
}