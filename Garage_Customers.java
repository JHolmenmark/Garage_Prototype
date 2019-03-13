import java.util.ArrayList;

public class Garage_Customers {
  public static ArrayList<Garage_CustomerEntry> bankList = new ArrayList();
  public static ArrayList<Garage_CustomerEntry> customerRecord = new ArrayList();

  Garage_CustomerEntry No = new Garage_CustomerEntry("XYXYXY", "Nordea", "1234575590");
  Garage_CustomerEntry Ha = new Garage_CustomerEntry("YXYXYX", "Handelsbanken", "1423598090");
  Garage_CustomerEntry SE = new Garage_CustomerEntry("XXXXXY", "SEB", "5531877890");
  Garage_CustomerEntry Sw = new Garage_CustomerEntry("YYYYYX", "Swedbank", "9regNr49");


   public void setupAccounts(){
    bankList.add(No);
    bankList.add(Ha);
    bankList.add(SE);
    bankList.add(Sw);
  }

  public boolean printCar() {
    String entryList = "Car List: \n";
    for(int i = 0; i < bankList.size(); i++) {
      entryList = entryList + "Car license number: " + bankList.get(i).getregNr();
      entryList = entryList + "\n Entry time:  " + bankList.get(i).entryTime + " hours. ";
      entryList = entryList + "\n Last payment time:  " + bankList.get(i).lastPayTime + " hours. ";
      entryList = entryList + "\n Card number: " + bankList.get(i).getAccountNumber();
      entryList = entryList + " \n" ;
    }
    System.out.println(entryList);
    return true;
  }

  public int getCustomerEntry(String regNr, String accountNr) {
    for(int i = 0; i < bankList.size(); i++) {
      if(bankList.get(i).getAccountNumber().equals(accountNr)) {
        return i;
      }
    }
    for(int i = 0; i < bankList.size(); i++) {
      if(bankList.get(i).getregNr().equals(regNr)) {
        return i;
      }
    }
    return -1;
  }

  public boolean addAccount(String regNr, int entryTime, String accountNr){
    if((!accountExists(accountNr) && regNr.equals("")) || (!regNrExists(regNr) && accountNr.equals(""))) {
      Garage_CustomerEntry accnew = new Garage_CustomerEntry();
      accnew.setRegNr(regNr);
      accnew.entryTime = entryTime;
      accnew.setAccountNumber(accountNr);
      bankList.add(accnew);
      return true;
    } else {
      return false;
    }
  }

  public boolean cardGoodFormat(String accountNumber) {
    return true;
  }

  public boolean accountGoodFormat(String bankName, String accountNumber) {
    boolean good = false;
    if(!(accountNumber.length() == 10)) {
      return false;
    }
    switch(bankName){
      case "Nordea":
        if(accountNumber.substring(0, 4).contains("1234")) {
          good = true;
        }
        break;
      case "Handelsbanken":
        if(accountNumber.substring(0, 4).contains("1423")) {
          good = true;
        }
        break;
      case "SEB":
        if(accountNumber.substring(0, 4).contains("5531")) {
          good = true;
        }
        break;
      case "Swedbank":
        if(accountNumber.substring(0, 5).contains("99156")
        && accountNumber.substring(9).contains("9")) {
          good = true;
        }
        break;
      default:
        good = false;
        break;
    }
    return good;
  }

  public boolean goodRegNrFormat(String regnumber) {
    if (regnumber.length() == 6) {
      return true;
    } else {
      return false;
    }
  }
  public boolean regNrExists(String accountNumber) {
    boolean found = false;
    for(int i = 0; i < bankList.size(); i++) {
      if(bankList.get(i).getregNr().contains(accountNumber)) {
        found = true;
      }
    }
    return found;
  }
  public boolean accountExists(String accountNumber) {
    boolean found = false;
    for(int i = 0; i < bankList.size(); i++) {
      if(bankList.get(i).getAccountNumber().contains(accountNumber)) {
        found = true;
      }
    }
    return found;
  }
  public static String makePurchase(String accountNumber, double ticketPrice) {
    String result = "";
    for(int i = 0; i < bankList.size(); i++) {
      if(bankList.get(i).getAccountNumber().contains(accountNumber)) {
        if(bankList.get(i).purchase(ticketPrice)) {
          result = bankList.get(i).getBankName();
          break;
        } else {
          result = "Insufficient Funds";
          break;
        }
      } else {
        result = "Wrong AccountNr";
      }
    }
    return result;
  }
}
