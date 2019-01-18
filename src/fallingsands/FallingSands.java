package fallingsands;

import javafx.application.Application;
import static javafx.application.Application.launch;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FallingSands extends Application {
    
    private Type type;
    private Random random;
    
    @Override
    public void init() {
        this.type = Type.METAL;
        this.random = new Random();
    }
    
    @Override
    public void start(Stage ikkuna) {
        
        this.init();
        
        int height = 400;
        int width = 400;
        
        Simulation simulation = new Simulation(width, height);               
                
        ToggleGroup btngroup = new ToggleGroup();
        
        RadioButton button1 = new RadioButton("Metal");
        button1.setToggleGroup(btngroup);
        RadioButton button2 = new RadioButton("Sand");
        button2.setToggleGroup(btngroup);
        RadioButton button3 = new RadioButton("Water");
        button3.setToggleGroup(btngroup);        
        
        Canvas canvas = new Canvas(width, height);
        GraphicsContext piirturi = canvas.getGraphicsContext2D();
        piirturi.setFill(Color.BLACK);
        piirturi.fillRect(0, 0, width, height);
        
        VBox menu = new VBox();
        menu.getChildren().add(button1);
        menu.getChildren().add(button2);
        menu.getChildren().add(button3);
        
        BorderPane posit = new BorderPane();
        posit.setCenter(canvas);
        posit.setRight(menu);                
        
        button1.setOnMouseClicked(event -> {
           button1.setSelected(true);
           this.type = Type.METAL;            
        });
        
        button2.setOnMouseClicked(event -> {
           button2.setSelected(true);
           this.type = Type.SAND;           
        });
        
        button3.setOnMouseClicked(event -> {
           button3.setSelected(true);
           this.type = Type.WATER;
        });
        
        button1.setSelected(true);                                
        
        
        canvas.setOnMouseClicked(event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();
                    
            simulation.add(x, y, type);
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {                    
                    simulation.add(x + this.random.nextInt(i), y + this.random.nextInt(j), type);
                }
            }
        });
                
        canvas.setOnMouseDragged(event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();
            
            simulation.add(x, y, type);   
            for (int i = -5; i < 6; i++) {
                for (int j = 1; j <6; j++) {
                    simulation.add(x + i, y + this.random.nextInt(j), type);
                }
            }
        });            
        
        
        new AnimationTimer() {
            long prev = 0;
            
            @Override
            public void handle(long now) {
                if (now - prev < 10000000) {
                    return;
                }     
                
                simulation.update();
                
                if (now - prev < 20000000) {
                    return;
                } 
                                
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        Type xy = simulation.content(x, y);
                        
                        if (xy == Type.EMPTY) {
                            piirturi.setFill(Color.BLACK);
                            piirturi.fillOval(x, y, 1, 1);
                        }
                        
                        if (xy == Type.METAL) {
                            piirturi.setFill(Color.WHITE);
                            piirturi.fillOval(x, y, 1, 1);
                        }
                        
                        if (xy == Type.SAND) {
                            piirturi.setFill(Color.ORANGE);
                            piirturi.fillOval(x, y, 1, 1);
                        }
                        
                        if (xy == Type.WATER) {
                            piirturi.setFill(Color.LIGHTBLUE);
                            piirturi.fillOval(x, y, 1, 1);
                        }
                                                
                    }
                }                                                   
                this.prev = now;
            }
        }.start();
        
        
        Scene scene = new Scene(posit);
        
        ikkuna.setScene(scene);
        ikkuna.setTitle("Falling Sands");
        ikkuna.show();        
    }
 
    public static void main(String[] args) {
        launch(FallingSands.class);
    }
    
}
