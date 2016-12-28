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

    public interface QuestionCompletion extends ErrorCompletion {
        void whenCompleted(Question[] questions);
    }

    public interface ChallengeCompletion extends ErrorCompletion {
        void whenCompleted(Challenge[] challenges);
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
        getRequest("/user/" + username, new Completion() {
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
        getRequest("/friends/" + username, new Completion() {
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

    public static void getQuestions(String challengeId, final QuestionCompletion completion) {
        getRequest("/question/" + challengeId, new Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.getJSONArray("questions");
                Question[] questions = new Question[10];
                for (int i = 0; i < questions.length; i++) {
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    JSONArray choicesArray = jsonItem.getJSONArray("choices");
                    String[] choices = new String[4];
                    for (int j = 0; j < 4; j++) {
                        choices[j] = choicesArray.getString(j);
                    }
                    String url = jsonItem.getString("songUrl");
                    questions[i] = new Question(choices, url);
                }
                completion.whenCompleted(questions);
            }

            public void whenError(String error) {
                completion.whenError(error);
            }
        });
    }

    public static void createChallenge(String username, String friend) {
        client.preparePost(host + "/challenge/create?me=" + username + "&her=" + friend).execute();
    }

    public static void acceptChallenge(String username, String challengeId) {
        client.preparePut(host + "/challenge/accept?me=" + username + "&id=" + challengeId).execute();
    }

    public static void finishChallenge(String username, String challengeId, int score) {
        client.preparePut(host + "/challenge/finish?me=" + username + "&id=" + challengeId + "&score=" + score).execute();
    }

    public static void getChallenges(final String username, final ChallengeCompletion completion) {
        getRequest("/challenge/all?me=" + username, new Completion() {
            public void whenCompleted(JSONObject jsonObject) {
                System.out.println("Got challenges of " + username);
                System.out.println(jsonObject);

                JSONArray jsonArray = jsonObject.getJSONArray("challenges");
                Challenge[] challenges = new Challenge[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    challenges[i] = new Challenge(item.getString("id"),
                            item.getString("sender"),
                            item.getString("receiver"),
                            item.getString("createdAt"),
                            item.getInt("senderScore"),
                            item.getInt("recieverScore"),
                            item.getInt("status"));
                }

                completion.whenCompleted(challenges);
            }

            public void whenError(String error) {
                System.out.println("Error...");
                completion.whenError("Error getting challenges...");
            }
        });
    }
}
