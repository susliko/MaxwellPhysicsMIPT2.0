package Maxwell.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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



    public int getVelocity() {
        while (!pane.gotcha) {
            System.out.print("");
        }
        return pane.velocity;
    }
}


class DialogPane extends JPanel
                 implements ActionListener {

    final int HEIGHT = 200;
    final int WIDTH = 400;

    private JFormattedTextField field;
    int velocity;
    boolean gotcha = false;

    DialogPane() {
        setBackground(Color.black);


        field = new JFormattedTextField(NumberFormat.getIntegerInstance());
        field.setPreferredSize(new Dimension(100, 50));
        field.setFont(new Font("Arial", Font.BOLD, 20));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setActionCommand("run");
        field.addActionListener(this);

        JLabel label = new JLabel("Enter velocity");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBackground(Color.BLACK);
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);

        JButton button = new JButton("Start!");
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setActionCommand("run");
        button.addActionListener(this);

        JPanel fieldPane = new JPanel(new GridLayout(0,1));

        fieldPane.add(label);
        fieldPane.add(field);
        fieldPane.add(button);
        add(fieldPane, BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equals("run")) {
            System.out.println(field.getText());
            gotcha = true;
            velocity = Integer.parseInt(field.getText());
        }
    }
}