package basic;

import exceptions.SolverException;
import model.Vector;

public interface Core {
    void start();
    Vector solveSystem() throws SolverException;
}
