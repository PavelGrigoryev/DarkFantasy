package com.grigoryev.heroes.util;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@UtilityClass
public class YamlUtil {

    public Map<String, Map<String, String>> getYaml() {
        InputStream inputStream = YamlUtil.class
                .getClassLoader()
                .getResourceAsStream("application.yaml");
        return new Yaml().load(inputStream);
    }

}
