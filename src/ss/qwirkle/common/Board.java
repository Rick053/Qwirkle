package ss.qwirkle.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raoul on 17/01/16.
 */
public class Board {
    List<List<Integer>> boardList = new ArrayList<>();
    public Board(int size){
        List<Integer> x = new ArrayList<Integer>();
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                x.add(0);
            }
            boardList.add(x);
        }

    }


    public void addTile(int x, int y){


    }
    public void addRows(){

    }
    public void addColumns{

    }
    @Override
    public String toString(){

    }

}
