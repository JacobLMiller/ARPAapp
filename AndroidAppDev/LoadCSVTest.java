import java.util.*;

public class LoadCSVTest {

public static void public static void main(String[] args) {

  int[][] myArray = ReadInCSV();

  for (int x = 0; x < myArray[].length; x++){
    for(int y = 0; y < myArray[][].length; y++){
      System.out.print(myArray[x][y]);
    }
    System.out.println(" ");
  }

}

public int[][] ReadInCSV(){

 String myFile = "~/Building.csv";
 Scanner scanner = new Scanner(new File(myFile));
 scanner.useDelimiter(",");

 int[][] csvData[][];

 while(scanner.hasNextLine()){
   String value = scanner.nextLine();
   int [stepValue][steValueY] = value;
    }
    return

  }

}
