package ss.qwirkle.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raoul on 17/01/16.
 */
public class Board {
    private int offSetY;
    List<List<Integer>> boardList = new ArrayList<>();
    private int offSetX;

    public Board(int size){
        for(int i = 0; i < size; i++) {
            List<Integer> x = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                x.add(0);
            }
            boardList.add(x);
        }

    }


    public void addTile(int x, int y, int tile){
        offSetY = boardList.size()/2;
        offSetX = boardList.get(0).size()/2;

        if ((x+offSetX)>boardList.size()){
            addColumns();
        }

        if ((y+offSetY)>boardList.get(0).size()){
            addRows();
        }else boardList.get(y+offSetY).set(x+offSetX,tile);
    }
    public void addRows(){
        List<Integer> x = new ArrayList<>();
        for (int j = 0; j < boardList.get(0).size(); j++) {
            x.add(0);
        }
        boardList.add(0,x);
        boardList.add(x);
    }
    public void addColumns(){
        for (int j = 0; j < boardList.size(); j++){
            boardList.get(j).add(0,0);
            boardList.get(j).add(0);
        }
    }
    @Override
    public String toString(){
        String board = "";
        for (List<Integer> row:boardList) {
            for (int tile:row) {
                board += intToQwirkle(tile);

            }
            board += System.lineSeparator();
        }
        return board;
    }
    public String intToQwirkle(int i){
        switch (i){
            case -1:
                return "#";
            case 0:
                return "O";
            case 1:
                return "\u25A0"; //square
            case 2:
                return "\u274C"; //cross
            case 3:
                return "\u271A"; //plus
            case 4:
                return "\u2737"; //star
            case 5:
                return "5";
            case 6:
                return "6";

        }
        return null;
    }


}
