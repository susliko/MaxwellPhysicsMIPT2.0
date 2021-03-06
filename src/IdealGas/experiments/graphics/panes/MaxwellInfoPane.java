package IdealGas.experiments.graphics.panes;


import IdealGas.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MaxwellInfoPane extends InfoPane {

    public MaxwellInfoPane(java.util.List<Atom> atoms) {
        super(atoms);

        labelMap.put("Rms", new JLabel("Среднеквадратичная скорость :"));
        labelMap.put("Avg", new JLabel("Средняя скорость :"));
        labelMap.put("Prob", new JLabel("Наивероятнейшая скорость :"));
        labelMap.put("disp", new JLabel("Среднеквадратичное отклонение :"));
        labelMap.put("percent", new JLabel("Процент частиц в области 2σ : "));

        textFieldMap.put("Rms", new JTextField());
        textFieldMap.put("Avg", new JTextField());
        textFieldMap.put("Prob", new JTextField());
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



    double getProbSpeed(int numberOfBars, double rmsSpeed) {

        double resolution = 4.5 * rmsSpeed / numberOfBars;
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



    double getDispersion(double avgSpeed) {
        int numberOfAtoms = atoms.size();
        double dispersion = 0;
        for (Atom atom : atoms) {
            dispersion += Math.pow(Math.sqrt(Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2)) - avgSpeed, 2);
        }
        dispersion = Math.sqrt(dispersion / numberOfAtoms);
        return dispersion;
    }



    int getNumInTwoSigma(double avgSpeed, double dispersion) {
        int numInTwoSigma = 0;
        for (Atom atom : atoms) {
            double speed = Math.sqrt(Math.pow(atom.vx, 2) + Math.pow(atom.vy, 2));

            if (speed < avgSpeed + 2 * dispersion && speed > avgSpeed - 2 * dispersion) {
                ++numInTwoSigma;
            }
        }
        return numInTwoSigma;
    }



    @Override
    public void update() {
        double rmsSpeed = getRmsSpeed();
        double avgSpeed = getAvgSpeed();
        double probSpeed = getProbSpeed(60, rmsSpeed);
        double dispersion = getDispersion(avgSpeed);
        int numInTwoSigma = getNumInTwoSigma(avgSpeed, dispersion);
        int numberOfAtoms = atoms.size();

        textFieldMap.get("Rms").setText(String.valueOf(Math.round(rmsSpeed)));
        textFieldMap.get("Avg").setText(String.valueOf(Math.round(avgSpeed)));
        textFieldMap.get("Prob").setText(String.valueOf(Math.round(probSpeed)));
        textFieldMap.get("disp").setText(String.valueOf(Math.round(dispersion)));
        textFieldMap.get("percent")
                    .setText(String.valueOf(Math.round(numInTwoSigma / (double)numberOfAtoms * 100)) + "%");
    }
}
