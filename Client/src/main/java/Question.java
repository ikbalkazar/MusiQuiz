/**
 * Created by Şamil on 15.12.2016.
 */
public class Question {

    private String[] choices;
    private int correctChoice;
    private String URL;

    public Question(String[] choices, String URL) {

        this.choices = choices;
        this.URL = URL;

    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public int shuffleChoices() {

        int index;

        int[] places = new int[4];

        for (int i = 0; i < 4; i++)
            places[i] = i;

        // shuffle the places of the choices

        for (int i = 3 ; i >= 0 ; i--) {

            index = (int)(Math.random()*4);

            // swap the values in index and i
            if ( i!=index ){
                places[index] += places[i];
                places[i] = places[index] - places[i];
                places[index] = places[index] - places[i];
            }

        }

        // put choices into new places
        String[] newChoices = new String[4];
        for (int i = 0; i < 4; i++) {
            newChoices[i] = choices[places[i]];
            if (places[i] == 0)
                correctChoice = i;
        }

        // replace it with the original array
        choices = newChoices;


        return correctChoice;

    }

    public String toString() {

        return "A) " + choices[0] + "\nB) " + choices[1] + "\nC) " + choices[2] + "\nD) " + choices[3] + "\n" + "correct : " + correctChoice + "\n";

    }

}