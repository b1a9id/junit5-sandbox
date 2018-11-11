package extention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class RandomNameExtension implements ParameterResolver {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    public @interface RandomName {

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomName.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getRandomName(parameterContext.getParameter(), extensionContext);
    }

    private Object getRandomName(Parameter parameter, ExtensionContext extensionContext) {
        Class<?> type = parameter.getType();
        if (!String.class.equals(type)) {
            throw new ParameterResolutionException("No random generator implemented for " + type);
        }
        List<String> names = Arrays.asList("TARO", "JIRO", "TERU");
        Random random = new Random();
        return random.ints(0, names.size())
                .mapToObj(names::get)
                .findFirst()
                .get();
    }
}
