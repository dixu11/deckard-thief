package com.deckard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    private final Drop game;

    private OrthographicCamera camera;
    private Rectangle bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private int dropsGathered;
    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    public GameScreen(Drop game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle(
                800 / 2 - 64 / 2,
                20,
                64,
                64
        );

        raindrops = new Array<>();

        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        rainMusic.setLooping(true);

        spawnRaindrop();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle(
                MathUtils.random(0,800-64),
                480,
                64,
                64
        );
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();//if we change something in camera
        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        game.getFont().draw(batch, "Drops Collected: " + dropsGathered, 0, 480);
        raindrops.forEach(drop->batch.draw(dropImage,drop.x,drop.y)); //todo risercz
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // convert to "our" coordinate system
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime(); //200 px/sec delta time = second %?
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime(); //200 px/sec delta time = second %?
        } //mayb delta form up?

        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1_000_000_000) {
            spawnRaindrop();
        }

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();){ // zapytać
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
                dropsGathered++;
            }
        }
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
