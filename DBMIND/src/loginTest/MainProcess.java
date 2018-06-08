package loginTest;

import main.Main_form;

public class MainProcess {
	LoginView loginView;
	Main_form main_form;
	
	

	public static void main(String[] args) {

		MainProcess main = new MainProcess();
		main.loginView = new LoginView();
		main.loginView.setMain(main);
	}

	public void showFrameTest() {
		loginView.dispose();
		this.main_form = new Main_form();
	}
}
