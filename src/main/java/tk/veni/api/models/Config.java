package tk.veni.api.models;

import java.util.HashMap;
import java.util.List;

public class Config {

    public String botToken;

    public String defaultPrefix;

    public List<Long> ownersIds;

    public HashMap<String, Long> supportServerIds;

    public HashMap<String, String> permissionsTranslates;

    public HashMap<String, Integer> colors;

}
