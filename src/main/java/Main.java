import exceptions.DataException;
import solver.GaussSolver;
import ui.processor.CommandLineCore;

public class Main {
    public static void main(String[] args) {
        try {
            final CommandLineCore core = new CommandLineCore(new GaussSolver());
            core.start();
        } catch (DataException e) {

            System.out.println(e.getMessage());
        }
    }
}
