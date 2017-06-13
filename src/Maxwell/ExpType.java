package Maxwell;

public enum ExpType {
    MAXWELL {
        @Override
        public String toString() {
            return "MAXWELL";
        }
    },
    BOLTZMANN,
    KNUDSEN;

    int velocity;
    int N;
}
