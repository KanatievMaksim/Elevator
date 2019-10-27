import java.util.LinkedList;

class Passenger {

    private static LinkedList<LinkedList<Passenger>> passengersInBuilding = new LinkedList<>();
    private Way way;
    private int requiredFloor;
    private int currentPosition;

    /**
     * Initialize current position< required floor and way
     * @param floor number of passenger current position
     */
    public Passenger(int floor){
        this.currentPosition = floor;
        this.setRequiredFloor();
        this.setRandomPassengerWay();
    }

    /**
     * Create passengers in the building
     */
    public static void createPassengersInBuilding(){
        for (int i = 0; i < Elevator.MAX_FLOORS; i++) {
            LinkedList<Passenger> passenger = new LinkedList<>();
            int passengersOnFloor = setRandomPassengersOnFloor();
            for (int j = 0; j < passengersOnFloor; j++) {
                passenger.add(new Passenger(i + 1));
            }
            passengersInBuilding.add(passenger);
        }
    }

    /**
     * Set random number of passenger on the floor
     * @return random number of passenger on the floor
     */
    private static int setRandomPassengersOnFloor(){
        return (int) (Math.random() * 11);
    }

    /**
     * Return passenger in the building
     * @return passenger in the building
     */
    public static LinkedList<LinkedList<Passenger>> getPassengersInBuilding(){
        return passengersInBuilding;
    }

    /**
     * Passenger leaves the elevator
     * @param cabin list of passengers in elevator cabin
     * @param floor number of elevator floor
     * @param toLeave list of passengers who go out
     */
    public static void leaveTheElevator(LinkedList<Passenger> cabin, int floor, LinkedList<Passenger> toLeave){
        cabin.removeAll(toLeave);
        for (int i = 0; i < toLeave.size(); i++) {
            Passenger.passengersInBuilding.get(floor - 1).add(new Passenger(floor));
        }
    }

    /**
     *
     * @param cabin list of passengers in elevator cabin
     * @param floor number of elevator floor
     * @param toEnter list of passengers who enter
     * @param elevator elevator
     */
    public static void enterTheElevator(LinkedList<Passenger> cabin, int floor, LinkedList<Passenger> toEnter, Elevator elevator){
        LinkedList<Passenger> passengerOnFloor = passengersInBuilding.get(floor - 1);
        for (int i = 0; i < toEnter.size(); i++) {
            if(elevator.checkSizeElevator()) {
                Passenger guy = toEnter.get(i);
                cabin.add(guy);
                passengerOnFloor.remove(guy);
            }
        }
    }

    /**
     * Get passengers from the elevator cabin and prepares for the exit
     * @param elevator elevator
     * @return list of passengers getting ready to exit
     */
    public static LinkedList<Passenger> getPassengersToLeave(Elevator elevator){
        LinkedList<Passenger> toLeave = new LinkedList<>();
        for (Passenger guy : elevator.getCabin()) {
            if(guy.getRequiredFloor() == elevator.getFloor()){
                toLeave.add(guy);
            }
        }
        return toLeave;
    }

    /**
     * Get passengers from the elevator cabin and prepares for entry
     * @param elevator elevator
     * @return list of passengers who get ready for the entrance
     */
    public static LinkedList<Passenger> getPassengersToEnter(Elevator elevator){
        LinkedList<Passenger> passengerOnFloor = passengersInBuilding.get(elevator.getFloor() - 1);
        LinkedList<Passenger> toEnter = new LinkedList<>();
        for (Passenger guy : passengerOnFloor) {
            if(elevator.getWayElevator() == guy.getWay()){
                toEnter.add(guy);
            }
        }
        return toEnter;
    }


    /**
     * Required passenger floor
     * @return number of required passenger floor
     */
    public int getRequiredFloor(){
        return this.requiredFloor;
    }

    /**
     * Set random way for passenger
     */
    private void setRandomPassengerWay(){
        if(this.currentPosition < this.requiredFloor){
            this.way = Way.UP;
        } else if(this.currentPosition > this.requiredFloor){
            this.way = Way.DOWN;
        }
    }

    /**
     *Set required floor for passenger
     */
    private void setRequiredFloor(){
        this.requiredFloor = 1 + (int) (Math.random() * Elevator.MAX_FLOORS);
        while(this.requiredFloor == this.currentPosition){
            this.requiredFloor = 1 + (int) (Math.random() * Elevator.MAX_FLOORS);
        }
    }

    /**
     * Get passenger way
     * @return passenger way
     */
    public Way getWay(){
        return this.way;
    }

}


