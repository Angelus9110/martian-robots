package com.martianrobots;

public enum Orientation {
    N,
    E,
    S,
    W;

    public static Orientation rotateClockwise(Orientation orientation) {

        switch(orientation) {
            case N: return E;
            case E: return S;
            case S: return W;
            case W: return N;
        }

        return null;
    }

    public static Orientation rotateAntiClockwise(Orientation orientation) {

        switch(orientation) {
            case N: return W;
            case E: return N;
            case S: return E;
            case W: return S;
        }

        return null;
    }
}
