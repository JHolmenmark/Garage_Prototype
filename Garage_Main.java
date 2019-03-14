import java.util.Scanner;

public class Garage_Main {
  public static Garage_Customers allCustomers = new Garage_Customers();
  public static String optionList = "Options: \n 1: carEntryTest \n 2: paymentTest \n 3: leaveByExitTest \n 4: airQualityToggle \n 5: fillGarageToggle \n 6: printGarageContent \n 7: historyPrint \n 8: Quit \n";
  public static String currentUsername = "Tester";
  public static String garageFullness = "Available";
  public static String optionSelect = "";
  public static int garageCapacity = 17;
  public static Scanner scan = new Scanner(System.in);
  public static boolean goLean = true;
  public static boolean airQuality = true;

  public Garage_Main() {
    allCustomers.setupAccounts();
  }

  public String getCurrentUsername(){
    return currentUsername;
  }
  public void setCurrentUsername(String aName){
    currentUsername = aName;
  }
  public String getOptionSelect(){
    return optionSelect;
  }
  public void setOptionSelect(String anOption){
    optionSelect = anOption;
  }
  public boolean getGoLean(){
    return goLean;
  }


  public static void welcomeLine() {
    System.out.println("Welcome " + currentUsername + "! ");
  }
  public static void optionList() {
    System.out.println("Please choose from this testoption list: \n" + optionList);
    optionSelect = scan.next().toLowerCase();
  }

  public static boolean optionHandle() {
    boolean goodOption = true;
    boolean problemDidNotOccur = true;
    if(optionSelect.contains("car") || optionSelect.contains("entry") || optionSelect.contains("1")){
      problemDidNotOccur = carEntryTest();
    } else if (optionSelect.contains("pay") || optionSelect.contains("2")) {
        problemDidNotOccur = paymentTest();
    } else if (optionSelect.contains("leave")|| optionSelect.contains("exit") || optionSelect.contains("3")) {
        problemDidNotOccur = leaveByExitTest();
    } else if (optionSelect.contains("print") || optionSelect.contains("6")) {
        problemDidNotOccur = allCustomers.printCar();
    } else if (optionSelect.contains("history") || optionSelect.contains("7")) {
        problemDidNotOccur = allCustomers.printHistory();
    } else if (optionSelect.contains("fill") || optionSelect.contains("5")) {
        if(garageCapacity == 17) {
          garageCapacity = 0;
        } else {
          garageCapacity = 17;
        }
        problemDidNotOccur = true;
    } else if (optionSelect.contains("air") || optionSelect.contains("4")) {
        problemDidNotOccur = true;
        if(airQuality) {
          airQuality = false;
        } else {
          airQuality = true;
        }
    } else if (optionSelect.contains("quit") || optionSelect.contains("8")) {
      goLean = false;
      problemDidNotOccur = true;
    } else {
      //go here if none of the accepted keywords were given by the user.
      goodOption = false;
      System.out.println("That was not one of the options available, please try again. ");
    }
    if(goodOption){
      return problemDidNotOccur;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    welcomeLine();
    while (goLean) {
      if (allCustomers.bankList.size() >= garageCapacity) {
        garageFullness = "Full!";
      } else {
        garageFullness = "Available";
      }
      System.out.println("Parking for cars is currently: " + garageFullness);
      if(!airQuality) {
        System.out.println("The air quality inside the garage is at unsafe levels!");
      }
      optionList();
      optionHandle();
    }
  }



  public static boolean carEntryTest(){
    if (allCustomers.bankList.size() >= garageCapacity) {
      System.out.println("Sorry, we are full right now, please come back later. ");
      return false;
    } else if(!airQuality) {
      System.out.println("Sorry, the air supply in the garage is currently not at a safe level, please come back later. ");
      return false;
    } else {


      System.out.println("Parking here costs 20 kr per hour.");
      System.out.println("Please enter the license plate nr: ");
      scan.nextLine();
      String newUsername = scan.nextLine();
      if(!allCustomers.goodRegNrFormat(newUsername)) {
        System.out.println("Sorry, we could not read your license plate. Please use the card reader");
        System.out.println("Please enter the card number: ");
        String newCardNumber = scan.next();
        while (!allCustomers.cardGoodFormat(newCardNumber)) {
            System.out.println("Incorrect number format, please try again: ");
            newCardNumber = scan.next();
        }
        System.out.println("Please enter the entry time value: ");
        int newPassword = scan.nextInt();
        if(allCustomers.addAccount("", newPassword, newCardNumber)) {
          System.out.println("Welcome! ");
          return true;
        } else {
          System.out.println("Sorry, for some reason we could not let you in, please contact a staff member");
          return false;
        }

      }
      else if(!allCustomers.regNrExists(newUsername)) {
          //checks that the registration does not already exists
          System.out.println("Please enter the entry time value: ");
          int newPassword = scan.nextInt();
          if(allCustomers.addAccount(newUsername, newPassword, "")) {
            System.out.println("Welcome " + newUsername);
            return true;
          } else {
            System.out.println("Sorry, for some reason we could not let you in, please contact a staff member");
            return false;
          }
        } else {
          System.out.println("Sorry, a car with your license number already exists, please contact a staff member. ");
          return false;
        }
    }
  }

    public static boolean paymentTest() {
      System.out.println("Would you like to pay with your card or an invoice?");
      String newUsername = scan.next();
      if (newUsername.contains("invoice")) {
        System.out.println("Please contact our local staff who will help you finish your payment.");
        return false;
      } else {
        System.out.println("Please enter the license plate nr: ");
        newUsername = scan.next();
        while (!allCustomers.goodRegNrFormat(newUsername)) {
          System.out.println("That license number is not correct format. Please try again: ");
          newUsername = scan.next();
        }
        System.out.println("Please enter the card number: ");
        String newCardNumber = scan.next();
        while (!allCustomers.cardGoodFormat(newCardNumber)) {
            System.out.println("Incorrect number format, please try again: ");
            newCardNumber = scan.next();
        }
        int found = allCustomers.getCustomerEntry(newUsername, newCardNumber);
        if(found >= 0) {
          Garage_CustomerEntry painment = allCustomers.bankList.get(found);

          System.out.println("Please enter the payment time value: ");
          int newPassword = scan.nextInt();
          int staytime = 0;
          if(painment.lastPayTime < 0){
            while (newPassword < painment.entryTime){
              System.out.println("Unless you are driving a delorean, you must leave at a time after you entered. Last entry time was: " + painment.entryTime + " o'clock ");
              newPassword = scan.nextInt();
            }
             staytime = newPassword - painment.entryTime;
          } else {
            while (newPassword < painment.lastPayTime){
              System.out.println("Unless you are driving a delorean, you must leave at a time after you entered. Last payment time was: " + painment.entryTime + " o'clock ");
              newPassword = scan.nextInt();
            }
            staytime = newPassword - painment.lastPayTime;
          }
          int price = 20 * staytime;
          System.out.println("Your card has been charged " + price + " kr. Please exit the garage within 15 minutes, have a nice day!");
          if(allCustomers.bankList.get(found).regNr.equals("")) {
            allCustomers.bankList.get(found).regNr = newUsername;
          }
          if(allCustomers.bankList.get(found).getAccountNumber().equals("")) {
            allCustomers.bankList.get(found).setAccountNumber(newCardNumber);
          }
          allCustomers.bankList.get(found).lastPayTime = newPassword;
          return true;

        } else {
          System.out.println("No entry registered by that license number or card number found, please try paying again or contact a staff member.");
          return false;
        }
      }
  }

  public static boolean leaveByExitTest(){
    System.out.println("Please enter the license plate nr: ");
    String newUsername = scan.next();
    while (!allCustomers.goodRegNrFormat(newUsername)) {
      System.out.println("That license number is not correct format. Please try again: ");
      newUsername = scan.next();
    }
    int found = allCustomers.getCustomerEntry(newUsername, "");
    if(found >= 0) {
      Garage_CustomerEntry painment = allCustomers.bankList.get(found);
      System.out.println("Please enter the departure time value: ");
      int newPassword = scan.nextInt();
      while (painment.lastPayTime > newPassword || painment.entryTime > newPassword) {
        System.out.println("No time travel please, you must depart After entering and/or paying");
        newPassword = scan.nextInt();
      }
      if(painment.lastPayTime == newPassword || painment.entryTime == newPassword){
        System.out.println("Thank you for visiting the garage, have a nice day! ");
        allCustomers.customerRecord.add(painment);
        allCustomers.bankList.remove(found);
        return true;
      } else {
        if(painment.lastPayTime < 0){
          System.out.println("You have not paid yet, entry to garage was at: " + painment.entryTime + " o'clock ");
        } else {
          System.out.println("Payment has expired, last payment was at: " + painment.lastPayTime + " o'clock ");
        }
        return false;
      }
    } else {
      System.out.println("Sorry, we could not read your license plate number, please contact a staff member");
      return false;
    }
  }

  public String purchaseTicket(String accountNr, Double ticketPrice) {
    return allCustomers.makePurchase(accountNr, ticketPrice);
  }

}
