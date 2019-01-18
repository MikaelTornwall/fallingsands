package fallingsands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    
    private Type[][] table;
    private Random random;
    
    public Simulation(int width, int height) {
        
        this.table = new Type[width][height];
        this.random = new Random();
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.table[i][j] = Type.EMPTY;
            }
        }
    }
    
    public void add(int x, int y, Type type) {        
        if (x >= 0 && y >= 0 && x < this.table.length && y < this.table[0].length) {
            this.table[x][y] = type;
        }         
    }
    
    public Type content(int x, int y) {
        
        if (x >= 0 && y >= 0 && x < this.table.length && y < this.table[0].length) {
            
            if (this.table[x][y] == null) {
                return Type.EMPTY;
            }                    
        
            return this.table[x][y];
        }
        
        return Type.METAL;
    }
    
    public void update() {
        for (int x = this.table.length - 1; x >= 0; x--) {
            for (int y = this.table[0].length - 1; y >= 0; y--) {                                
                
                if (this.content(x, y) == Type.SAND) {                    
                    this.moveSand(x, y);      
                    this.replaceWater(x, y);
                }
                
                if (this.content(x, y) == Type.WATER) {                    
                    this.moveWater(x, y);
                }
                                                                                                                             
            }
        }
        
    }
    
    public void moveSand(int x, int y) {
        List<Integer> directions = new ArrayList<>();
                    
        Type sw = this.content(x - 1, y + 1);
                                               
        if (sw == Type.EMPTY) {
            directions.add(-1);
        }                    
                    
        Type s = this.content(x, y + 1);
                        
        if (s == Type.EMPTY) {
            directions.add(0);
        }                    
                    
                    
        Type se = this.content(x + 1, y + 1);
                        
        if (se == Type.EMPTY) {
            directions.add(1);                        
        }                                                                                                                                                                    
                
        if (directions.size() == 1) {
                        
            this.add(x, y, Type.EMPTY);
            this.add(x + directions.get(0), y + 1, Type.SAND);                        
                        
        } else if (directions.size() > 1) {
                        
            int luku = random.nextInt(directions.size());
                    
            int suunta = directions.get(luku);
                    
            this.add(x, y, Type.EMPTY);
            this.add(x + suunta, y + 1, Type.SAND);                                                
        }    
    }
    
    public void moveWater(int x, int y) {
        
        List<Integer> directions = new ArrayList<>();                    
                    
        Type sw = this.content(x - 1, y + 1);
                        
        if (sw == Type.EMPTY) {
            directions.add(-1);
        }                    
                                        
        Type s = this.content(x, y + 1);
                        
        if (s == Type.EMPTY) {
            directions.add(0);
        }
                    
        
        Type se = this.content(x + 1, y + 1);
                        
        if (se == Type.EMPTY) {
            directions.add(1);
        }        
                
        if (directions.size() == 1) {
                        
        this.add(x, y, Type.EMPTY);
        this.add(x + directions.get(0), y + 1, Type.WATER);                        
                        
        } else if (directions.size() > 1) {
                        
        int luku = random.nextInt(directions.size());
                    
        int suunta = directions.get(luku);
                    
        this.add(x, y, Type.EMPTY);
        this.add(x + suunta, y + 1, Type.WATER);                                                
        } 
                                                            
        if (directions.size() == 0) {                                                
                        
            List<Integer> sides = new ArrayList<>();
                                
            Type left = this.content(x - 1, y);
                            
            if (left == Type.EMPTY) {
                sides.add(-1);
            }      
                        
        
            Type right = this.content(x + 1, y);
                            
            if (right == Type.EMPTY) {
                sides.add(1);
            }                                                                            
                        
            if (!sides.isEmpty()) {
                int suunta = this.random.nextInt(sides.size());
                            
                this.add(x, y, Type.EMPTY);
                this.add(x + sides.get(suunta), y, Type.WATER);
            }                        
        }
    }
    
    public void replaceWater(int x, int y) {                               
        
        List<Integer> suunnat = new ArrayList<>();
                    
        Type sw = this.content(x - 1, y + 1);
                                               
        if (sw == Type.WATER) {
            suunnat.add(-1);
        }                    
                    
        Type s = this.content(x, y + 1);
                        
        if (s == Type.WATER) {
            suunnat.add(0);
        }                    
                    
                    
        Type se = this.content(x + 1, y + 1);
                        
        if (se == Type.WATER) {
            suunnat.add(1);                        
        }                                                                                                                                                                    
                
        if (suunnat.size() == 1) {
                        
            this.add(x, y, Type.WATER);
            this.add(x + suunnat.get(0), y + 1, Type.SAND);                        
                        
        } else if (suunnat.size() > 1) {
                        
            int random = this.random.nextInt(suunnat.size());
                    
            int dir = suunnat.get(random);
                    
            this.add(x, y, Type.WATER);
            this.add(x + dir, y + 1, Type.SAND);                                                
        }
    }        
}
