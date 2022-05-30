import view.MainView;

public class MainInterface {
	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainView mw = new MainView();
            	mw.setVisible(true);
            }
        });
        /*
		MainView mw = new MainView();
		mw.setVisible(true);
		*/
	}
}
