package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;


class Game {
    Player player;
    Enemy BOSS;
    ArrayList<Enemy> enemies;
    ArrayList<Bullet> bullets;
    Game () {
        player = new Player(100, 30, 10, 100, 100);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    void updateShot () {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Bullet bullet = player.shot(Gdx.input.getX(),Gdx.input.getY());
            bullets.add(bullet);
        }

        for (Bullet bullet : bullets) {
            bullet.Move();
            if (bullet.pos.x >= 1500) {
                bullets.remove(bullet);
            }
            if (bullet.pos.x <= 0) {
                bullets.remove(bullet);
            }
            if (bullet.pos.y >= 800) {
                bullets.remove(bullet);
            }
            if (bullet.pos.y <= 0) {
                bullets.remove(bullet);
            }
            for (Enemy enemy : enemies) {
                if (math.distance(enemy.pos,bullet.pos) <= enemy.r + bullet.r) {}
            }
        }
    }

    void update () {
        player.update();

        updateShot();
    }
}

class Player {
    int hp;
    int m1DMG;
    int bDMG;
    Vec2 pos;
    float bSpeed;
    float speed = 5;
    float r = 20;

    Player (int hp, int m1DMG, int bDMG, float x, float y) {
        this.hp = hp;
        this.bDMG = bDMG;
        this.m1DMG = m1DMG;
        this.pos = new Vec2(x, y);
        this.bSpeed = 30;
    }

    void attack (Enemy enemy) {
        enemy.takeDmg(m1DMG);
    }

    Bullet shot(float mx,float my) {
        Vec2 mousepos = new Vec2(mx,my);
        Vec2 bV = math.getvelocity(pos,mousepos,bSpeed);
        return new Bullet(pos.x, pos.y, bV.x, bV.y);          // Incomplete
    }

    void takeDmg (int amount) {
        if (hp >= amount) {
            hp -= amount;
        }
    }

    void Controll () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.y -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += speed;
        }
    }

    void update () {
        Controll();
    }

}

class Enemy {
    int hp;
    int m1DMG;
    int bDMG;
    float r = 20;
    Vec2 pos;
    float timer = 0f;
    float cooldown = 0.5f;

    Enemy (int hp, int m1DMG, int bDMG,Vec2 pos) {
        this.hp = hp;
        this.bDMG = bDMG;
        this.m1DMG = m1DMG;
        this.pos = pos;
    }

    void attack (Player player) {
        if (math.distance(player.pos,pos) <= player.r + r) {
            player.takeDmg(m1DMG);
        }
    }

    void takeDmg (int amount) {
        if (hp >= amount) {
            hp -= amount;
        }
    }

    void update () {
        timer += Gdx.graphics.getDeltaTime();

        if (timer >= cooldown) {
            takeDmg(m1DMG);
        }
    }
}


class Bullet {
    Vec2 pos;
    Vec2 velocity;
    float r = 5;

    Bullet (float x, float y, float vx, float vy) {
        this.pos = new Vec2(x,y);
        this.velocity = new Vec2(vx,vy);
    }

    void Move () {
        pos.x += velocity.x;
        pos.y += velocity.y;
    }
}



// LibGDX stuff
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Game game;
    private ShapeRenderer sr;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        game = new Game();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        batch.begin();
//        batch.end();
        game.update();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0.2f,0.8f,1f,1f);
        sr.circle(game.player.pos.x,game.player.pos.y,game.player.r);
        sr.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
    }
}
