import java.awt.*;
//an API used to develop GUI Windows aka grafiken vi mst göra

import java.awt.event.*;
//awt.event provides interface classes package which deals with events done by the awt component

import javax.swing.*;
//Javax stores class package to make your GUI more user friendly Swing is one of these packages

import java.util.Random;

//Used to generate pseudorandom numbers

import javax.swing.JPanel;





public class GamePanel extends JPanel implements ActionListener{
    
private static final long serialVersionUID = 1L; 

static final int WIDTH = 500;
//final makes it so that the variable cannot be modified vilket ger en klar inblick på hur stor spel rutan kmr va
static final int HEIGHT = 500;
//Juste lärde mig att static final är en constant variable vilket inom java brukar skrivas med Stora bokstäver
static final int UNIT_SIZE = 20;
static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

//Håller koll på Ormens kropps cordinater

final int x[] = new int [NUMBER_OF_UNITS];
final int y[] = new int [NUMBER_OF_UNITS];

//initial length of the snake i starten av spelet samt game variables needed

int length = 5;
//storleken är i pixlar
int eatenFood;
int foodX;
int foodY;
char direction = 'D';
boolean running = false;
Random random;
Timer timer;

private int i;

    GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.BLACK);
    this.setFocusable(true);
    // Makes it so the window can listen to key inputs

    this.addKeyListener(new MyKeyListener());
    play();

    }

        public void play() {
            addFood();
            running = true;

            timer = new Timer(120, this);
            timer.start();
            //makes it so when we play the game timer has a delay when we start
        }

            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                //kallar på a class that is higher in the heiarchy generalise common attributes to keep structure
                draw(graphics);
           
            }

                public void move() {
                    //for loopen kopierar x och y variablerna och kopierar de och sedan ändrar de så att de ser ut som att ormen rör sig
                    for (int i = length; i > 0; i--)  {
                        //Change the snakes unit to the desired direction
                        x[i] = x[i-1];
                        y[i] = y[i-1];

                    }

                    if (direction == 'L') {
                        x[0] = x[0] - UNIT_SIZE;

                        //U,L och R är rörelse riktningar
                    } else if(direction =='R') {
                     x[0] = x[0] + UNIT_SIZE;   
                    } else if (direction == 'U') {
                        y[0] = y[0] - UNIT_SIZE;
                    } else {
                        y[0] = y[0] + UNIT_SIZE;
                     }

                }

                    public void foodChecker() {
                        //Ser till att the checka att the food is touching the snake so we can use it to increase size and move the food
                        if(x[0] == foodX && y[0] == foodY) {
                            length++;
                            eatenFood++;
                            addFood();

                        }
                    }

                        public void draw(Graphics graphics) {
                            if (running) {
                                //Färgen på maten
                                graphics.setColor(new Color(255, 1 , 255));
                                graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

                                graphics.setColor(Color .darkGray);
                                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                            
                            //orm färgen
                            for (int i = 1; i < length; i++) {
                                graphics.setColor(Color .gray);
                                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                            }
                            //Poäng färgen
                            graphics.setColor(Color .white);
                            graphics.setFont(new Font("Monocraft", Font.ROMAN_BASELINE, 25));
                            FontMetrics metrics = getFontMetrics(graphics.getFont());
                            //Ritar texten score och centrerar den and gets the info off the size and font
                            graphics.drawString("Score: " + eatenFood, (WIDTH - metrics.stringWidth("Score: "+ eatenFood)) /2, graphics.getFont().getSize());
                            }
                         else {
                            gameStop(graphics);
                              }
                        }
                        //Foods position
                public void addFood() {
                    foodX = random.nextInt((int)(WIDTH / UNIT_SIZE))* UNIT_SIZE;
                    foodY = random.nextInt((int)(HEIGHT / UNIT_SIZE))* UNIT_SIZE;
                }
            public void collisionChecker() {

                //Head collision med kroppen checkning = game over
                for (int i = length; i > 0; i--) {
                    if(x[0] == x[i] && y[0] == y[i]) {
                        running = false;
                    }
                }
                //Wall Collision check
                if (x[0] < 0 || x [0] > WIDTH || y[0] < 0 || y[0] > HEIGHT){
                    running = false;
                }

                if(!running){
                    timer.stop();
                }
            }

                public void gameStop(Graphics graphics) {

                    graphics.setColor(Color .red);
                    graphics.setFont(new Font("Monocraft", Font.ROMAN_BASELINE, 50));
                    FontMetrics metrics = getFontMetrics(graphics.getFont());
                    //centering the text
                    graphics.drawString("Game Over Lolz", (WIDTH - metrics.stringWidth("Game Over Lolz")) / 2, HEIGHT /2);
                
                    graphics.setColor(Color .white);
                    graphics.setFont(new Font("Monocraft", Font.ROMAN_BASELINE, 25));
                    //centering text again
                    metrics = getFontMetrics(graphics.getFont());
                    graphics.drawString("Score: " + eatenFood, (WIDTH - metrics.stringWidth("Score. " + eatenFood)) /2, graphics.getFont().getSize());

                }

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(running) {
                        //checks events and controls its been performed
                        move();
                        foodChecker();
                        collisionChecker();
                    }
                    repaint();//återritar spelplanen so the game updates

                }

                public class MyKeyListener extends KeyAdapter {

                    @Override
                    public void keyPressed(KeyEvent e) {
                        //Case = place för if and else statements
                        switch(e.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                            if (direction != 'R'){
                                direction = 'L';
                            }
                            break;

                             case KeyEvent.VK_RIGHT:
                                if (direction != 'L'){
                                    direction = 'R';
                                }
                                break;

                                case KeyEvent.VK_UP:
                                if(direction != 'D') {
                                    direction = 'U';
                                 }
                                 break;

                                    case KeyEvent.VK_DOWN:
                                    if(direction != 'U'){
                                        direction = 'D';
                                    }
                                    break;

                                    
                                    
                                    

                                
                        }

                    }
                }


    

}