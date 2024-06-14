package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  @Test
  void showGraph(){
    ShowGraph sg = new ShowGraph();
    assertEquals(AdjacencyList.class, sg.createDirectedGraph("E:/Study/软件工程/实验/lab1/text.txt").getClass());
    assertNull(sg.createDirectedGraph(""));
    System.out.println("Show graph test pass!");
  }

  @Test
  void queryBridgeWords() {
    QueryBridgeWordsTest qt = new QueryBridgeWordsTest();
    qt.init("E:/Study/软件工程/实验/lab3/test.txt");
    System.out.println(qt.queryBridgeWords("new", "and"));
    System.out.println(qt.queryBridgeWords("to", "strange"));
    System.out.println(qt.queryBridgeWords("strange", "world"));
  }
}