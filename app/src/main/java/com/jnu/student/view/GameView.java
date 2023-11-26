package com.jnu.student.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.jnu.student.R;
import com.jnu.student.data.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private boolean isPlaying;
    private Thread gameThread;

    private List<Book> books;
    private int score;

    private float fingerX;
    private float fingerY;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();

        books = new ArrayList<>();
        score = 0;

        fingerX = -1;
        fingerY = -1;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    fingerX = event.getX();
                    fingerY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    fingerX = -1;
                    fingerY = -1;
                    break;
            }
            return true;
        });

        startGame();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // ...
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopGame();
    }

    private void startGame() {
        isPlaying = true;
        gameThread = new Thread(this::runGame);
        gameThread.start();
    }

    private void stopGame() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runGame() {
        while (isPlaying) {
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                update();
                draw();
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() {
        spawnBook();
        updateBooks();
        checkCollisions();
        removeOutOfBoundsBooks();
    }

    private void spawnBook() {
        Random random = new Random();
        if (random.nextInt(100) < 5) {
            int resourceId = getRandomBookResourceId();
            Book book = new Book(getResources(), resourceId, random.nextInt(canvas.getWidth()), 0);
            books.add(book);
        }
    }

    private int getRandomBookResourceId() {
        int[] bookResourceIds = {R.drawable.book_1, R.drawable.book_2, R.drawable.book_no_name};
        Random random = new Random();
        return bookResourceIds[random.nextInt(bookResourceIds.length)];
    }

    private void updateBooks() {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            book.update();

            if (book.isTouched(fingerX, fingerY)) {
                iterator.remove();
                score++;
            }
        }
    }

    private void checkCollisions() {
        // ...
    }

    private void removeOutOfBoundsBooks() {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getY() > canvas.getHeight()) {
                iterator.remove();
            }
        }
    }

    private void draw() {
        canvas.drawColor(Color.BLACK);

        for (Book book : books) {
            canvas.drawBitmap(book.getBitmap(), book.getX(), book.getY(), paint);
        }

        drawScore();
    }

    private void drawScore() {
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("Score: " + score, 50, 100, paint);
    }

    @Override
    public void run() {

    }

    private class Book {
        private Bitmap bitmap;
        private float x;
        private float y;
        private RectF rectF;

        private static final int BOOK_WIDTH = 100; // 指定书本的宽度
        private static final int BOOK_HEIGHT = 100; // 指定书本的高度

        public Book(Resources resources, int resourceId, float x, float y) {
            bitmap = BitmapFactory.decodeResource(resources, resourceId);
            // 缩放位图到指定的宽高
            bitmap = Bitmap.createScaledBitmap(bitmap, BOOK_WIDTH, BOOK_HEIGHT, false);
            this.x = x;
            this.y = y;
            rectF = new RectF();
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void update() {
            y += 10; // 调整书本的下降速度

            rectF.set(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
        }

        public boolean isTouched(float touchX, float touchY) {
            return rectF.contains(touchX, touchY);
        }
    }
}