package fr.aytrexois.launcher;

import java.text.DecimalFormat;
import java.util.HashMap;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPanel extends IScreen {
	public HashMap<String, LauncherTextField> user;
	Main main;
	public LauncherPanel(Main main) {
		this.main = main;
	}
	private LauncherRectangle topRectangle;
	public LauncherTextField usernameField;
	public LauncherTextField passwordField;
	
	public LauncherButton loginButton;
	public LauncherButton settingsButton;
	private LauncherButton quitButton;
	private LauncherButton hideButton;
	
	private LauncherButton discordButton;
	private LauncherButton teamspeakButton;
	private LauncherButton siteButton;
	//private LauncherButton admin;
	
	public Timeline timeLine;
	public DecimalFormat decimalFormat = new DecimalFormat(".#");
	public Thread updateThread;
	public GameUpdater gameUpdater = new GameUpdater();
	private LauncherRectangle updateRectangle;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	
	private GameEngine theGameEngine;
	
	public LauncherConfig config;
	GameEngine gameEngine2;
	public LauncherPanel(Pane contentPane, GameEngine gameEngine) {
		gameEngine2 = gameEngine;
		user = new HashMap<String, LauncherTextField>();
		this.theGameEngine = gameEngine;
		this.config = new LauncherConfig(gameEngine);
		this.config.loadConfiguration();
		
		this.drawBackgroundImage(gameEngine, contentPane, "background.png");
		
		this.drawLogo(gameEngine, getResourceLocation().loadImage(gameEngine, "logo.png"), gameEngine.getWidth() / 2, 140, 100, 100, contentPane, Mover.MOVE);
		
		this.topRectangle = new LauncherRectangle(contentPane, 0, 0, gameEngine.getWidth(), 42);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.2));
		
		/**this.admin = new LauncherButton(contentPane);
		LauncherImage adminimage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "close.png"));
		adminimage.setSize(35, 35);
		this.admin.setGraphic(adminimage);
		this.admin.setPosition(gameEngine.getHeight() / 2+ 730, gameEngine.getWidth() / 2 + 109);*/
		
		/*this.admin = new LauncherButton(contentPane);
		this.admin.setInvisible();
		LauncherImage adminimage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "admin.png"));
		adminimage.setSize(35, 35);
		this.admin.setGraphic(adminimage);
		this.admin.setPosition(gameEngine.getHeight() / 2 + 730, gameEngine.getWidth() / 2 + 109);
		
		this.admin.setOnAction(event -> {
			
				//user.put("user", usernameField);
				Scene scene = new Scene(createLogin());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(500);
				stage.setHeight(300);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
				
			
		});*/
		
		this.usernameField = new LauncherTextField(contentPane);
		this.usernameField.setPosition(gameEngine.getWidth() / 2 - 120, gameEngine.getHeight() / 2 - 57);
		this.usernameField.setSize(330, 50);
		this.usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white;");
		this.usernameField.setFont(FontLoader.loadFont("lovely-script.otf", "Lovely Script", 18f));
		this.usernameField.setVoidText("Pseudonime");
		
		this.passwordField = new LauncherTextField(contentPane);
		this.passwordField.setPosition(gameEngine.getWidth() / 2 - 120, gameEngine.getHeight() / 2 + 10);
		this.passwordField.setSize(330, 50);
		this.passwordField.setFont(FontLoader.loadFont("lovely-script.otf", "Lovely Script", 18f));
		this.passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white;");
		this.passwordField.setVoidText("Mod de passe");
		
		this.loginButton = new LauncherButton(contentPane);
		this.loginButton.setInvisible();
		LauncherImage loginImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "play.png"));
		loginImage.setSize(110, 110);
		this.loginButton.setGraphic(loginImage);
		this.loginButton.setPosition(gameEngine.getWidth() / 2 - 30, gameEngine.getHeight() / 2 + 80);
		
		this.loginButton.setOnAction(event -> {
			
				//user.put("user", usernameField);
				Scene scene = new Scene(createChoice(usernameField, passwordField, settingsButton, loginButton, gameUpdater, updateThread, timeLine, decimalFormat));
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(500);
				stage.setHeight(300);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
				
			
		});
		
		this.settingsButton = new LauncherButton(contentPane);
		this.settingsButton.setInvisible();
		LauncherImage settingsImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "options.png"));
		settingsImage.setSize(36, 36);
		this.settingsButton.setGraphic(settingsImage);
		this.settingsButton.setPosition(gameEngine.getWidth() / 2 + 380, gameEngine.getHeight() / 2 - 362);
		this.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				Scene scene = new Scene(createSettingsPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(500);
				stage.setHeight(300);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
				
			}
		});
		
		this.quitButton = new LauncherButton(contentPane);
		this.quitButton.setInvisible();
		LauncherImage quitImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "close.png"));
		quitImage.setSize(35, 35);
		this.quitButton.setGraphic(quitImage);
		this.quitButton.setPosition(gameEngine.getWidth() / 2 + 500, gameEngine.getHeight() / 2 - 362);
		this.quitButton.setBackground(null);
		this.quitButton.setOnAction(event ->{
			System.exit(0);
		});
		
		this.hideButton = new LauncherButton(contentPane);
		this.hideButton.setInvisible();
		LauncherImage hideImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "hide.png"));
		hideImage.setSize(66, 100);
		this.hideButton.setGraphic(hideImage);
		this.hideButton.setPosition(gameEngine.getWidth() / 2 + 422, gameEngine.getHeight() / 2 - 420);
		this.hideButton.setBackground(null);
		this.hideButton.setOnAction(event ->{
			Stage stage = (Stage)((LauncherButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		});
		
		this.discordButton = new LauncherButton(contentPane);
		this.discordButton.setInvisible();
		LauncherImage discordImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "discord.png"));
		discordImage.setSize((int)(32 * 2.5), (int)(37 * 2.5));
		this.discordButton.setGraphic(discordImage);
		this.discordButton.setPosition(gameEngine.getWidth() / 2 - 550, gameEngine.getHeight() / 2 + 220);
		this.discordButton.setBackground(null);
		this.discordButton.setOnAction(event ->{
			openLink("https://discord.gg/pQRv9QV");
		});
		
		this.teamspeakButton = new LauncherButton(contentPane);
		this.teamspeakButton.setInvisible();
		LauncherImage teamspeak = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "teamspeak.png"));
		teamspeak.setSize((int)(35 * 2.5), (int)(35 * 2.5));
		this.teamspeakButton.setGraphic(teamspeak);
		this.teamspeakButton.setPosition(gameEngine.getWidth() / 2 - 550, gameEngine.getHeight() / 2 - 10);
		this.teamspeakButton.setBackground(null);
		this.teamspeakButton.setOnAction(event ->{
			new LauncherAlert("En construction.", "");
			//openLink("http://www.ts3server.com//ts.pladrinset.fr?port=10434");
		});
		
		this.siteButton = new LauncherButton(contentPane);
		this.siteButton.setInvisible();
		LauncherImage siteImage = new LauncherImage(contentPane, getResourceLocation().loadImage(gameEngine, "web.png"));
		siteImage.setSize((int)(35 * 2.5), (int)(35 * 2.5));
		this.siteButton.setGraphic(siteImage);
		this.siteButton.setPosition(gameEngine.getWidth() / 2 - 550, gameEngine.getHeight() / 2 + 100);
		this.siteButton.setBackground(null);
		this.siteButton.setOnAction(event ->{
			openLink("http://www.pladrinset.fr/");
		});
		
		this.updateRectangle = new LauncherRectangle(contentPane, gameEngine.getWidth() / 2 - 260, gameEngine.getHeight() / 2 - 60, 600, 200);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.40));
		this.updateRectangle.setVisible(false);
		
		this.currentStep = new LauncherLabel(contentPane);
		this.currentStep.setText("Preparation de la Mise à Jour...");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setFont(FontLoader.loadFont("Cream Cake.ttf", "Cream Cake", 36f));
		this.currentStep.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white;");
		this.currentStep.setPosition(gameEngine.getWidth() / 2 - 230, gameEngine.getHeight() / 2 + 83);
		this.currentStep.setOpacity(0.8);
		this.currentStep.setSize(540, 40);
		this.currentStep.setVisible(false);
		
		this.percentageLabel = new LauncherLabel(contentPane);
		this.percentageLabel.setText("0%");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-text-fill: white;");
		this.percentageLabel.setPosition(gameEngine.getWidth() / 2 - 65, gameEngine.getHeight() / 2);
		this.percentageLabel.setSize(200, 40);
		this.percentageLabel.setVisible(false);
		
	}
	
	public void update(GameEngine gameEngine, GameAuth auth) {
		
		this.usernameField.setDisable(true);
		this.usernameField.setVisible(false);
		this.loginButton.setDisable(true);
		this.loginButton.setVisible(false);
		this.settingsButton.setDisable(true);
		this.settingsButton.setVisible(false);
		this.passwordField.setDisable(true);
		this.passwordField.setVisible(false);
		
		this.updateRectangle.setVisible(true);
		this.currentStep.setVisible(true);
		this.percentageLabel.setVisible(true);
		
		gameUpdater.reg(gameEngine);
		gameUpdater.reg(auth.getSession());
		gameEngine.reg(gameUpdater);
		this.updateThread = new Thread() {
			public void run() {
				gameEngine.getGameUpdater().run();
				if(gameEngine.getGameUpdater().isInterrupted()) {
					new LauncherAlert("Connexion echouee !", "La connexion a echouee, relancez votre launcher !");
				}
			}
		};
		this.updateThread.start();
		
		this.timeLine = new Timeline(new KeyFrame[] {
				new KeyFrame(javafx.util.Duration.seconds(0.0D), e -> updateDownload(gameEngine),
				new javafx.animation.KeyValue[0]),
                new KeyFrame(javafx.util.Duration.seconds(0.1D), 
                new javafx.animation.KeyValue[0])
		});
		this.timeLine.setCycleCount(Animation.INDEFINITE);
		this.timeLine.play();
	}

	private void updateDownload(GameEngine gameEngine) {
		if(gameEngine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(gameEngine.getGameUpdater().downloadedFiles * 100.0D / gameEngine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentStep.setText(gameEngine.getGameUpdater().getCurrentInfo());
	}

	private Parent createSettingsPanel() {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		Rectangle rect = new Rectangle(500, 300);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherSettings(contentPane, theGameEngine, this);
		return contentPane;
	}
	/*private Parent createLogin() {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		Rectangle rect = new Rectangle(500, 300);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new Login(contentPane, theGameEngine, this);
		return contentPane;
	}*/
	private Parent createChoice(final LauncherTextField usernameField2, final LauncherTextField passwordField2,LauncherButton settingsButton, LauncherButton loginButton, GameUpdater gameUpdater, 
			Thread updateThread, Timeline timeline, DecimalFormat decimalFormat) {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		Rectangle rect = new Rectangle(500, 300);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new Choice(contentPane, theGameEngine, contentPane, usernameField2, passwordField2, settingsButton, loginButton, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
		return contentPane;
		
	}
	public void vanilla() {
		System.out.println("test");
	}
}
