package fr.aytrexois.launcher;

import java.io.IOException;
import java.text.DecimalFormat;

import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.northenflo.auth.mineweb.AuthMineweb;
import fr.northenflo.auth.mineweb.utils.TypeConnection;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
	
public class Choice extends IScreen{
	LauncherPanel launcherPanel;
	public Choice(LauncherPanel launcherPanel) {
		this.launcherPanel = launcherPanel;
	}
	Main main;
	public Choice(Main main) {
		this.main = main;
	}
	private LauncherRectangle topRectangle;
	private LauncherLabel titleLabel;
	private LauncherLabel titleLabel2;
	private LauncherButton vanillaButton;
	private LauncherButton quitButton;
	
	public Choice(final Pane root, final GameEngine engine, final Pane pane, final LauncherTextField usernameField, final LauncherTextField passwordField, LauncherButton settingsButton, LauncherButton loginButton, GameUpdater gameUpdater, 
			Thread updateThread, Timeline timeline, DecimalFormat decimalFormat, LauncherRectangle updateRectangle, LauncherLabel currentStep, LauncherLabel percentageLabel) {
		this.drawBackgroundImage(engine, root, "background-2.png");
		
		
		
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 500, 40);
		this.topRectangle.setOpacity(0.7);
		
		this.quitButton = new LauncherButton(pane);
		this.quitButton.setInvisible();
		LauncherImage quitImage = new LauncherImage(pane, getResourceLocation().loadImage(engine, "close.png"));
		quitImage.setSize(30, 30);
		this.quitButton.setGraphic(quitImage);
		this.quitButton.setPosition(450, 0);
		this.quitButton.setBackground(null);
		this.quitButton.setOnAction(event ->{
			Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
			stage.close();
		});
		
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("choisissez votre");
		this.titleLabel.setStyle("-fx-text-fill: black;");
		this.titleLabel.setFont(FontLoader.loadFont("Cream Cake.ttf", "Cream Cake", 50f));
		this.titleLabel.setPosition(150, 50);
		this.titleLabel.setSize(230, 35);
		
		this.titleLabel2 = new LauncherLabel(root);
		this.titleLabel2.setText("     serveur");
		this.titleLabel2.setStyle("-fx-text-fill: black;");
		this.titleLabel2.setFont(FontLoader.loadFont("Cream Cake.ttf", "Cream Cake", 50f));
		this.titleLabel2.setPosition(150, 82);
		this.titleLabel2.setSize(230, 35);
		
		this.vanillaButton = new LauncherButton(root);
		this.vanillaButton.setText("Pladrinset");
		this.vanillaButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.vanillaButton.setFont(FontLoader.loadFont("lovely-script.otf", "lovely script", 22f));
		this.vanillaButton.setPosition(260, 150);
		this.vanillaButton.setSize(200, 50);
		this.vanillaButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				
				// MineWeb
				System.out.println(usernameField.getText() +" "+ passwordField.getText());
				
				AuthMineweb.setTypeConnection(TypeConnection.launcher);
				AuthMineweb.setUrlRoot("http://www.pladrinset.fr");
				AuthMineweb.setUsername(usernameField.getText());
				AuthMineweb.setPassword(passwordField.getText());
				try {
				AuthMineweb.start();
				} catch (DataWrongException e) {
				e.printStackTrace();
				return;
				} catch (DataEmptyException e) {
				e.printStackTrace();
				return;
				} catch (ServerNotFoundException e) {
				e.printStackTrace();
				return;
				} catch (IOException e) {
				e.printStackTrace();
				return;
				}if(AuthMineweb.isConnected()) {
					Thread t = new Thread() {
						@Override
						public void run(){
							GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
				            if (auth.isLogged()) {
				                update(engine, auth, usernameField, loginButton, settingsButton, passwordField, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
				                
				            }
						}
					};t.start();
				}else {
					new LauncherAlert("Identifiant faux", "Merci de vous inscrire sur notre site internet et de vous connecter avec les même identifiant: http://www.pladrinset.fr");
				}
				
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
				
				//
				
				
				
				/*System.out.println(usernameField.getText().length());
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
				GameFolder gameFolder = new GameFolder(References.GAME_FOLDER_PATH);
		        LauncherPreferences launcherPreferences = new LauncherPreferences(References.LAUNCHER_NAME, References.SIZE_LAUNCHER_X, References.SIZE_LAUNCHER_Z, References.CAN_MOOVE_LAUNCHER);
		        GameLinks gameLink = new GameLinks(References.FILES_LINK, References.JSON_VERSION);

		        GameEngine engine = new GameEngine(gameFolder, gameLink, launcherPreferences, References.GAMESTYLE);
		        if (usernameField.getText().length() < 3) {
		            new LauncherAlert("Authentification echouee",
		                    "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
		        } else if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
		            GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
		            if (auth.isLogged()) {
		                update(engine, auth, usernameField, loginButton, settingsButton, passwordField, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
		                
		            }
		        }
		        else if (usernameField.getText().length() < 3) {
		        	 new LauncherAlert("Authentification echouee",
			                    "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
		        }else {
		        	 if(usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
				        	
				        	GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
				                    AccountType.MOJANG);
				            if (auth.isLogged()) {
				            	update(engine, auth, usernameField, loginButton, settingsButton, passwordField, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
				            } else {
				                new LauncherAlert("Authentification echouee!", "Impossible de se connecter");
				            }
				        
			        }
		        }*/
		       
			}
		
		});
		
		
		
		this.vanillaButton = new LauncherButton(root);
		this.vanillaButton.setText("TM Mod's");
		this.vanillaButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.vanillaButton.setFont(FontLoader.loadFont("lovely-script.otf", "lovely script", 22f));
		this.vanillaButton.setPosition(50, 150);
		this.vanillaButton.setSize(200, 50);
		this.vanillaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
				GameFolder gameFolder = new GameFolder(References2.GAME_FOLDER_PATH);
		        LauncherPreferences launcherPreferences = new LauncherPreferences(References2.LAUNCHER_NAME, References2.SIZE_LAUNCHER_X, References2.SIZE_LAUNCHER_Z, References2.CAN_MOOVE_LAUNCHER);
		        GameLinks gameLink = new GameLinks(References2.FILES_LINK, References2.JSON_VERSION);

		        GameEngine engine = new GameEngine(gameFolder, gameLink, launcherPreferences, References2.GAMESTYLE);
		        if (usernameField.getText().length() < 3) {
		            new LauncherAlert("Authentification echouee",
		                    "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
		        } else if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
		            GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
		            if (auth.isLogged()) {
		                update(engine, auth, usernameField, loginButton, settingsButton, passwordField, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
		                
		            }
		        }
		        else if (usernameField.getText().length() < 3) {
		        	 new LauncherAlert("Authentification echouee",
			                    "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
		        }else {
		        	 if(usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
				        	
				        	GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
				                    AccountType.MOJANG);
				            if (auth.isLogged()) {
				            	update(engine, auth, usernameField, loginButton, settingsButton, passwordField, gameUpdater, updateThread, timeline, decimalFormat, updateRectangle, currentStep, percentageLabel);
				            } else {
				                new LauncherAlert("Authentification echouee!", "Impossible de se connecter");
				            }
				        
			        }
		        }
		       
			}
		
		});
		
	}
	public void update(GameEngine gameEngine, GameAuth auth, LauncherTextField usernameField, LauncherButton loginButton, LauncherButton settingsButton, LauncherTextField passwordField, GameUpdater gameUpdater, Thread updateThread,
			Timeline timeLine, DecimalFormat decimalFormat, LauncherRectangle updateRectangle, LauncherLabel currentStep, LauncherLabel percentageLabel) {
		
		usernameField.setDisable(true);
		usernameField.setVisible(false);
		loginButton.setDisable(true);
		loginButton.setVisible(false);
		settingsButton.setDisable(true);
		settingsButton.setVisible(false);
		passwordField.setDisable(true);
		passwordField.setVisible(false);
		
		updateRectangle.setVisible(true);
		currentStep.setVisible(true);
		percentageLabel.setVisible(true);
		
		gameUpdater.reg(gameEngine);
		gameUpdater.reg(auth.getSession());
		gameEngine.reg(gameUpdater);
		updateThread = new Thread() {
			public void run() {
				gameEngine.getGameUpdater().run();
				if(gameEngine.getGameUpdater().isInterrupted()) {
					new LauncherAlert("Connexion echouee !", "La connexion a echouee, relancez votre launcher !");
				}
			}
		};
		updateThread.start();
		
		timeLine = new Timeline(new KeyFrame[] {
				new KeyFrame(javafx.util.Duration.seconds(0.0D), e -> updateDownload(gameEngine, decimalFormat, percentageLabel, currentStep),
				new javafx.animation.KeyValue[0]),
                new KeyFrame(javafx.util.Duration.seconds(0.1D), 
                new javafx.animation.KeyValue[0])
		});
		timeLine.setCycleCount(Animation.INDEFINITE);
		timeLine.play();
		
	}

	private void updateDownload(GameEngine gameEngine, DecimalFormat decimalFormat, LauncherLabel percentageLabel, LauncherLabel currentStep) {
		if(gameEngine.getGameUpdater().downloadedFiles > 0) {
			percentageLabel.setText(decimalFormat.format(gameEngine.getGameUpdater().downloadedFiles * 100.0D / gameEngine.getGameUpdater().filesToDownload) + "%");
		}
		if(gameEngine.getGameUpdater().downloadedFiles * 100.0D / gameEngine.getGameUpdater().filesToDownload == 100) {
			try {
				Thread.sleep(5000);
				System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		currentStep.setText(gameEngine.getGameUpdater().getCurrentInfo());
		
	}
}
