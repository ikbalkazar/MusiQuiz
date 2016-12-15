import org.asynchttpclient.*;
import org.json.JSONObject;

/**
 * Created by ikbalkazar on 13/12/16.
 */
public class Network {
    private final static String host = "http://localhost:8080";

    public interface Completion {
        void whenCompleted(JSONObject jsonObject);
        void whenError(String error);
    }

    public interface UserCompletion {
        void whenCompleted(User user);
        void whenError(String error);
    }

    public static void getRequest(String path, final Completion completion) {
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        asyncHttpClient.prepareGet(host + path).execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) throws Exception {
                JSONObject json = new JSONObject(response.getResponseBody());
                completion.whenCompleted(json);
                return null;
            }

            @Override
            public void onThrowable(Throwable t) {
                super.onThrowable(t);

            }
        });
    }
}
