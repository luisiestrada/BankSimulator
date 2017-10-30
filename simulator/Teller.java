package simulator;

class Teller
{
    // start time and end time of current interval
    private int startTime;
    private int endTime;

    // teller id and current customer which is served by this teller
    private int tellerID;
    private Customer currentCustomer;

    // for keeping statistical data
    private int totalFreeTime;
    private int totalBusyTime;
    private int totalCustomers;

    // Constructor
    Teller()
    {
        this(1);
    }

    // Constructor with teller id
    Teller(int tellerId)
    {
        tellerID = tellerId;
    }

    // accessor methods

    int getTellerID()
    {
        return tellerID;
    }

    Customer getCustomer()
    {
        return currentCustomer;
    }

    // need this to setup priority queue
    int getEndBusyIntervalTime()
    {
        // return end time of busy interval
        return endTime;
    }

    // functions for state transition
    // FREE -> BUSY :
    void freeToBusy (Customer currentCustomer, int currentTime)
    {
        // Main goal : switch from free interval to busy interval
        //
        // end free interval, start busy interval
        // steps : update totalFreeTime
        //         set startTime, endTime, currentCustomer
        //         update totalCustomers

        totalFreeTime += (currentTime - startTime);
        startTime = currentTime;
        endTime = startTime + currentCustomer.getTransactionTime();
        this.currentCustomer = currentCustomer;
        totalCustomers++;
    }

    // BUSY -> FREE :
    Customer busyToFree ()
    {
        // Main goal : switch from busy interval to free interval
        //
        // steps : update totalBusyTime
        //         set startTime
        //         return currentCustomer

        totalBusyTime += (endTime - startTime);
        startTime = endTime;
        return currentCustomer;
    }

    // need this method at the end of simulation to update teller data
    // intervalType: 0 for FREE interval, 1 for BUSY interval
    void setEndIntervalTime (int endsimulationtime, int intervalType)
    {
        // for end of simulation
        // set endTime,
        // for FREE interval, update totalFreeTime
        // for BUSY interval, update totalBusyTime

        endTime = endsimulationtime;

        if (intervalType == 0) {
            totalFreeTime += endTime - startTime;
        } else {
            totalBusyTime += endTime - startTime;
        }
    }

    // functions for printing statistics :
    void printStatistics ()
    {
        // print teller statistics, see project statement

        System.out.println("\t\tTeller ID                : "+tellerID);
        System.out.println("\t\tTotal free time          : "+totalFreeTime);
        System.out.println("\t\tTotal busy time          : "+totalBusyTime);
        System.out.println("\t\tTotal # of customers     : "+totalCustomers);

        if (totalCustomers > 0) {
            System.out.format("\t\tAverage transaction time : %.2f\n",
                    (totalBusyTime*1.0)/totalCustomers);
        }
        System.out.println();
    }

    @Override
    public String toString()
    {
        return "Teller:"+tellerID+":"+startTime+"-"+endTime+":Customer:"+currentCustomer;
    }

    public static void main(String[] args)
    {
        // quick check
        Customer mycustomer = new Customer(20,30,40);
        Teller myteller = new Teller(5);
        myteller.freeToBusy (mycustomer, 13);
        myteller.printStatistics();
    }

}
