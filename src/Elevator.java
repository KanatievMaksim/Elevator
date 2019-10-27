import java.util.LinkedList;

class Elevator {

    final static int MAX_PASSENGERS = 5;
    final static int MAX_FLOORS = 5 + (int) (Math.random() * 16);
    private LinkedList<Passenger> cabin = new LinkedList<>();
    private Way wayElevator;
    private Printer printer = new Printer();
    private int floor = 1;
    private int countUp = 0;
    private int countDown = 0;

    /**
     * Initialize Elevator way and fills Elevator cabin
     */
    public Elevator(){
        this.wayElevator = Way.UP;
        this.fillEmptyElevator();
    }

    /**
     * Fills empty Elevator
     */
    private void fillEmptyElevator(){
        LinkedList<Passenger> passengersOnFloor = Passenger.getPassengersInBuilding().get(this.floor - 1);
        LinkedList<Passenger> toRemove = new LinkedList<>();
        for (Passenger guy : passengersOnFloor) {
            if(this.checkSizeElevator()) {
                this.cabin.add(guy);
                toRemove.add(guy);
            }
        }
        passengersOnFloor.removeAll(toRemove);
    }

    /**
     * Elevator start
     */
    public void start(){
        int limiter = 0;
        do {
            while (checkMaxFloor()) {
                goUp();
            }
            while (checkMinFloor()) {
                goDown();
            }
            limiter++;
        }while (limiter != 2);
    }

    /**
     * Lifts the elevator up
     */
    private void goUp(){
        System.out.println(printer.printWork(this));
        if(!this.emptyElevator()) {
            this.wayElevator = Way.UP;
            goNextFloorUp();
            this.releasePassengers();
            this.letPassengersIn();
        }
    }

    /**
     * Lowers the elevator down
     */
    private void goDown(){
        if(!this.emptyElevator()) {
            System.out.println(printer.printWork(this));
            goNextFloorDown();
            this.releasePassengers();
            this.letPassengersIn();
        }
    }

    /**
     * Releases passengers from the elevator
     */
    private void releasePassengers(){
        if(checkFloor()){
            LinkedList<Passenger> toLeave = Passenger.getPassengersToLeave(this);
            Passenger.leaveTheElevator(this.cabin, this.floor, toLeave);
        }
    }

    /**
     * Let passengers in the elevator
     */
    private void letPassengersIn() {
        LinkedList<Passenger> toEnter = Passenger.getPassengersToEnter(this);
        Passenger.enterTheElevator(this.cabin, floor, toEnter, this);
    }

    /**
     * Check for similarity between the elevator floor and the passenger floor
     * @return true if floors are equal, false if not
     */
    private boolean checkFloor(){
        for (Passenger guy : this.cabin) {
            if(guy.getRequiredFloor() == this.floor){
                return true;
            }
        }
        return false;
    }

    /**
     * Check for empty cabin, if the cabin is empty, it is fills
     * @return true if the cabin is empty, false if not
     */
    private boolean emptyElevator(){
        if(this.cabin.size() == 0){
            fillEmptyElevator();
            this.changeWay();
            return true;
        }
        return false;
    }

    /**
     * Changes the way of the elevator
     */
    private void changeWay(){
        if(countUpDown()){
            this.wayElevator = Way.UP;
        }
        else{
            this.wayElevator = Way.DOWN;
        }
    }

    /**
     * Counting the number of people traveling up and down
     * @return  true if the number of people traveling up is greater
     */
    private boolean countUpDown(){
        for (Passenger guy : this.cabin) {
            if(guy.getWay() == Way.UP){
                this.countUp++;
            }
            else{
                this.countDown++;
            }
        }
        return this.countUp > this.countDown;
    }

    /**
     * Checks the number of passengers in the elevator, not exceeding MAX_PASSENGER
     * @return true if the number of passengers in the elevator does not exceed MAX_PASSENGER
     */
    public boolean checkSizeElevator(){
        return this.cabin.size() != MAX_PASSENGERS;
    }

    /**
     * Increment Elevator floor
     */
    private void goNextFloorUp(){
        this.floor++;
    }

    /**
     * Decrement Elevator floor
     */
    private void goNextFloorDown(){
        this.floor--;
    }

    /**
     * Return Elevator cabin
     * @return Elevator cabin
     */
    public LinkedList<Passenger> getCabin(){
        return this.cabin;
    }

    /**
     * Return Elevator way
     * @return Elevator way
     */
    public  Way getWayElevator(){
        return this.wayElevator;
    }

    /**
     * Return Elevator floor
     * @return Elevator floor
     */
    public int getFloor(){
        return this.floor;
    }

    /**
     * Check Elevator floor with MAX_FLOORS
     * @return true if floor is not equal MAX_FLOORS
     */
    private boolean checkMaxFloor(){
        return this.floor != MAX_FLOORS;
    }

    /**
     * Check Elevator floor with minimal floor = 1
     * @return true if floor is not equal minimal floor = 1
     */
    private boolean checkMinFloor(){
        return  this.floor != 1;
    }

}

