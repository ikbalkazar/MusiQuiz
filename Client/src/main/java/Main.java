import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

/**
 * Created by ikbalkazar on 13/12/16.
 */
public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Network.getRequest("/user/joey", new Network.Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
            public void whenError(String error) {
                System.err.println(error);
            }
        });

        Network.registerUser(new User("watson", "baker", "question?", "answer!!"), new Network.RegisterCompletion() {
            public void whenCompleted(boolean success) {
                System.err.println("Registration success: " + success);
            }

            public void whenError(String error) {
                System.err.println("ERROR: Registration error!!");
            }
        });
    }
}
