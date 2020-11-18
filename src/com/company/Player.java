package com.company;

class Player implements Comparable<Player>{
    String name;
    int score;

    public Player(String name, int Score) {
        this.name = name;
        this.score = Score;
    }

    @Override
    public int compareTo(Player o) {
        if( this.score > o.score ) return -1;
        else if( this.score < o.score ) return 1;
        return 0;
    }
}
