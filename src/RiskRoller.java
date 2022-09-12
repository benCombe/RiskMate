import BasicIO.*;

public class RiskRoller {

    BasicForm mainForm;

    int numAttack, numDef;
    int[] attackDie, defDie;


    public RiskRoller(){
        attackDie = new int[3];
        defDie = new int[2];

        /* //debugging
        roll();
        for (int i: attackDie) {
            System.out.println(i);
        }
        System.out.println("--------");
        for (int i: defDie) {
            System.out.println(i);
        }

         */

        buildForm();
        runProgram();
        mainForm.close();

    } //constructor

    //main program loop
    private void runProgram()  {
        boolean running = true;
        while(running) {
            int button = mainForm.accept();

            switch (button){

                case 0: //Roll Once
                    getValues();
                    roll();
                    calculateLosses();
                    writeToForm();
                    break;

                case 1: //Roll to complete
                        getValues();
                        while(numDef > 0 && numAttack > 0){
                            roll();
                            calculateLosses();
                            writeToForm();
                            try {
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e) {

                            }
                            getValues();
                        }

                    break;

                case 2: //Close
                    running = false;
                    break;

            }
        }

    } //runProgram


    private void roll(){
        for (int i = 0; i < 3; i++){
            attackDie[i] = randomInt(1,7);
            if (i <= 1)
                defDie[i] = randomInt(1, 7);
        }
        sortDies();
    } //roll

    private void calculateLosses(){
        for(int i = 1; i >= 0; i--){
            if (defDie[i] >= attackDie[i + 1]){
                if (numAttack > 0)
                numAttack--;
            }
            else
                if (numDef > 0)
                numDef--;
        }
    } //calculateLosses

    //get input values
    private void getValues(){
        numAttack = mainForm.readInt("numAttack");
        numDef = mainForm.readInt("numDef");
    }//getValues

    //sort arrays from highest to lowest
    private void sortDies(){
        mergeSort(attackDie, 0 , 2);

        int temp = defDie[0];
        if (defDie[0] > defDie[1]){
            defDie[0] = defDie[1];
            defDie[1] = temp;
        }
    }//sortDies

    public void buildForm(){
        mainForm = new BasicForm("Roll Once", "Roll to Complete", "Close");
        mainForm.setTitle("Risk Roller");

        mainForm.addTextField("numAttack", "# of Attackers", 5);
        mainForm.addTextField("numDef", "# of Defenders", 5, mainForm.getX("numAttack") + 160, mainForm.getY("numAttack"));

        mainForm.addTextField("dieDis0", 2);
        mainForm.setEditable("dieDis0", false);
        for (int i = 1; i < 3; i++){
            mainForm.addTextField("dieDis" + i, 2, mainForm.getX("dieDis" + (i-1)) + 50, mainForm.getY("dieDis0"));
            mainForm.setEditable("dieDis" + i, false);
        }
        mainForm.addTextField("dieDis3", 2, mainForm.getX("dieDis2") + 100, mainForm.getY("dieDis0"));
        mainForm.setEditable("dieDis3", false);
        mainForm.addTextField("dieDis4", 2, mainForm.getX("dieDis3") + 50, mainForm.getY("dieDis0"));
        mainForm.setEditable("dieDis4", false);

        mainForm.show();
    } //buildForm

    private void writeToForm(){

        //Dice
        for(int i = 0; i < 3; i++){
            mainForm.writeInt("dieDis" + i, attackDie[i]);
        }
        mainForm.writeInt("dieDis3", defDie[0]);
        mainForm.writeInt("dieDis4", defDie[1]);

       //Troops
        mainForm.writeInt("numAttack", numAttack);
        mainForm.writeInt("numDef", numDef);
    } //writeToForm

    private int randomInt(int min, int max){
        return (int)((max-min)*Math.random())+min;
    }

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    void mergeSort(int arr[], int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }


    //main
    public static void main(String[] args) {
        new RiskRoller();
    }

}
