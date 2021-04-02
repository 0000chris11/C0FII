package com.cofii2.components.javafx;

import java.util.PriorityQueue;
import java.util.Queue;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class ScrollerPiano extends ScrollPane{
    
    public static final int WK_AMOUNT = 52;
    public static final int BK_AMOUNT = 36;

    private Rectangle[] wks = new Rectangle[WK_AMOUNT];
    private Rectangle[] bks = new Rectangle[BK_AMOUNT];

    private int[] bkIds = new int[] { 0, 2, 3, 5, 6, 7, 9, 10, 12, 13, 14, 16, 17, 19, 20, 21, 23, 24, 26, 27, 28, 30,
            31, 33, 34, 35, 37, 38, 40, 41, 42, 44, 45, 47, 48, 49 };

    public static final int WK_WIDTH = 36;
    public static final int WK_HEIGHT = 140;

    public static final int BK_WIDTH = 24;
    public static final int BK_HEIGHT = 80;

    public static final Color WK_FILL = Color.WHITE;
    public static final Color BK_FILL = Color.BLACK;

    public static final Color WK_FILL_PRESSED = Color.rgb(153, 204, 255);
    public static final Color BK_FILL_PRESSED = Color.rgb(0, 51, 102);

    public static final Color STROKE = Color.BLACK;

    public ScrollerPiano(){
        Queue<Integer> queue = new PriorityQueue<>();
        for (int a = 0; a < bkIds.length; a++) {
            queue.offer(bkIds[a]);
        }

        Group root = new Group();
        int wx = 0;

        String path = getClass().getResource("/resources/AikA Zero - E1 Bells.wav").toString();
        System.out.println("Path: " + path);
        AudioClip ac = new AudioClip(path);
        
        
        int bkCount = 0;
        for (int a = 0; a < WK_AMOUNT; a++) {
            wks[a] = new Rectangle(wx, 0, WK_WIDTH, WK_HEIGHT);
            wks[a].setFill(WK_FILL);
            wks[a].setStroke(STROKE);

            // WK LISTENERS
            final int aa = a;
            wks[a].setOnMousePressed(e -> {
                wks[aa].setFill(WK_FILL_PRESSED);
                ac.play();
            });

            wks[a].setOnMouseReleased(e -> {
                wks[aa].setFill(WK_FILL);
            });

            wx += WK_WIDTH;

            if (!queue.isEmpty()) {
                if (a == queue.peek()) {
                    bks[bkCount] = new Rectangle((wx - 12), 0, BK_WIDTH, BK_HEIGHT);
                    bks[bkCount].setFill(BK_FILL);
                    bks[bkCount].setStroke(STROKE);

                    // BK LISTENERS
                    final int bb = bkCount;
                    bks[bkCount].setOnMousePressed(e -> {
                        bks[bb].setFill(BK_FILL_PRESSED);
                    });

                    bks[bkCount].setOnMouseReleased(e -> {
                        bks[bb].setFill(BK_FILL);
                    });

                    bkCount++;
                    queue.poll();
                }
            }
        }

        root.getChildren().addAll(wks);
        root.getChildren().addAll(bks);

        this.setContent(root);
    }
}
