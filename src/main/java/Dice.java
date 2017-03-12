import java.util.Random;

/**
 * Created by armccullough on 1/26/17.
 */
public class Dice {

    public static void main(String[] args) {

        String finalSum = "";

        for (int i = 0; i < 1000; i++) {
            finalSum = getRandomDollar();
        }
        System.out.println(finalSum);
    }


    public static String getRandomDollar() {
        String _sum = "100";
        Random random = new Random();

        int digitOne = 0;
        int digitTwo = 0;
        int digitThree = 0;

        int gtOdds = 0;

        gtOdds = random.nextInt(4) + 1;

        if (gtOdds == 3 || gtOdds == 2 || gtOdds == 1) {
            digitOne = random.nextInt(6);
            digitTwo = digitOne >= 3 ? random.nextInt(9) : random.nextInt(10);
            digitThree = digitOne >= 3 ? random.nextInt(9) : random.nextInt(10);

        } else {
            digitOne = random.nextInt(4) + 10;
            digitTwo = random.nextInt(6) + 2;
            digitThree = random.nextInt(6) + 2;
        }


        String sum = "" + digitOne + digitTwo + digitThree;
        return _sum;
    }
}
