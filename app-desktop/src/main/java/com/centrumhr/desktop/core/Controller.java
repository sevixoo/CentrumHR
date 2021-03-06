package com.centrumhr.desktop.core;

import com.centrumhr.application.core.UI;
import com.centrumhr.desktop.ui.common.ErrorDialog;
import com.centrumhr.desktop.ui.common.LoadingDialog;
import com.centrumhr.desktop.ui.common.SimpleAskDialog;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.PopOver;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Seweryn on 18.09.2016.
 */
public abstract class Controller implements UI{

    public static final int RESULT_CANCEL = 0;
    public static final int RESULT_OK = 1;

    private final String resource;
    private Stage stage;
    private String title;
    private PopOver popOver;

    private int result = RESULT_CANCEL;

    public Controller(String resource) {
        this.resource = resource;
        this.title = Controller.class.getName();
    }

    public void setPopOver(PopOver popOver) {
        this.popOver = popOver;
    }

    public String getResource(){
        return resource;
    }

    public void onSceneCreated( Scene scene ){ }

    public void setTitle( String title ){
        this.title = title;
        if(stage!=null) {
            stage.setTitle(title);
        }
    }

    public String getTitle() {
        return title;
    }

    public final void setStage( Stage stage ){
        this.stage = stage;
        onStageCreated(stage);
    }

    public void onStageCreated(Stage stage ){ }

    public abstract void initialize();

    public Stage getStage(){
        return stage;
    }

    public void start(){
        SceneManager.getInstance().displayScene( this );
    }

    public void startForResult( Controller parent ){
        SceneManager.getInstance().displayForResult( parent , this );
    }

    public void displayPopOver( Window parent ){
        SceneManager.getInstance().displayPopOver( parent , this );
    }

    public void displayPopOver( Window parent , double x , double y ){
        SceneManager.getInstance().displayPopOver( parent , this , new Point( (int)x , (int)y ));
    }

    public void finish(){
        stage.close();
        stage = null;
    }

    public void dismiss(){
        if(popOver!=null) {
             popOver.hide();
        }else{
            finish();
        }
    }

    public double getWidth(){
        if(stage!=null){
            return stage.getWidth();
        }else{
            return popOver.getWidth();
        }
    }

    public double getHeight(){
        if(stage!=null){
            return stage.getHeight();
        }else{
            return popOver.getHeight();
        }
    }

    public void displayComponent( Pane container , Controller controller ){
        container.getChildren().clear();
        try{
            Node node = SceneManager.getInstance().load( this , controller );
            ((Pane)node).prefWidthProperty().bind(container.widthProperty());
            ((Pane)node).prefHeightProperty().bind(container.heightProperty());
            container.getChildren().add(node);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public void displayError(Throwable throwable) {
        throwable.printStackTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        ErrorDialog dialog = new ErrorDialog( "B��d",throwable.getMessage(),sw.toString());
        dialog.startForResult( this );
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
