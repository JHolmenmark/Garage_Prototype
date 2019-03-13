public class Garage_CustomerEntry {
    public String regNr = "";
    public int entryTime = 0;
    public int lastPayTime = -1;
    public int exitTime = -1;

    private String bankName = "";
    private String accountNr = "";
    private double balance = 0.0;


    public Garage_CustomerEntry(){

    }
    public Garage_CustomerEntry(String regNring, String bankNameing, String AccountNring) {
      this.regNr = regNring;
      this.bankName = bankNameing;
      this.accountNr = AccountNring;
    }
    public void setRegNr(String regNumber){
      regNr = regNumber;
    }
    public String getregNr() {
      return regNr;
    }
    public void setBankName(String name) {
      bankName = name;
    }
    public void setAccountNumber(String number) {
      accountNr = number;
    }
    public void setBalance(double money) {
      balance = money;
    }
    public String getBankName() {
      return bankName;
    }
    public String getAccountNumber() {
      return accountNr;
    }
    public double getBalance() {
      return balance;
    }

    public boolean purchase(double price) {
      if(price > balance) {
        return false;
      } else {
        balance = balance - price;
        return true;
      }
    }
  }
