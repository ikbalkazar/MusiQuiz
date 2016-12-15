import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.util.HttpConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ikbalkazar on 13/12/16.
 */
public class Network {
    private final static AsyncHttpClient client = new DefaultAsyncHttpClient();
    private final static String host = "http://localhost:8080";

    public interface ErrorCompletion {
        void whenError(String error);
    }

    public interface Completion extends ErrorCompletion {
        void whenCompleted(JSONObject jsonObject);
    }

    public interface UserCompletion extends ErrorCompletion {
        void whenCompleted(User user);
    }

    public interface FriendsCompletion extends ErrorCompletion {
        void whenCompleted(String[] friends);
    }

    public interface RegisterCompletion extends ErrorCompletion {
        void whenCompleted(boolean success);
    }

    public static void getRequest(String path, final Completion completion) {
        client.prepareGet(host + path).execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) throws Exception {
                JSONObject json = new JSONObject(response.getResponseBody());
                completion.whenCompleted(json);
                return null;
            }

            @Override
            public void onThrowable(Throwable t) {
                super.onThrowable(t);
                completion.whenError(t.getMessage());
            }
        });
    }

    public static void registerUser(User user, final RegisterCompletion completion) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("securityQuestion", user.getSecurityQuestion());
        jsonObject.put("securityAnswer", user.getSecurityAnswer());
        System.out.println(jsonObject.toString());
        client.preparePost(host + "/user/create/")
                .setBody(jsonObject.toString())
                .addHeader("Content-Type", "application/json")
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (response.getStatusCode() != HttpConstants.ResponseStatusCodes.OK_200) {
                            completion.whenCompleted(false);
                        } else {
                            completion.whenCompleted(true);
                        }
                        return null;
                    }
                });
    }

    public static void fetchUser(String username, final UserCompletion completion) {
        getRequest(host + "/user/" + username, new Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                completion.whenCompleted(new User(jsonObject.getString("username"),
                        jsonObject.getString("password"),
                        jsonObject.getString("securityQuestion"),
                        jsonObject.getString("securityAnswer")));
            }

            public void whenError(String error) {
                completion.whenError(error);
            }
        });
    }

    public static void addFriend(String username, String friend) {
        client.preparePost(host + "/friends/" + username + "/" + friend).execute();
    }

    public static void deleteFriend(String username, String friend) {
        client.prepareDelete(host + "/friends/" + username + "/" + friend).execute();
    }

    public static void fetchFriends(String username, final FriendsCompletion completion) {
        getRequest(host + "/friends/" + username, new Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.getJSONArray("friends");
                String[] friends = new String[jsonArray.length()];
                for (int i = 0; i < friends.length; i++) {
                    friends[i] = jsonArray.getString(i);
                }
                completion.whenCompleted(friends);
            }

            public void whenError(String error) {
                completion.whenError(error);
            }
        });
    }
}
