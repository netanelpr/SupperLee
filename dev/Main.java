import Presentation.WelcomeMenu;
import Trans_HR.Business_Layer.Transportations.Utils.Buisness_Exception;

public class Main {
    public static void main (String[] argv) throws Buisness_Exception {
        WelcomeMenu menu = new WelcomeMenu();
        menu.newStart();
    }
}
