import java.util.LinkedList;

class Printer {

    /**
     * Prints to the console Elevator MAX_FLOORS and MAX_PASSENGERS
     */
    public static void sayHello(){
        System.out.println("In building " + Elevator.MAX_FLOORS + " floors. " +
                "Elevator limit " + Elevator.MAX_PASSENGERS + " people.");
    }

    /**
     * Return combine Step(head), current floor, previous floor and next floor depending on the condition
     * @param elevator elevator
     * @return combine result depending on the condition
     */
    public StringBuilder printWork(Elevator elevator){
        int floorElevator = elevator.getFloor();
        StringBuilder head = new StringBuilder("\t ----Level " + floorElevator + " ----" + " Way " + elevator.getWayElevator());
        if(floorElevator == 1){
            return head.append(nextFloor(floorElevator)).append(currentFloor(elevator));
        }
        if(floorElevator == Elevator.MAX_FLOORS){
            return head.append(currentFloor(elevator)).append(previousFloor(floorElevator));
        }
        return head.append(nextFloor(floorElevator)).append(currentFloor(elevator)).append(previousFloor(floorElevator));
    }

    /**
     * Return result of the current floor
     * @param elevator elevator
     * @return result of the current floor
     */
    private static StringBuilder currentFloor(Elevator elevator){
        int floorElevator = elevator.getFloor();
        LinkedList<Passenger> cabin = elevator.getCabin();
        return new StringBuilder("\n " + floorElevator + " | " + printCollection(cabin) + "\t| " + printCollection(Passenger.getPassengersInBuilding(), floorElevator - 1));
    }

    /**
     * Return result of the previous floor
     * @param floorElevator elevator floor
     * @return result of the previous floor
     */
    private static StringBuilder previousFloor(int floorElevator){
        return new StringBuilder("\n " + (floorElevator - 1) + " | \t\t    | " + printCollection(Passenger.getPassengersInBuilding(), floorElevator - 2));
    }

    /**
     * Return result of the next floor
     * @param floorElevator elevator floor
     * @return result of the next floor
     */
    private static StringBuilder nextFloor(int floorElevator){
        return new StringBuilder("\n " + (floorElevator + 1) + " | \t\t    | " + printCollection(Passenger.getPassengersInBuilding(), floorElevator));
    }

    /**
     * Return list in row
     * @param cabin list of passengers in the cabin
     * @return result list in row
     */
    private static StringBuilder printCollection(LinkedList<Passenger> cabin){
        StringBuilder result = new StringBuilder();
        for (Passenger guy : cabin) {
            result.append(guy.getRequiredFloor()).append(" ");
        }
        return result;
    }

    /**
     * Return list in row
     * @param passengersInBuilding passengers in the building
     * @param floor elevator floor
     * @return list in row
     */
    private static StringBuilder printCollection(LinkedList<LinkedList<Passenger>> passengersInBuilding, int floor){
        LinkedList<Passenger> passengersOnFloor = passengersInBuilding.get(floor);
        return printCollection(passengersOnFloor);
    }

}
