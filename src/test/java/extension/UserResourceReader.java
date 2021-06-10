package extension;

import com.google.gson.Gson;
import domain.User;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class UserResourceReader implements ArgumentConverter {
    @Override
    public User convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new ArgumentConversionException("");
        } else {
            String path = (String) source;
            try {
                return readUserFromResources(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private User readUserFromResources(String name) throws Exception {
        String collect;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(name)) {
            try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {
                collect = br.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
        return new Gson().fromJson(collect, User.class);
    }
}
