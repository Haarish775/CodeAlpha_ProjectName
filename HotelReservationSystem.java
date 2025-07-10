import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String type;
    boolean isBooked;

    Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room #" + roomNumber + " - " + type + " - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    String customerName;
    Room room;

    Booking(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + ", Room: " + room.roomNumber + ", Type: " + room.type;
    }
}

public class HotelReservationSystem {
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadRooms();
        int choice;
        do {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1: viewAvailableRooms(); break;
                case 2: bookRoom(); break;
                case 3: cancelBooking(); break;
                case 4: viewBookings(); break;
                case 5: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    static void loadRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
        rooms.add(new Room(302, "Suite"));
    }

    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (!r.isBooked) {
                System.out.println(r);
            }
        }
    }

    static void bookRoom() {
        viewAvailableRooms();
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter room number to book: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();  // consume newline

        for (Room r : rooms) {
            if (r.roomNumber == roomNumber && !r.isBooked) {
                r.isBooked = true;
                bookings.add(new Booking(name, r));
                System.out.println("Room booked successfully!");
                return;
            }
        }
        System.out.println("Room not available or invalid number.");
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();
        Iterator<Booking> it = bookings.iterator();
        boolean found = false;
        while (it.hasNext()) {
            Booking b = it.next();
            if (b.customerName.equalsIgnoreCase(name)) {
                b.room.isBooked = false;
                it.remove();
                System.out.println("Booking cancelled.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No booking found under your name.");
        }
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            System.out.println("\nCurrent Bookings:");
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }
}
