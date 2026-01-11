package it.ispw.unilife.boundary;

import it.ispw.unilife.bean.UserBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubAuthAdapter implements ExternalLogin {

    private final GithubAuthBoundary githubBoundary = new GithubAuthBoundary();

    @Override
    public UserBean authenticate() throws Exception {
        String rawJsonProfile = githubBoundary.executeLoginAndFetchProfile();

        if (rawJsonProfile == null) return null;

        UserBean user = new UserBean();

        user.setUserName(parseJson(rawJsonProfile, "login"));

        String name = parseJson(rawJsonProfile, "name");
        if (name != null && name.contains(" ")) {
            user.setName(name.split(" ")[0]);
            user.setSurname(name.substring(name.indexOf(" ") + 1));
        } else {
            user.setName(name);
            user.setSurname("");
        }

        return user;
    }

    private String parseJson(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + key + "\":\\s*\"([^\"]+)\"").matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
}