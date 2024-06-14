package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowGraph {
  private HashMap<String, Integer> word2id = null;
  private String[] id2word = null;
  private AdjacencyList graph = null;

  public AdjacencyList createDirectedGraph(String filename){
    String[] wordList = readFile(filename);
    if (wordList == null) {
      System.out.println("文件读取失败！");
      return null;
    }
    this.word2id = encodeWords(wordList);
    this.id2word = getReversedWordDict(this.word2id);
    this.graph = buildGraph(wordList, this.word2id);
    System.out.println("文件读取成功！");
    return this.graph;
  }

  private static HashMap<String, Integer> encodeWords(String[] wordList) {
    // 将单词列表进行编码，构建词典
    AtomicInteger count = new AtomicInteger();
    HashMap<String, Integer> wordDict = new HashMap<>();
    for (String word : wordList) {
      wordDict.computeIfAbsent(word, k -> count.getAndIncrement());
    }
    return wordDict;
  }

  private static String[] getReversedWordDict(HashMap<String, Integer> wordDict) {
    // 创建一个数组，将哈希表的映射反过来
    String[] reversedWordDict = new String[wordDict.size()];
    String key;
    int value;
    for (Map.Entry<String, Integer> entry : wordDict.entrySet()) {
      key = entry.getKey();
      value = entry.getValue();
      reversedWordDict[value] = key;
    }
    return reversedWordDict;
  }

  private static AdjacencyList buildGraph(String[] wordList, HashMap<String, Integer> wordDict) {
    // 根据单词列表和词典构建图
    int n = wordDict.size();
    int m = wordList.length;
    AdjacencyList graph = new AdjacencyList(n);
    for (int i = 0; i < m - 1; i++) {
      graph.addEdge(wordDict.get(wordList[i]), wordDict.get(wordList[i + 1]));
    }
    return graph;
  }

  private String[] readFile(String fileName) {
    // 读取文件，返回单词列表
    StringBuilder sb = new StringBuilder();
    try (FileReader fileReader = new FileReader(fileName);
         BufferedReader bufferedReader = new BufferedReader(fileReader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // 使用正则表达式替换非字母字符为空格，并替换为小写
        String cleanedLine = line.replaceAll("[^a-zA-Z]+", " ").toLowerCase();
        sb.append(cleanedLine).append(" ");
      }
    } catch (IOException e) {
      return null;
    }
    return sb.toString().split("\\s+");
  }
}
