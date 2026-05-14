package com.mycompany.flightsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AirlineGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private addFlight flightManager = new addFlight();
    private booking bookingSystem = new booking();

    // Pastel blue palette
    private final Color pastelBlue = new Color(179, 198, 231);
    private final Color pastelBlueDark = new Color(127, 161, 214);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AirlineGUI::new);
    }

    public AirlineGUI() {
        // Setup initial flights
        flightManager.addFlight(new flight("FL123", "New York", "Los Angeles", "10:00 AM", 5));
        flightManager.addFlight(new flight("FL456", "Paris", "Berlin", "2:00 PM", 3));

        frame = new JFrame("NOGOM Airport Flight System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 550);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "login");
        mainPanel.add(menuPanel(), "menu");
        mainPanel.add(viewFlightsPanel(), "viewFlights");
        mainPanel.add(bookFlightPanel(), "bookFlight");
        mainPanel.add(cancelFlightPanel(), "cancelFlight");
        mainPanel.add(addFlightPanel(), "addFlight");
        mainPanel.add(viewPassengersPanel(), "viewPassengers");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    private JPanel loginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(60, 60, 60, 60));

        JLabel welcome = new JLabel("Welcome to NOGOM airport", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        welcome.setForeground(pastelBlueDark);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passLabel = new JLabel("Enter the password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passField = new JPasswordField(15);
        passField.setMaximumSize(new Dimension(200, 30));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(pastelBlueDark);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedback = new JLabel(" ");
        feedback.setForeground(Color.RED);
        feedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> {
            String pass = new String(passField.getPassword());
            if (pass.equals("sim123")) {
                cardLayout.show(mainPanel, "menu");
            } else {
                feedback.setText("Wrong password. Try again.");
            }
        });

        panel.add(welcome);
        panel.add(Box.createVerticalStrut(30));
        panel.add(passLabel);
        panel.add(passField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(feedback);

        return panel;
    }

    private JPanel menuPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(60, 60, 60, 60));

        JLabel title = new JLabel("NOGOM Airport - Main Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(pastelBlueDark);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton viewFlightsBtn = new JButton("View Flights");
        JButton bookFlightBtn = new JButton("Book Flight");
        JButton cancelFlightBtn = new JButton("Cancel Flight");
        JButton viewPassengersBtn = new JButton("View Passengers");
        JButton addFlightBtn = new JButton("Add Flight");

        for (JButton btn : new JButton[]{viewFlightsBtn, bookFlightBtn, cancelFlightBtn, viewPassengersBtn}) {
            btn.setBackground(pastelBlueDark);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(220, 40));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel.add(Box.createVerticalStrut(10));
            panel.add(btn);
        }

        addFlightBtn.setBackground(pastelBlueDark);
        addFlightBtn.setForeground(Color.WHITE);
        addFlightBtn.setFont(new Font("Arial", Font.BOLD, 16));
        addFlightBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFlightBtn.setMaximumSize(new Dimension(220, 40));
        addFlightBtn.setFocusPainted(false);
        addFlightBtn.setBorderPainted(false);
        addFlightBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addFlightBtn.addActionListener(e -> cardLayout.show(mainPanel, "addFlight"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(addFlightBtn);

        viewFlightsBtn.addActionListener(e -> {
            refreshFlightsList();
            cardLayout.show(mainPanel, "viewFlights");
        });
        bookFlightBtn.addActionListener(e -> {
            refreshBookFlightPanel();
            cardLayout.show(mainPanel, "bookFlight");
        });
        cancelFlightBtn.addActionListener(e -> {
            refreshCancelFlightPanel();
            cardLayout.show(mainPanel, "cancelFlight");
        });
        viewPassengersBtn.addActionListener(e -> {
            refreshViewPassengersPanel();
            cardLayout.show(mainPanel, "viewPassengers");
        });

        panel.add(Box.createVerticalStrut(20));
        panel.add(title);

        return panel;
    }

    // --- View Flights Panel ---
    private DefaultListModel<String> flightsListModel = new DefaultListModel<>();
    private JList<String> flightsList = new JList<>(flightsListModel);

    private JPanel viewFlightsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(pastelBlue);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Available Flights", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(pastelBlueDark);

        flightsList.setFont(new Font("Arial", Font.PLAIN, 14));
        flightsList.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(flightsList);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(pastelBlueDark);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshFlightsList() {
        flightsListModel.clear();
        String flightsText = flightManager.printAllFlights();
        if (!flightsText.equals("No flights available.")) {
            String[] flightLines = flightsText.split("\n");
            for (String line : flightLines) flightsListModel.addElement(line);
        }
    }

    // --- Book Flight Panel ---
    private JComboBox<String> flightComboBoxBook = new JComboBox<>();
    private JComboBox<String> seatTypeComboBox = new JComboBox<>(new String[]{"VIP", "Economy"});
    private JComboBox<String> paymentComboBoxBook = new JComboBox<>(new String[]{"Card", "Cash"});
    private JLabel bookFeedback = new JLabel(" ");

    private JPanel bookFlightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Book a Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(pastelBlueDark);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(250, 40));
        nameField.setBorder(BorderFactory.createTitledBorder("Passenger Name"));

        JTextField idField = new JTextField();
        idField.setMaximumSize(new Dimension(250, 40));
        idField.setBorder(BorderFactory.createTitledBorder("Passenger ID"));

        flightComboBoxBook.setMaximumSize(new Dimension(250, 30));
        seatTypeComboBox.setMaximumSize(new Dimension(250, 30));
        paymentComboBoxBook.setMaximumSize(new Dimension(250, 30));

        JButton bookBtn = new JButton("Book Flight");
        bookBtn.setBackground(pastelBlueDark);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        bookFeedback.setForeground(new Color(0, 128, 0));
        bookFeedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        bookBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            int fIndex = flightComboBoxBook.getSelectedIndex();
            String seatType = (String) seatTypeComboBox.getSelectedItem();
            String payment = (String) paymentComboBoxBook.getSelectedItem();
            if (!name.isEmpty() && !id.isEmpty() && fIndex >= 0 && seatType != null && payment != null) {
                passenger p = new passenger(name, id);
                flight[] flights = flightManager.getFlightList();
                if (fIndex < flights.length) {
                    flight f = flights[fIndex];
                    bookingSystem.bookFlight(p, f, seatType, payment);
                    bookFeedback.setForeground(new Color(0, 128, 0));
                    bookFeedback.setText("Booking successful!");
                    nameField.setText("");
                    idField.setText("");
                } else {
                    bookFeedback.setForeground(Color.RED);
                    bookFeedback.setText("Invalid flight selection.");
                }
            } else {
                bookFeedback.setForeground(Color.RED);
                bookFeedback.setText("Please fill all fields and select all options.");
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(pastelBlueDark);
        backBtn.setForeground(Color.WHITE);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nameField);
        panel.add(idField);
        panel.add(flightComboBoxBook);
        panel.add(seatTypeComboBox);
        panel.add(paymentComboBoxBook);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bookBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bookFeedback);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backBtn);

        return panel;
    }

    private void refreshBookFlightPanel() {
        flightComboBoxBook.removeAllItems();
        flight[] flights = flightManager.getFlightList();
        for (flight f : flights) {
            flightComboBoxBook.addItem(f.getFlightId() + " (" + f.getDeparture() + " -> " + f.getDestination() + ")");
        }
        bookFeedback.setText(" ");
    }

    // --- Cancel Flight Panel ---
    private JComboBox<String> flightComboBoxCancel = new JComboBox<>();
    private JComboBox<String> paymentComboBoxCancel = new JComboBox<>(new String[]{"Card", "Cash"});
    private JLabel cancelFeedback = new JLabel(" ");

    private JPanel cancelFlightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Cancel a Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(pastelBlueDark);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameFieldCancel = new JTextField();
        nameFieldCancel.setMaximumSize(new Dimension(250, 40));
        nameFieldCancel.setBorder(BorderFactory.createTitledBorder("Passenger Name"));

        JTextField idFieldCancel = new JTextField();
        idFieldCancel.setMaximumSize(new Dimension(250, 40));
        idFieldCancel.setBorder(BorderFactory.createTitledBorder("Passenger ID"));

        flightComboBoxCancel.setMaximumSize(new Dimension(250, 30));
        paymentComboBoxCancel.setMaximumSize(new Dimension(250, 30));

        JButton cancelBtn = new JButton("Cancel Flight");
        cancelBtn.setBackground(pastelBlueDark);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancelFeedback.setForeground(new Color(0, 128, 0));
        cancelFeedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        cancelBtn.addActionListener(e -> {
            String name = nameFieldCancel.getText().trim();
            String id = idFieldCancel.getText().trim();
            int fIndex = flightComboBoxCancel.getSelectedIndex();
            String payment = (String) paymentComboBoxCancel.getSelectedItem();
            if (!name.isEmpty() && !id.isEmpty() && fIndex >= 0 && payment != null) {
                passenger p = new passenger(name, id);
                flight[] flights = flightManager.getFlightList();
                if (fIndex < flights.length) {
                    flight f = flights[fIndex];
                    bookingSystem.cancelFlight(p, f, payment);
                    cancelFeedback.setForeground(new Color(0, 128, 0));
                    cancelFeedback.setText("Cancellation processed!");
                    nameFieldCancel.setText("");
                    idFieldCancel.setText("");
                } else {
                    cancelFeedback.setForeground(Color.RED);
                    cancelFeedback.setText("Invalid flight selection.");
                }
            } else {
                cancelFeedback.setForeground(Color.RED);
                cancelFeedback.setText("Please fill all fields and select all options.");
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(pastelBlueDark);
        backBtn.setForeground(Color.WHITE);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(nameFieldCancel);
        panel.add(idFieldCancel);
        panel.add(flightComboBoxCancel);
        panel.add(paymentComboBoxCancel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelFeedback);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backBtn);

        return panel;
    }

    private void refreshCancelFlightPanel() {
        flightComboBoxCancel.removeAllItems();
        flight[] flights = flightManager.getFlightList();
        for (flight f : flights) {
            flightComboBoxCancel.addItem(f.getFlightId() + " (" + f.getDeparture() + " -> " + f.getDestination() + ")");
        }
        cancelFeedback.setText(" ");
    }

    // --- View Passengers Panel ---
    private JComboBox<String> flightComboBoxViewPassengers = new JComboBox<>();
    private JTextArea passengersTextArea = new JTextArea();
    private JLabel viewPassengersFeedback = new JLabel(" ");

    private JPanel viewPassengersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("View Passengers (Booking History) per Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(pastelBlueDark);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        flightComboBoxViewPassengers.setMaximumSize(new Dimension(350, 30));
        JButton loadBtn = new JButton("Load Passengers");
        loadBtn.setBackground(pastelBlueDark);
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        passengersTextArea.setEditable(false);
        passengersTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(passengersTextArea);
        scroll.setPreferredSize(new Dimension(600, 300));

        loadBtn.addActionListener(e -> {
            int idx = flightComboBoxViewPassengers.getSelectedIndex();
            if (idx < 0) {
                viewPassengersFeedback.setText("Please select a flight.");
                return;
            }
            flight[] flights = flightManager.getFlightList();
            if (idx >= flights.length) {
                viewPassengersFeedback.setText("Invalid selection.");
                return;
            }
            flight f = flights[idx];
            // Show in GUI
            String list = f.getPassengerQueue().printQueue();
            passengersTextArea.setText(list);
            viewPassengersFeedback.setForeground(new Color(0, 128, 0));
            viewPassengersFeedback.setText("Loaded passengers for " + f.getFlightId());

            // Also print to console (console booking history)
            System.out.println("=== Booking history for flight " + f.getFlightId() + " ===");
            System.out.println(list);
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(pastelBlueDark);
        backBtn.setForeground(Color.WHITE);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(flightComboBoxViewPassengers);
        panel.add(Box.createVerticalStrut(8));
        panel.add(loadBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(scroll);
        panel.add(Box.createVerticalStrut(8));
        panel.add(viewPassengersFeedback);
        panel.add(Box.createVerticalStrut(8));
        panel.add(backBtn);

        return panel;
    }

    private void refreshViewPassengersPanel() {
        flightComboBoxViewPassengers.removeAllItems();
        flight[] flights = flightManager.getFlightList();
        for (flight f : flights) {
            flightComboBoxViewPassengers.addItem(f.getFlightId() + " (" + f.getDeparture() + " -> " + f.getDestination() + ")");
        }
        passengersTextArea.setText("");
        viewPassengersFeedback.setText(" ");
    }

    private JPanel addFlightPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(pastelBlue);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Add New Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(pastelBlueDark);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField();
        idField.setMaximumSize(new Dimension(200, 40));
        idField.setAlignmentX(Component.CENTER_ALIGNMENT);
        idField.setBorder(BorderFactory.createTitledBorder("Flight ID"));

        JTextField fromField = new JTextField();
        fromField.setMaximumSize(new Dimension(200, 40));
        fromField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fromField.setBorder(BorderFactory.createTitledBorder("Departure"));

        JTextField toField = new JTextField();
        toField.setMaximumSize(new Dimension(200, 40));
        toField.setAlignmentX(Component.CENTER_ALIGNMENT);

        toField.setBorder(BorderFactory.createTitledBorder("Destination"));

        JTextField timeField = new JTextField();
        timeField.setMaximumSize(new Dimension(200, 40));
        timeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeField.setBorder(BorderFactory.createTitledBorder("Take Off Time"));

        JTextField seatsField = new JTextField();
        seatsField.setMaximumSize(new Dimension(200, 40));
        seatsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        seatsField.setBorder(BorderFactory.createTitledBorder("Total Seats"));

        JButton addBtn = new JButton("Add Flight");
        addBtn.setBackground(pastelBlueDark);
        addBtn.setForeground(Color.WHITE);
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedback = new JLabel(" ");
        feedback.setForeground(new Color(0, 128, 0));
        feedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            String time = timeField.getText().trim();
            String seatsStr = seatsField.getText().trim();
            if (!id.isEmpty() && !from.isEmpty() && !to.isEmpty() && !time.isEmpty() && !seatsStr.isEmpty()) {
                try {
                    int seats = Integer.parseInt(seatsStr);
                    flight f = new flight(id, to, from, time, seats);
                    flightManager.addFlight(f);
                    feedback.setForeground(new Color(0, 128, 0));
                    feedback.setText("Flight added!");
                    idField.setText("");
                    fromField.setText("");
                    toField.setText("");
                    timeField.setText("");
                    seatsField.setText("");
                } catch (NumberFormatException ex) {
                    feedback.setForeground(Color.RED);
                    feedback.setText("Seats must be a number.");
                }
            } else {
                feedback.setForeground(Color.RED);
                feedback.setText("Please fill all fields.");
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(pastelBlueDark);
        backBtn.setForeground(Color.WHITE);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(idField);
        panel.add(fromField);
        panel.add(toField);
        panel.add(timeField);
        panel.add(seatsField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(feedback);
        panel.add(Box.createVerticalStrut(10));
        panel.add(backBtn);

        return panel;
    }
}
