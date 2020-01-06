package chap8;

abstract class OnlineBanking {

    public void processCustomer(int id){
        Customer c=Database.getCustomerWithId(id);
        makeCustomeerHappy(c);
    }

    protected abstract void makeCustomeerHappy(Customer c);

    static private class Customer{}

    static private class Database{
        static Customer getCustomerWithId(int id){
            return new Customer();
        }
    }

}
