package fr.aytrexois.launcher;


import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends AlternativeBase {
	
	private GameFolder gameFolder = new GameFolder(References.GAME_FOLDER_PATH);
	private LauncherPreferences launcherPreferences = new LauncherPreferences(References.LAUNCHER_NAME, References.SIZE_LAUNCHER_X, References.SIZE_LAUNCHER_Z, References.CAN_MOOVE_LAUNCHER);
	private GameLinks gameLink = new GameLinks(References.FILES_LINK, References.JSON_VERSION);
	private GameEngine gameEngine = new GameEngine(gameFolder, gameLink, launcherPreferences, References.GAMESTYLE);
	//private GameConnect gameConnect = new GameConnect("play.pladrinset.fr", "25565");
	
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
	    	Scene scene = new Scene(createContent());
	    	this.gameEngine.reg(stage);
	    	//this.gameEngine.reg(this.gameConnect);
	    	LauncherBase launcherBase = new LauncherBase(stage, scene, References.STAGESTYLE, gameEngine);
	    	launcherBase.setIconImage(stage, getResourceLocation().loadImage(gameEngine, References.PATH_LOGO));
	    	this.gameEngine.reg(stage);
	    	
	    
		
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(gameEngine);
		new LauncherPanel(contentPane, gameEngine);
		return contentPane;
	}
	
}
