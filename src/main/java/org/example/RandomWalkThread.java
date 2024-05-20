package org.example;

import java.util.*;
import javafx.scene.control.TextField;


public class RandomWalkThread implements Runnable{
    private final Map<String, Integer> word2id;
    private final String[] id2word;
    private final List<LinkedList<Edge>> graph;
    private final TextField txtRandomWalkResult;
    private final Random random = new Random();

    public RandomWalkThread(Map<String, Integer> word2id, String[] id2word, List<LinkedList<Edge>> graph, TextField txtRandomWalkResult) {
        this.word2id = word2id;
        this.id2word = id2word;
        this.graph = graph;
        this.txtRandomWalkResult = txtRandomWalkResult;
    }
    @Override
    public void run() {
        StringBuilder randomWalkSentence = new StringBuilder();
        Map<String, Integer> copiedMap = new HashMap<>(word2id);
        for (Map.Entry<String, Integer> entry : copiedMap.entrySet()) {
            entry.setValue(0);
        }
        int startId;
        int pointer;
        pointer = startId = random.nextInt(word2id.size());
        randomWalkSentence.append(id2word[startId]);
        copiedMap.remove(id2word[startId]);
        while(!Thread.interrupted()){
            LinkedList<Edge> head = graph.get(pointer);
            if (head.isEmpty())
                break;
            else {
                int r = random.nextInt(head.size());
                pointer = head.get(r).destination;
                if (pointer!=startId && copiedMap.containsKey(id2word[pointer])){
                    randomWalkSentence.append(" ").append(id2word[pointer]);
                    copiedMap.remove(id2word[pointer]);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                txtRandomWalkResult.setText(randomWalkSentence.toString());
                Thread.currentThread().interrupt();
                break;
            }
        }
        randomWalkSentence.append(".");
        txtRandomWalkResult.setText(randomWalkSentence.toString());
    }
}

