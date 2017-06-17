package IdealGas;



public enum ExpType {
    MAXWELL {
        @Override
        public String toString() {
            return "MAXWELL";
        }
    },
    BOLTZMANN,
    KNUDSEN,
    PISTON
}
