package basic;

import exceptions.DataException;
import exceptions.SolverException;
import model.LinearSystem;
import model.Vector;

public interface Core {
    void setSize(int size) throws DataException;
    void setLinearSystem(LinearSystem linearSystem) throws DataException;
    Vector solveSystem() throws SolverException;
    LinearSystem getData();
}
