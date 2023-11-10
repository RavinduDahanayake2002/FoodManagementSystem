package com.example.foodiesfavetask4;

public class FoodQueue {

    public final Customer[] customers;

    public FoodQueue(int customersArrayLength) {
        this.customers = new Customer[customersArrayLength];
    }

    public void addCustomers(Customer customer){
        customers[existingItemCount()] = customer;
    }

    public String removeCustomer(){
        return this.removeCustomer(1);
    }

    public String removeCustomer(int place){
        customers[place-1] = null;
        sortQueue(place);
        return "Customer successfully removed from the queue.";
    }

    public void sortQueue(int place){
        if (customers.length != place) {
            for (int i = (place-1); i < customers.length; i++) {
                if (i == (customers.length-1))
                    return;

                customers[i] = customers[i+1];
                customers[i+1] = null;
            }
        }
    }

    public int existingItemCount(){
        int count = 0;
        for (Customer object: customers){
            if (object != null)
                count++;
        }
        return count;
    }

    public int getQueueLength() {
        return customers.length;
    }

    public double calculateIncome(double unitPrice) {
        if(existingItemCount() == 0)
            return 0;
        return existingItemCount()*unitPrice;
    }
}
