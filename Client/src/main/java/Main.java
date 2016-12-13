import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

/**
 * Created by ikbalkazar on 13/12/16.
 */
public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        Network.getRequest("/user/joey", new Network.Completion() {
            @Override
            public void onCompleted(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
        });
    }
}
