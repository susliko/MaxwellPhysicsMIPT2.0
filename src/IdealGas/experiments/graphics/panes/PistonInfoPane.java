package IdealGas.experiments.graphics.panes;

import IdealGas.experiments.Experiment;
import IdealGas.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;

public class PistonInfoPane extends InfoPane {
    public PistonInfoPane(java.util.List<Atom> atoms) {
        super(atoms);

        labelMap.put("rms", new JLabel("Среднеквадратичная скорость :"));
        labelMap.put("avg", new JLabel("Средняя скорость :"));
        labelMap.put("accel", new JLabel("Ускорение (пикс / сек ^ 2) :"));
        labelMap.put("weight", new JLabel("Отношение M поршня к m атома :"));

        textFieldMap.put("rms", new JTextField());
        textFieldMap.put("avg", new JTextField());
        textFieldMap.put("accel", new JTextField());
        textFieldMap.get("accel").setText("100");
        textFieldMap.put("weight", new JTextField());

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

        JButton increaseMassButt = new JButton("x4");
        increaseMassButt.setPreferredSize(new Dimension(100, 50));
        increaseMassButt.setBorderPainted(false);
        increaseMassButt.addActionListener(e -> Experiment.pistonWeight *= 4);
        add(increaseMassButt, CENTER_ALIGNMENT);

        JButton decreaseMassButt = new JButton("x0.25");
        decreaseMassButt.setPreferredSize(new Dimension(100, 50));
        decreaseMassButt.addActionListener(e -> Experiment.pistonWeight /= 4);
        decreaseMassButt.setBorderPainted(false);
        add(decreaseMassButt, CENTER_ALIGNMENT);

        add(stopButton, CENTER_ALIGNMENT);
    }



    @Override
    public void update() {
        double rmsSpeed = getRmsSpeed();
        double avgSpeed = getAvgSpeed();

        textFieldMap.get("rms").setText(String.valueOf(Math.round(rmsSpeed)));
        textFieldMap.get("avg").setText(String.valueOf(Math.round(avgSpeed)));
        textFieldMap.get("weight").setText(String.valueOf(Experiment.pistonWeight));
    }
}
