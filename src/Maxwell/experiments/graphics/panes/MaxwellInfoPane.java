package Maxwell.experiments.graphics.panes;


import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MaxwellInfoPane extends InfoPane {

    private JButton stopButton;
    private Map<String, JLabel> labelMap = new HashMap<>();
    private Map<String, JTextField> textFieldMap = new HashMap<>();

    public MaxwellInfoPane(java.util.List<Atom> atoms) {
        super(atoms);

        stopButton = new JButton("Остановить");
        stopButton.setPreferredSize(new Dimension(200, 50));
        stopButton.setBorderPainted(false);

        labelMap.put("rms", new JLabel("Среднеквадратичная скорость :"));
        labelMap.put("avg", new JLabel("Средняя скорость :"));
        labelMap.put("prob", new JLabel("Наивероятнейшая скорость :"));
        labelMap.put("disp", new JLabel("Среднеквадратичное отклонение :"));
        labelMap.put("percent", new JLabel("Процент частиц в области 2σ : "));

        textFieldMap.put("rms", new JTextField());
        textFieldMap.put("avg", new JTextField());
        textFieldMap.put("prob", new JTextField());
        textFieldMap.put("disp", new JTextField());
        textFieldMap.put("percent", new JTextField());

        JPanel labelPanel = new JPanel(new GridBagLayout());
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        labelPanel.setBackground(Color.lightGray);
        fieldsPanel.setBackground(Color.lightGray);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 0;
        gbc.gridheight = 1;

        for (String key : labelMap.keySet()) {
            JLabel label = labelMap.get(key);
            label.setPreferredSize(new Dimension(450, 50));
            labelPanel.add(label, gbc);
        }

        for (String key : textFieldMap.keySet()) {
            JTextField textField = textFieldMap.get(key);
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setPreferredSize(new Dimension(100, 50));
            textField.setEditable(false);
            fieldsPanel.add(textField, gbc);
        }


        add(labelPanel);
        add(fieldsPanel);
        add(stopButton, CENTER_ALIGNMENT);

    }



    @Override
    public void addListener(ActionListener listener) {
        stopButton.setActionCommand("stop");
        stopButton.addActionListener(listener);
    }



    private double getProbSpeed(int numberOfBars, double rmsSpeed) {

        double resolution = 4.5 * Math.sqrt(rmsSpeed) / numberOfBars;
        ArrayList<Integer> distribution = new ArrayList<>(numberOfBars);
        int maxIndex = 0;
        int maxValue = 0;
        for (int i = 0; i < numberOfBars; i++)
            distribution.add(i, 0);
        for (Atom atom : atoms) {
            int barIndex = (int) Math.floor(Math.sqrt(atom.vx * atom.vx + atom.vy * atom.vy) / resolution);
            if (barIndex >= numberOfBars) {
                continue;
            }
            distribution.set(barIndex, distribution.get(barIndex) + 1);
        }

        for (int i = 0; i < distribution.size(); ++i) {
            if (distribution.get(i) > maxValue) {
                maxValue = distribution.get(i);
                maxIndex = i;
            }
        }

        return resolution * (2 * maxIndex + 1) / 2;
    }



    @Override
    public void update() {
        double rmsSpeed = 0;
        double avgSpeed = 0;
        double dispersion = 0;
        double probSpeed = 0;
        int numInTwoSigma = 0;
        int numberOfAtoms = atoms.size();

        for (Atom atom : atoms) {
            double speedSquare = Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2);
            avgSpeed += Math.sqrt(speedSquare);
            rmsSpeed += speedSquare;
        }

        probSpeed = getProbSpeed(60, rmsSpeed / numberOfAtoms);
        avgSpeed /= numberOfAtoms;
        rmsSpeed = Math.sqrt(rmsSpeed / numberOfAtoms);

        for (Atom atom : atoms) {
            dispersion += Math.pow(Math.sqrt(Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2)) - avgSpeed, 2);
        }

        dispersion = Math.sqrt(dispersion / numberOfAtoms);

        for (Atom atom : atoms) {
            double speed = Math.sqrt(Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2));

            if (speed < avgSpeed + 2 * dispersion && speed > avgSpeed - 2 * dispersion) {
                ++numInTwoSigma;
            }
        }

        textFieldMap.get("rms").setText(String.valueOf(Math.round(rmsSpeed)));
        textFieldMap.get("avg").setText(String.valueOf(Math.round(avgSpeed)));
        textFieldMap.get("prob").setText(String.valueOf(Math.round(probSpeed)));
        textFieldMap.get("disp").setText(String.valueOf(Math.round(dispersion)));
        textFieldMap.get("percent")
                    .setText(String.valueOf(Math.round(numInTwoSigma / (double)numberOfAtoms * 100)) + "%");
    }
}
