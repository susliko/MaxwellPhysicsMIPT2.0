package Maxwell.experiments.graphics.panes;


import Maxwell.experiments.Experiment;
import Maxwell.experiments.physics.Atom;

import javax.swing.*;
import java.awt.*;


public class KnudsenInfoPane extends InfoPane {

    private int particlesRightTickAgo = 0;

    public KnudsenInfoPane(java.util.List<Atom> atoms) {
        super(atoms);

        labelMap.put("avg", new JLabel("Средняя скорость :"));
        labelMap.put("rms", new JLabel("Среднеквадратичная скорость :"));
        labelMap.put("Conl", new JLabel("Число частиц слева :"));
        labelMap.put("Conr", new JLabel("Число частиц справа :"));
        labelMap.put("Flow", new JLabel("Поток (шт/сек) :"));

        textFieldMap.put("rms", new JTextField());
        textFieldMap.put("avg", new JTextField());
        textFieldMap.put("Conl", new JTextField());
        textFieldMap.put("Conr", new JTextField());
        textFieldMap.put("Flow", new JTextField());

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



    int getConcentrationLeft() {
        int atomsLeft = 0;
        for (Atom atom : atoms) {
            if (atom.x <= Experiment.WIDTH / 2) {
                atomsLeft++;
            }
        }
        return atomsLeft;
    }



    int getConcentrationRight() {
        int atomsRight = 0;
        for (Atom atom : atoms) {
            if (atom.x > Experiment.WIDTH / 2) {
                atomsRight++;
            }
        }
        return atomsRight;
    }



    @Override
    public void update() {
        double rmsSpeed = getRmsSpeed();
        double avgSpeed = getAvgSpeed();
        int particlesLeft = getConcentrationLeft();
        int particlesRight = getConcentrationRight();
        int flow = particlesRight - particlesRightTickAgo;
        particlesRightTickAgo = particlesRight;

        textFieldMap.get("rms").setText(String.valueOf(Math.round(rmsSpeed)));
        textFieldMap.get("avg").setText(String.valueOf(Math.round(avgSpeed)));
        textFieldMap.get("Conl").setText(String.valueOf(Math.round(particlesLeft)));
        textFieldMap.get("Conr").setText(String.valueOf(Math.round(particlesRight)));
        textFieldMap.get("Flow").setText(String.valueOf(flow));
    }
}
