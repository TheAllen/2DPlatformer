package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Audio.AudioPlayer;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;


public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private AudioPlayer music;
	
	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
		
	}
	
	public void init(){
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset1.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.7);
		bg = new Background("/Backgrounds/forestbg.jpg", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		music = new AudioPlayer("/Music/MegaMan.mp3");
		music.play();
	}
	
	private void populateEnemies(){
		
		enemies = new ArrayList<Enemy>();
		Slugger s;
		
		s = new Slugger(tileMap);
		s.setPosition(150, 100);
		enemies.add(s);
		s = new Slugger(tileMap);
		s.setPosition(860, 200);
		enemies.add(s);
		s = new Slugger(tileMap);
		s.setPosition(1525, 200);
		enemies.add(s);
		s = new Slugger(tileMap);
		s.setPosition(1682, 200);
		enemies.add(s);
		s = new Slugger(tileMap);
		s.setPosition(1800, 200);
		enemies.add(s);
		s = new Slugger(tileMap);
		s.setPosition(2000, 200);
		enemies.add(s);
		
		/*
		Point[] points = new Point[]{
			    new Point(150, 100),
				new Point(860, 200),
				new Point(1525, 200),
				new Point(1682, 200),
				new Point(1800, 200)
			};
			
			for(int i = 0; i < points.length; i++){
				s = new Slugger(tileMap);
				s.setPosition(points[i].x, points[i].y);
				enemies.add(s);
			}
		*/
	}
	
	public void update(){
		//update()
		player.update();
		tileMap.setPosition(GamePanel.width / 2 - player.getX(), GamePanel.height / 2 - player.getY());
		
		//enables the background to follow the player
		bg.setPosition(tileMap.getX(), tileMap.getY());
		
		//attack enemies
		player.checkAttack(enemies);
		
		//update enemies
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			//enemies.get(i).update();
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getX(), e.getY()));
			}
		}
		
		//update explosions
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
	}
	
	
	public void draw(Graphics2D g){
		
		//background picture
		bg.draw(g);
		
		//draw tileMap
		tileMap.draw(g);
		
		player.draw(g);
		
		//draw enemy
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		
		//draw Explosions
		for(int i = 0; i < explosions.size();i++){
			explosions.get(i).setMapPosition((int)tileMap.getX(), (int)tileMap.getY());
			explosions.get(i).draw(g);
			
		}
		
		//draw hud
	    hud.draw(g);
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_A) player.setLeft(true);
		if(k == KeyEvent.VK_D) player.setRight(true);
		//if(k == KeyEvent.VK_W) player.setUp(true); 
		if(k == KeyEvent.VK_S) player.setDown(true);
		
		if(k == KeyEvent.VK_W) player.setJumping(true);
		//change to flying
		if(k == KeyEvent.VK_K) player.setGliding(true);
		if(k == KeyEvent.VK_J) player.setScratching();
		if(k == KeyEvent.VK_L) player.setFiring();
		
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_A) player.setLeft(false);
		if(k == KeyEvent.VK_D) player.setRight(false);
		if(k == KeyEvent.VK_W) player.setUp(false); 
		if(k == KeyEvent.VK_S) player.setDown(false);
		
		if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		
		if(k == KeyEvent.VK_K) player.setGliding(false);
		
		
	}
	
	
	
}