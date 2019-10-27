public class Main {

    public static void main(String[] args) {
        Printer.sayHello();
        Passenger.createPassengersInBuilding();
        Elevator elevator = new Elevator();
        elevator.start();
    }
}
