import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        var manager = new DealershipFileManager();
        var dealer = manager.getDealership();
        var ui = new UserInterface(dealer);
        ui.display();
    }
}