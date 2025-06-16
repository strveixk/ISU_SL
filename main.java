// stephen lee isu
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        ArrayList<String> serviceNames = new ArrayList<>();
        ArrayList<String> serviceCategories = new ArrayList<>();
        ArrayList<Double> serviceRates = new ArrayList<>();
        ArrayList<Boolean> serviceAvailable = new ArrayList<>();
        ArrayList<Integer> cart = new ArrayList<>();
        ArrayList<Double> cartHours = new ArrayList<>(); // Array lists initialized for the marketplace


        boolean running = true;


        while (running) {
            System.out.println("\nLocal Service Marketplace"); // Sets out menu options
            System.out.println("1 - Add a Service");
            System.out.println("2 - Book a Service");
            System.out.println("3 - View Cart");
            System.out.println("4 - Checkout");
            System.out.println("5 - Exit");
            System.out.print("Choose an option: ");
            String option = input.nextLine();


            if (option.equals("1")) { // prompts service creation
                boolean adding = true;
                while (adding) {
                    System.out.print("Enter service name: ");
                    String name = input.nextLine().trim();


                    boolean nameExists = false;
                    for (int i = 0; i < serviceNames.size(); i++) { // checks if name exists
                        if (serviceNames.get(i).equalsIgnoreCase(name)) {
                            nameExists = true;
                        }
                    }


                    if (nameExists) { // if name exists, reloops code
                        System.out.println("A service with this name already exists.");
                    } else {
                        System.out.print("Enter category: "); // if doesnt exist, prompts category entry
                        String category = input.nextLine().trim();


                        boolean categoryExists = false;
                        for (int i = 0; i < serviceCategories.size(); i++) { // checks if category exists
                            if (serviceCategories.get(i).equalsIgnoreCase(category)) {
                                categoryExists = true;
                            }
                        }


                        System.out.print("Enter hourly rate: ");
                        double rate = input.nextDouble();
                        input.nextLine();


                        serviceNames.add(name);
                        serviceCategories.add(category);
                        serviceRates.add(rate);
                        serviceAvailable.add(true);


                        System.out.println("Service added: " + name);
                    }


                    System.out.print("Add another service? (y/n): ");
                    String more = input.nextLine();
                    if (!more.equalsIgnoreCase("y")) {
                        adding = false;
                    }
                }


            } else if (option.equals("2")) { // Shows any available categories to show and prompts user to choose
                ArrayList<String> displayedCategories = new ArrayList<>();
                for (int i = 0; i < serviceCategories.size(); i++) {
                    String lower = serviceCategories.get(i).toLowerCase();
                    boolean exists = false;
                    for (int j = 0; j < displayedCategories.size(); j++) {
                        if (displayedCategories.get(j).equals(lower)) {
                            exists = true;
                        }
                    }
                    if (!exists) {
                        displayedCategories.add(lower);
                    }
                }


                if (displayedCategories.size() == 0) { // if no categories
                    System.out.println("No services available to book.");
                } else {
                    System.out.println("\nAvailable Categories:");
                    for (int i = 0; i < displayedCategories.size(); i++) {
                        System.out.println((i + 1) + " - " + displayedCategories.get(i));
                    }


                    System.out.print("Select category number: "); // enters category
                    int categoryChoice = input.nextInt() - 1;
                    input.nextLine();


                    if (categoryChoice >= 0 && categoryChoice < displayedCategories.size()) {
                        String selectedCategory = displayedCategories.get(categoryChoice);
                        ArrayList<Integer> matches = new ArrayList<>(); // list to detect matches between service and category


                        for (int i = 0; i < serviceNames.size(); i++) {
                            if (serviceCategories.get(i).equalsIgnoreCase(selectedCategory)) {
                                matches.add(i); // saves the index of services in the selected category
                            }
                        }


                        if (matches.size() == 0) {
                            System.out.println("No services found in that category.");
                        } else {
                            System.out.println("\nAvailable Services:"); // displays categories with their information
                            for (int i = 0; i < matches.size(); i++) {
                                int idx = matches.get(i);
                                String status = serviceAvailable.get(idx) ? "" : "(Fully Booked)";
                                System.out.print(String.format("%d. %s - $%.2f/hr %s\n", (i + 1), serviceNames.get(idx), serviceRates.get(idx), status));
                            }


                            System.out.print("Choose service number to book: ");
                            int serviceChoice = input.nextInt() - 1;
                            input.nextLine();


                            if (serviceChoice >= 0 && serviceChoice < matches.size()) {
                                int realIdx = matches.get(serviceChoice);


                                if (!serviceAvailable.get(realIdx)) { // checks if booked
                                    System.out.println("That service is fully booked.");
                                } else {
                                    System.out.print("How many hours do you want to book? ");
                                    double hours = input.nextDouble();
                                    input.nextLine();


                                    cart.add(realIdx); //adds service to cart
                                    cartHours.add(hours); //adds hours to cart
                                    serviceAvailable.set(realIdx, false);
                                    System.out.println("Service added to cart.");
                                }
                            } else {
                                System.out.println("Invalid service selection.");
                            }
                        }


                    } else {
                        System.out.println("Invalid category selection.");
                    }
                }


            } else if (option.equals("3")) { // This section displays everything in the cart, including hours and price
                System.out.println("\nYour Cart");
                double total = 0.0;
                for (int i = 0; i < cart.size(); i++) {
                    int idx = cart.get(i);
                    double hours = cartHours.get(i);
                    double subtotal = serviceRates.get(idx) * hours;
                    total += subtotal;
                    System.out.print(String.format("%d. %s - $%.2f/hr x %.1f hrs = $%.2f\n", (i + 1), serviceNames.get(idx), serviceRates.get(idx), hours, subtotal));
                }
                System.out.print(String.format("Total: $%.2f\n", total));

                if (cart.size() > 0) { // This section prompts the user if they want to remove a service from the cart, only if they have 1+ service
                    System.out.print("Do you want to remove a service? (y/n): ");
                    String remove = input.nextLine();

                    if (remove.equalsIgnoreCase("y")) {
                        System.out.print("Enter the number of the service to remove: ");
                        int removeIndex = input.nextInt() - 1;
                        input.nextLine();

                        if (removeIndex >= 0 && removeIndex < cart.size()) {
                            int serviceIndex = cart.get(removeIndex);
                            serviceAvailable.set(serviceIndex, true); // make it available again

                            cart.remove(removeIndex);
                            cartHours.remove(removeIndex);
                            System.out.println("Service removed from cart.");
                        } else {
                            System.out.println("Invalid selection.");
                        }
                    }
                }
            } else if (option.equals("4")) { // Displays the checkout with everything in the cart and cost
                System.out.println("\nInvoice");
                double total = 0.0;
                for (int i = 0; i < cart.size(); i++) {
                    int idx = cart.get(i);
                    double hours = cartHours.get(i);
                    double subtotal = serviceRates.get(idx) * hours;
                    total += subtotal;
                    System.out.print(String.format("%d. %s - $%.2f/hr x %.1f hrs = $%.2f\n", (i + 1), serviceNames.get(idx), serviceRates.get(idx), hours, subtotal));
                }
                System.out.print(String.format("Total Amount Due: $%.2f\n", total));
                System.out.println("Thank you for booking!");


                for (int i = cart.size() - 1; i >= 0; i--) { // removes everything in cart after checkout
                    cart.remove(i);
                    cartHours.remove(i);
                }


            } else if (option.equals("5")) { // ends program
                System.out.println("Thank you for using the marketplace!");
                running = false;


            } else { // invalid menu option and loops again
                System.out.println("Invalid menu option.");
            }
        }
    }
}
