package Maxwell.frames;

import Maxwell.ExpType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;


public class Dialog extends JFrame {

    private DialogPane pane;

    public Dialog() {
        super("Maxwell");
        pane = new DialogPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - pane.WIDTH) / 2, (screenSize.height - pane.HEIGHT) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(pane);
        pack();
    }



    public void setListener(ActionListener listener) {
        pane.setListener(listener);
    }



    public int getVelocity() {
        return pane.getVelocity();
    }



    public int getN() {
        return pane.getN();
    }



    public ExpType getExperiment() {
        return pane.getExperiment();
    }
}



class DialogPane extends JPanel {

    final int HEIGHT = 400;
    final int WIDTH = 400;

    private final String[] experiments = { "Maxwell", "Boltzmann", "Knudsen" };

    private JLabel velocityLabel;
    private JLabel numberLabel;
    private JFormattedTextField velocityField;
    private JFormattedTextField numberField;
    private JButton button;
    private JComboBox experimentField;

    int velocity;
    int N;



    DialogPane() {
        setBackground(Color.black);

        experimentField = new JComboBox(experiments);
        experimentField.setFont(new Font("Arial", Font.BOLD, 20));
        ((JLabel)experimentField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        velocityLabel = new JLabel("Enter velocity");
        velocityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        velocityLabel.setBackground(Color.BLACK);
        velocityLabel.setForeground(Color.white);
        velocityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        velocityLabel.setOpaque(true);

        numberLabel = new JLabel("Enter number of atoms");
        numberLabel.setFont(new Font("Arial", Font.BOLD, 20));
        numberLabel.setBackground(Color.BLACK);
        numberLabel.setForeground(Color.white);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setOpaque(true);

        velocityField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        velocityField.setPreferredSize(new Dimension(100, 50));
        velocityField.setFont(new Font("Arial", Font.BOLD, 20));
        velocityField.setHorizontalAlignment(SwingConstants.CENTER);

        numberField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        numberField.setPreferredSize(new Dimension(100, 50));
        numberField.setFont(new Font("Arial", Font.BOLD, 20));
        numberField.setHorizontalAlignment(SwingConstants.CENTER);

        button = new JButton("Start!");
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel fieldPane = new JPanel(new GridLayout(0,1));

        fieldPane.add(experimentField);
        fieldPane.add(velocityLabel);
        fieldPane.add(velocityField);
        fieldPane.add(numberLabel);
        fieldPane.add(numberField);
        fieldPane.add(button);
        add(fieldPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
    }


    ExpType experiment;



    int getVelocity() {
        try {
            velocity = Integer.parseInt(velocityField.getText().replace(",", ""));
        } catch (NumberFormatException ignored) { return 0; }
        return velocity;
    }



    int getN() {
        try {
            N = Integer.parseInt(numberField.getText().replace(",", ""));
        } catch (NumberFormatException ignored) { return 0; }
        return N;
    }



    ExpType getExperiment() {
        String exp = experimentField.getSelectedItem().toString();
        switch (exp) {
            case "Maxwell" :
                experiment = ExpType.MAXWELL;
                break;
            case "Boltzmann" :
                experiment = ExpType.BOLTZMANN;
                break;
            case "Knudsen" :
                experiment = ExpType.KNUDSEN;
                break;
        }
        return experiment;
    }


    void setListener(ActionListener listener) {
        button.setActionCommand("run");
        numberField.setActionCommand("run");
        button.addActionListener(listener);
        numberField.addActionListener(listener);
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }
}