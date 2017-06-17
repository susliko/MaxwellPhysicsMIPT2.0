package IdealGas.graphics.panes;

import IdealGas.ExpType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;



public class DialogPane extends JPanel {

    public final int HEIGHT = 400;
    public final int WIDTH = 400;

    private final String[] experiments = { "Максвелл", "Больцман", "Кнудсен", "Поршень"};

    private JLabel velocityLabel;
    private JLabel numberLabel;
    private JFormattedTextField velocityField;
    private JFormattedTextField numberField;
    private JButton button;
    private JComboBox experimentField;

    private int velocity;
    private int N;



    public DialogPane() {
        setBackground(Color.black);

        experimentField = new JComboBox(experiments);
        experimentField.setFont(new Font("Arial", Font.BOLD, 20));
        ((JLabel)experimentField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        velocityLabel = new JLabel("Скорость(пикс/сек)");
        velocityLabel.setFont(new Font("Arial", Font.BOLD, 20));
        velocityLabel.setBackground(Color.BLACK);
        velocityLabel.setForeground(Color.white);
        velocityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        velocityLabel.setOpaque(true);

        numberLabel = new JLabel("Число частиц");
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

        button = new JButton("Начать");
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


    private ExpType experiment;



    public int getVelocity() {
        try {
            velocity = Integer.parseInt(velocityField.getText().replace(",", ""));
        } catch (NumberFormatException ignored) { return 0; }
        return velocity;
    }



    public int getN() {
        try {
            N = Integer.parseInt(numberField.getText().replace(",", ""));
        } catch (NumberFormatException ignored) { return 0; }
        return N;
    }



    public ExpType getExperiment() {
        String exp = experimentField.getSelectedItem().toString();
        switch (exp) {
            case "Максвелл" :
                experiment = ExpType.MAXWELL;
                break;
            case "Больцман" :
                experiment = ExpType.BOLTZMANN;
                break;
            case "Кнудсен" :
                experiment = ExpType.KNUDSEN;
                break;
            case "Поршень" :
                experiment = ExpType.PISTON;
        }
        return experiment;
    }



    public void setListener(ActionListener listener) {
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