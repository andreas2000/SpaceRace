package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.*;

import java.util.LinkedList;
import java.util.List;

/**
 * О! Это самое сердце игры - борда, на которой все происходит.
 * Если какой-то из жителей борды вдруг захочет узнать что-то у нее, то лучше ему дать интефейс {@see Field}
 * Борда реализует интерфейс {@see Tickable} чтобы быть уведомленной о каждом тике игры. Обрати внимание на {Sample#tick()}
 */
public class Sample implements Tickable, Field {

    private List<Player> players;

    private final int size;
    private Dice dice;
    private List<Wall> walls;
    private List<Bullet> bullets;
    private List<Stone> stones;
    private boolean isNewStone = true;

    public Sample(Level level, Dice dice) {
        this.dice = dice;
        walls = level.getWalls();
        size = level.getSize();
        players = new LinkedList<>();
        stones = new LinkedList<>();
        bullets = new LinkedList<>();

    }

    /**
     * @see Tickable#tick()
     */
    @Override
    public void tick() {
        for (Player player : players) {
            Hero hero = player.getHero();
            hero.tick();
        }
        for (Stone stone : stones) {
            stone.getStone().tick();
//            stone.tick();
        }



//        for (Bullet bullet : bullets) {
//            bullet.tick();
//        }
//
//        for (Bullet bullet : bullets.toArray(new Bullet[0])) {
//            if (walls.contains(bullet)) {
//                bullets.remove(bullet);
//            }
//        }
    }

    public int size() {
        return size;
    }

    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        player.newHero(this);
    }

    public void remove(Player player) {
        players.remove(player);
    }

    public BoardReader reader() {
        return new BoardReader() {
            private int size = Sample.this.size;

            @Override
            public int size() {
                return size;
            }

            @Override
            public Iterable<? extends Point> elements() {
                List<Point> result = new LinkedList<>();
                result.addAll(getHeroes());
                result.addAll(stones);
                result.addAll(walls);
                return result;
            }
        };
    }

    public List<Hero> getHeroes() {
        List<Hero> heroes = new LinkedList<>();
        for (Player player : players) {
           heroes.add(player.getHero());
        }
        return heroes;
    }

    @Override
    public void addBullet(int x, int y, Direction direction) {
        bullets.add(new Bullet(x, y, direction));
    }

    @Override
    public void setStone(int x) {
        if (isNewStone){
            stones.add(new Stone(x, size));
            isNewStone = false;
        }
    }
}
















