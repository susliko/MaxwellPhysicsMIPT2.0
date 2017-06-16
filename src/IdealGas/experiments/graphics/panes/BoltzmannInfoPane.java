package IdealGas.experiments.graphics.panes;

import IdealGas.experiments.Experiment;
import IdealGas.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;


public class BoltzmannInfoPane extends InfoPane {

    public BoltzmannInfoPane(java.util.List<Atom> atoms) {
        super(atoms);

        labelMap.put("rms", new JLabel("Среднеквадратичная скорость :"));
        labelMap.put("avg", new JLabel("Средняя скорость :"));
        labelMap.put("accel", new JLabel("Ускорение (пикс / сек ^ 2) :"));

        textFieldMap.put("rms", new JTextField());
        textFieldMap.put("avg", new JTextField());
        textFieldMap.put("accel", new JTextField());
        textFieldMap.get("accel").setText(String.valueOf(Math.round(Experiment.boltzmannAcceleration)));

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
    public void update() {
        double rmsSpeed = getRmsSpeed();
        double avgSpeed = getAvgSpeed();

        textFieldMap.get("rms").setText(String.valueOf(Math.round(rmsSpeed)));
        textFieldMap.get("avg").setText(String.valueOf(Math.round(avgSpeed)));
    }
}
